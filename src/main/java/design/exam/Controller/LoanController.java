package design.exam.Controller;

import design.exam.Helpers.SessionHelper;
import design.exam.Model.Equipment;
import design.exam.Model.Loan;
import design.exam.Repository.EquipmentRepository;
import design.exam.Repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class LoanController {

    private static Long id;

    @Autowired
    EquipmentRepository equipmentRepo;
    @Autowired
    LoanRepository loanRepo;

    @GetMapping("/equipment/{id}/loan")
    public String createLoan(@PathVariable Long id, Model model) {
        if (SessionHelper.isLoginSessionValid()) {
            Optional<Equipment> equipment = equipmentRepo.findById(id);
            this.id = id;
            model.addAttribute("loan", new Loan());
            model.addAttribute("equipment", equipment.get());
            return "RequestLoan";
        } else {
            return "redirect:/forbidden";
        }
    }

    @PostMapping("/equipment/loan")
    public String loans(@RequestParam String startDate, @RequestParam String endDate) {
        if (SessionHelper.isLoginSessionValid()) {
            Loan loan = new Loan();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate d = LocalDate.parse(startDate, formatter);
            LocalDate d2 = LocalDate.parse(endDate, formatter);
            System.out.println(loan.getEndDate());
            loan.setStartDate(d);
            loan.setEndDate(d2);
            loan.setLoanee(SessionHelper.getCurrentUser());
            Optional<Equipment> e = equipmentRepo.findById(id);
            loan.setEquipment(e.get());
//        e.get().setAvailableForLoan(false);
            saveAvailability(e.get());
            loanRepo.save(loan);
            return "redirect:/user/loans";
        } else {
            return "redirect:/forbidden";
        }
    }

    //@PutMapping
    public void saveAvailability(Equipment e) {
        equipmentRepo.save(e);
    }


    @GetMapping("/user/loans")
    public String showLoans(Model model) {
        if (SessionHelper.isLoginSessionValid()) {
            List<Loan> loans = loanRepo.findAllByLoanee(SessionHelper.getCurrentUser());
            model.addAttribute("loans", loans);

            return "loans";
        } else {
            return "redirect:/forbidden";
        }
    }

    @GetMapping("/user/loanrequests")
    public String loanRequests(Model model) {
        if (SessionHelper.isLoginSessionValid()) {
            List<Loan> loans = loanRepo.findAllByEquipment_Owner(SessionHelper.getCurrentUser());
            model.addAttribute("loanRequests", loans);
            id = SessionHelper.getCurrentUser().getId();
            return "loanRequests";
        } else {
            return "redirect:/forbidden";
        }
    }

    @GetMapping("/loan/approve/{id}")
    public String AcceptLoan(@PathVariable Long id) {
        if (SessionHelper.isLoginSessionValid()) {
            Optional<Loan> optionalLoan = loanRepo.findById(id);
            Loan loan = optionalLoan.get();
            loan.setAccepted(true);
            Equipment equipment = loan.getEquipment();
            equipment.setAvailableForLoan(false);
            equipment.setCurrentHolder(loan.getLoanee());
            saveAvailability(equipment);
            loanRepo.save(loan);
            return "redirect:/user/loanrequests";
        } else {
            return "redirect:/forbidden";
        }
    }

}
