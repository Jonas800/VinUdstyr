package design.exam.Controller;

import design.exam.Helpers.PasswordHasher;
import design.exam.Helpers.PasswordMatcher;
import design.exam.Helpers.SessionHelper;
import design.exam.Model.User;
import design.exam.Repository.UserRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@Controller
public class UserController {


    @Autowired
    private UserRepository userRepository;
    String error = "";

    @GetMapping("/login")
    public String login(Model model){

        model.addAttribute("error", error);

        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpServletRequest request){

        User user = userRepository.findByEmail(email);

        if(user != null && user.getEmail() != null){
            try {
                if(PasswordMatcher.validatepassword(password, user.getPassword())){
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    SessionHelper.setRequest(request);
                    return "index";
                    //return SessionHelper.loginRedirect(user);
                }
            } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        error = "Email or password is invalid";

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(){

        SessionHelper.logout();

        return "redirect:/login";

    }

    @GetMapping("/user/create")
    public String createUser(Model model){
        model.addAttribute("user", new User());

        return "createUser";
    }

    @PostMapping("/user/create")
    public String createUser(@ModelAttribute User user){

        try {
            user.setPassword(PasswordHasher.generateStrongPasswordHash(user.getPassword()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        user.setApproved(false);
        userRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/user/view")
    public String approveUser(Model model){
        model.addAttribute("users", userRepository.findAllByIsApprovedEquals(false));

        return "userView";
    }

    @GetMapping("/user/approve/{id}")
    public String approveUser(@PathVariable Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();
        user.setApproved(true);

        userRepository.save(user);

        return "redirect:/user/view";
    }
}
