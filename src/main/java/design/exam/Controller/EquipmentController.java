package design.exam.Controller;


import design.exam.Helpers.SessionHelper;
import design.exam.Model.Equipment;
import design.exam.Model.Person;
import design.exam.Repository.EquipmentRepository;
import design.exam.storage.StorageFileNotFoundException;
import design.exam.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class EquipmentController {

    private final StorageService storageService;

    @Autowired
    public EquipmentController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    private EquipmentRepository equipmentRepo;

    @GetMapping("/equipment/new")
    public String createEquipment(Model m){
        m.addAttribute("equipment", new Equipment());
        return "equipmentNew";
    }

    @GetMapping("/equipment/edit/{id}")
    public String equipmentEditView(Model m, @PathVariable Long id) {
        Optional<Equipment> optionalEquipment = equipmentRepo.findById(id);
        Equipment e = optionalEquipment.get();
        m.addAttribute("equipment", e);
        return "equipmentEdit";
    }

    @GetMapping("/equipment/show/files/{filename:.+}")
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


    @GetMapping("/equipment/show/{id}")
    public String showEquipment(Model model, @PathVariable Long id){
        Optional<Equipment> equipment = equipmentRepo.findById(id);
        Equipment e = equipment.get();
        storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(EquipmentController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList());
        model.addAttribute("file", "equipment/show/files/" + e.getFileName());
        model.addAttribute("equipment", e);
        return "equipmentShow";
    }
    @PostMapping("/equipment/new")
    public String newEquipment(Equipment equipment, @RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        String fileName = storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        Person person = SessionHelper.getCurrentUser();
        equipment.setOwner(person);
        equipment.setCurrentHolder(person);
        equipment.setFileName(fileName);
        Equipment e = equipmentRepo.save(equipment);

        return "redirect:/equipmentList";
    }
}
