package design.exam.Controller;


import design.exam.Model.Equipment;
import design.exam.Repository.EquipmentRepository;
import design.exam.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@RestController
public class EquipmentAPIController {

    private final StorageService storageService;

    @Autowired
    public EquipmentAPIController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    private EquipmentRepository equipmentRepo;

    @PutMapping("/equipment/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                                   @RequestParam String equipmentName,
                                                   @RequestParam Integer equipmentAge,
                                                   @RequestParam Integer priceFromNew,
                                                   @RequestParam String dependency,
                                                   @RequestParam String ownerComment,
                                                   @RequestParam boolean availableForLoan
                                                   ) {

        Optional<Equipment> optionalEquipment = equipmentRepo.findById(id);
        Equipment equipmentToBeUpdated = optionalEquipment.get();
        equipmentToBeUpdated.setEquipmentName(equipmentName);
        equipmentToBeUpdated.setEquipmentAge(equipmentAge);
        equipmentToBeUpdated.setPriceFromNew(priceFromNew);
        equipmentToBeUpdated.setDependency(dependency);
        equipmentToBeUpdated.setOwnerComment(ownerComment);
        equipmentToBeUpdated.setAvailableForLoan(availableForLoan);

        equipmentRepo.save(equipmentToBeUpdated);
        return "equipmentShow";
    }
    @DeleteMapping("/equipment/delete/{id}")
    public ResponseEntity<Equipment> deleteEquipment(@PathVariable Long id){
        Optional<Equipment> optionalEquipment = equipmentRepo.findById(id);
        Equipment equipment = optionalEquipment.get();
        equipmentRepo.delete(equipment);
        return new ResponseEntity(equipment, HttpStatus.OK);
    }

/*    @PostMapping("/fileUpload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }*/
}
