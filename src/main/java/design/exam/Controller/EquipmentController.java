package design.exam.Controller;


import design.exam.Model.Equipment;
import design.exam.equipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class EquipmentController {

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

}
