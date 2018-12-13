package design.exam.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import design.exam.Helpers.GoogleDistanceAPI;
import design.exam.Helpers.SessionHelper;
import design.exam.Model.Equipment;
import design.exam.Model.Person;
import design.exam.Repository.EquipmentRepository;
import design.exam.storage.StorageFileNotFoundException;
import design.exam.storage.StorageService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final StorageService storageService;

    @Autowired
    public HomeController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    private EquipmentRepository equipmentRepository;

    @GetMapping("/")
    public String index(Model model) {
        if (SessionHelper.isLoginSessionValid()) {
            storageService.loadAll().map(
                    path -> MvcUriComponentsBuilder.fromMethodName(HomeController.class,
                            "serveFile", path.getFileName().toString()).build().toString())
                    .collect(Collectors.toList());
            model.addAttribute("newestEquipment", equipmentRepository.findTop4ByOrderByIdDesc());

            GoogleDistanceAPI googleDistanceAPI = new GoogleDistanceAPI();
            Person currentLogin = SessionHelper.getCurrentUser();

            String destinations = "";

            List<Equipment> equipmentList = equipmentRepository.findAll();
            equipmentList.removeIf(equipment -> equipment.getCurrentHolder().getId().equals(currentLogin.getId()));

            for (int i = 0; i < equipmentList.size(); i++) {
                Person currentHolder = equipmentList.get(i).getCurrentHolder();
                if (!currentHolder.getId().equals(currentLogin.getId())) {
                    destinations += currentHolder.getAddress() + " " + currentHolder.getZipcode() + " Danmark" + "|";
                }
            }
            destinations = destinations.substring(0, destinations.length() - 1);
            System.out.println(destinations);


            try {
                //Get Json from Google as JSONObject
                JSONObject distanceJson = googleDistanceAPI.calculate(currentLogin.getAddress() + " " + currentLogin.getZipcode() + " Danmark", destinations);
                //Get distances
                JSONArray rows = distanceJson.getJSONArray("rows");
                JSONObject elements = rows.getJSONObject(0);
                JSONArray elementsArray = elements.getJSONArray("elements");
                for (int i = 0; i < equipmentList.size(); i++) {
                    JSONObject distances = elementsArray.getJSONObject(i);
                    JSONObject distancesArray = distances.getJSONObject("distance");

                    Integer distance = distancesArray.getInt("value");
                    equipmentList.get(i).setDistance(distance);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            equipmentList.sort(new Comparator<Equipment>() {
                @Override
                public int compare(Equipment e1, Equipment e2) {
                    return e1.getDistance().compareTo(e2.getDistance());
                }
            });

            model.addAttribute("closestEquipment", equipmentList);

            return "index";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


}
