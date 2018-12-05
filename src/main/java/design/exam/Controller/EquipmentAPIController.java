package design.exam.Controller;


import design.exam.Model.Equipment;
import design.exam.equipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EquipmentAPIController {

    @Autowired
    private equipmentRepository equipmentRepo;

    @PostMapping("/equipment/new")
    public ResponseEntity<Equipment> newEquipment(Equipment equipment){
        Equipment e = equipmentRepo.save(equipment);
        return new ResponseEntity(e, HttpStatus.OK);
    }
}
