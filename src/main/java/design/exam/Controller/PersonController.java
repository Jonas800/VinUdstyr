package design.exam.Controller;

import design.exam.Helpers.PasswordHelper;
import design.exam.Helpers.SessionHelper;
import design.exam.Model.Person;
import design.exam.Model.User;
import design.exam.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    String error = "";

    @GetMapping("/login")
    public String login(Model model) {
        if (!SessionHelper.isLoginSessionValid()) {
            model.addAttribute("error", error);
            return "login";
        } else{
            return "redirect:/index";
        }
    }


    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {

        Person person = personRepository.findByEmail(email);

        if (person != null && person.getEmail() != null) {
            try {
                if (PasswordHelper.validatePassword(password, person.getPassword())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("login", person);
                    SessionHelper.setSession(session);
                    return "redirect:/";
                }
            } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        error = "Email or password is invalid";

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        SessionHelper.logout();
        return "redirect:/login";
    }

    @GetMapping("/forbidden")
    public String forbidden(){

        return "forbidden";
    }
}
