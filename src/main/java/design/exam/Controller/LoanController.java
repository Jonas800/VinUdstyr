package design.exam.Controller;

import design.exam.Helpers.SessionHelper;
import design.exam.Model.Equipment;
import design.exam.Model.Loan;
import design.exam.Repository.LoanRepository;
import design.exam.equipmentRepository;
import org.hibernate.annotations.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class LoanController {

    private static Long id;

    @Autowired
    equipmentRepository equipmentRepo;
    @Autowired
    LoanRepository loanRepo;

    @GetMapping("/equipment/{id}/loan")
    public String createLoan(@PathVariable Long id, Model model){
        Optional<Equipment> equipment = equipmentRepo.findById(id);
        this.id=id;
        model.addAttribute("loan", new Loan());
        model.addAttribute("equipment", equipment.get());
        return "RequestLoan";
    }

    @PostMapping("/equipment/loan")
    public String loans(@RequestParam String startDate, @RequestParam String endDate){
        Loan loan = new Loan();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate d = LocalDate.parse(startDate, formatter);
        LocalDate d2 = LocalDate.parse(endDate,formatter);
        System.out.println(loan.getEndDate());
        loan.setStartDate(d);
        loan.setEndDate(d2);
        loan.setLoanee(SessionHelper.getCurrentUser());
        Optional<Equipment> e = equipmentRepo.findById(id);
        loan.setEquipment(e.get());
        e.get().setAvailableForLoan(false);
        saveAvailability(e.get());
        loanRepo.save(loan);
        return "redirect:/user/loans";
    }
    //@PutMapping
    public void saveAvailability(Equipment e){
        equipmentRepo.save(e);
    }


    @GetMapping("/user/loans")
    public String showLoans(Model model){
        List<Loan> loans = loanRepo.findAllByLoanee(SessionHelper.getCurrentUser());
        System.out.println(loans.get(0).getStartDate());
        model.addAttribute("loans", loans);

        return "loans";
    }


}
