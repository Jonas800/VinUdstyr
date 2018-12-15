package design.exam.Controller;

import design.exam.Helpers.SearchSpecification;
import design.exam.Model.Equipment;
import design.exam.Repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class SearchController {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @PostMapping("/simpleSearch")
    public ModelAndView simpleSearch(@RequestParam String search) {

        ArrayList<Equipment> equipment = (ArrayList<Equipment>) equipmentRepository.findAll(Specification.where(SearchSpecification.doesFieldContain(search, "equipmentName"))
        );

        ModelAndView mav = new ModelAndView("searchResult");
        mav.getModel().put("equipmentSearch", equipment);

        return mav;
    }

    @GetMapping("/search/advanced")
    public String advancedSearch(Model model){
        Equipment equipment = new Equipment();
        equipment.setAvailableForLoan(true);
        model.addAttribute("equipment", equipment);

        return "advancedSearch";
    }
    @PostMapping("/advancedSearch")
    public ModelAndView advancedSearch(@ModelAttribute Equipment equipment){
        ArrayList<Equipment> result = (ArrayList<Equipment>) equipmentRepository.findAll(Specification.
                where(SearchSpecification.doesFieldContain(equipment.getEquipmentName(), "equipmentName")).
                and(SearchSpecification.doesFieldEqual(equipment.getPriceFromNew(), "priceFromNew")).
                and(SearchSpecification.doesFieldEqual(equipment.getAvailableForLoan(), "availableForLoan")).
                and(SearchSpecification.doesFieldContain(equipment.getOwnerComment(), "ownerComment")).
                and(SearchSpecification.doesForeignFieldContain(equipment.getOwner().getFirstName(), "firstName", "owner")).
                and(SearchSpecification.doesForeignFieldContain(equipment.getOwner().getLastName(), "lastName", "owner")).
                and(SearchSpecification.doesForeignFieldEqual(equipment.getOwner().getZipcode(), "zipcode", "owner"))
        );

        ModelAndView mav = new ModelAndView("searchResult");
        mav.getModel().put("equipmentSearch", result);

        return mav;
    }
}
