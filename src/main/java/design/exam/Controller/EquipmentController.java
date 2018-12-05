package design.exam.Controller;


import design.exam.Model.Equipment;
import design.exam.equipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EquipmentController {

    @Autowired
    private equipmentRepository equipmentRepo;

    @GetMapping("/equipment/new")
    public String createEquipment(Model m){
        m.addAttribute("equipment", new Equipment());
        return "equipmentNew";


    }

}
