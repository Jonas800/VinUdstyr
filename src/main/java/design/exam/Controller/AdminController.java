package design.exam.Controller;

import design.exam.Helpers.PasswordHelper;
import design.exam.Helpers.SessionHelper;
import design.exam.Model.Admin;
import design.exam.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/admin/create")
    public String createAdmin(Model model) {
      //  if (SessionHelper.isAdmin()) {
            model.addAttribute("person", new Admin());
            return "createAdmin";
      //  } else {
       //     return SessionHelper.invalidRequestRedirect();
     //   }
    }


    @PostMapping("/admin/create")
    public String createAdmin(@ModelAttribute Admin admin) {
      //  if (SessionHelper.isAdmin()) {
            try {
                admin.setPassword(PasswordHelper.generateStrongPasswordHash(admin.getPassword()));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }

            adminRepository.save(admin);
            return "redirect:/admin/view";
     //   } else {
       //     return SessionHelper.invalidRequestRedirect();
    //    }
    }

    @GetMapping("/admin/view")
    public String viewAdmins(Model model) {
        if (SessionHelper.isAdmin()) {
            model.addAttribute("persons", adminRepository.findAll());

            return "viewAdmin";
        } else {
            return SessionHelper.invalidRequestRedirect();
        }
    }
}
