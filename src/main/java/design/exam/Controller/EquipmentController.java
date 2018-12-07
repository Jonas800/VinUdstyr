package design.exam.Controller;


import design.exam.Model.Equipment;
import design.exam.equipmentRepository;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class EquipmentController {

    private final StorageService storageService;

    @Autowired
    public EquipmentController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    private equipmentRepository equipmentRepo;

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
