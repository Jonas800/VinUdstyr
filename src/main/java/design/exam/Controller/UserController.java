package design.exam.Controller;

import design.exam.Helpers.PasswordHelper;
import design.exam.Helpers.SessionHelper;
import design.exam.Model.User;
import design.exam.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/create")
    public String createUser(Model model){
        model.addAttribute("person", new User());

        return "createUser";
    }

    @PostMapping("/user/create")
    public String createUser(@ModelAttribute User user){

        try {
            user.setPassword(PasswordHelper.generateStrongPasswordHash(user.getPassword()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        user.setApproved(false);
        userRepository.save(user);

        return "redirect:/wait";
    }

    @GetMapping("/user/approve")
    public String approveUser(Model model){
        model.addAttribute("persons", userRepository.findAllByIsApprovedEquals(false));

        return "viewUser";
    }

    @GetMapping("/user/approve/{id}")
    public String approveUser(@PathVariable Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();
        user.setApproved(true);

        userRepository.save(user);

        return "redirect:/user/view";
    }

    @GetMapping("/wait")
    public String waitForApproval(){
        return "waitForApproval";
    }

    @GetMapping("/user/view")
    public String viewActiveUsers(Model model){

        model.addAttribute("persons", userRepository.findAllByIsApprovedEquals(true));

        return "viewApprovedUsers";
    }

    @GetMapping("/user/ban/{id}")
    public String banUser(@PathVariable Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();
        user.setApproved(false);

        userRepository.save(user);

        return "redirect:/user/view";
    }
}
