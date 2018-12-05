package design.exam.Controller;


import design.exam.Model.Equipment;
import design.exam.Model.User;
import design.exam.equipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class EquipmentAPIController {

    @Autowired
    private equipmentRepository equipmentRepo;

    @PostMapping("/equipment/new")
    public ResponseEntity<Equipment> newEquipment(Equipment equipment){
        Equipment e = equipmentRepo.save(equipment);
        return new ResponseEntity(e, HttpStatus.OK);
    }
    @PutMapping("/equipment/update/{id}")
    public ResponseEntity<Equipment> updateStudent(@PathVariable Long id,
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
        return new ResponseEntity(equipmentToBeUpdated, HttpStatus.OK);
    }
    @DeleteMapping("/equipment/delete/{id}")
    public ResponseEntity<Equipment> deleteEquipment(@PathVariable Long id){
        Optional<Equipment> optionalEquipment = equipmentRepo.findById(id);
        Equipment equipment = optionalEquipment.get();
        equipmentRepo.delete(equipment);
        return new ResponseEntity(equipment, HttpStatus.OK);
    }
}
