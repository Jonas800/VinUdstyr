package design.exam.Controller;

import design.exam.Helpers.PasswordHelper;
import design.exam.Helpers.SessionHelper;
import design.exam.Model.Equipment;
import design.exam.Model.Person;
import design.exam.Model.User;
import design.exam.Repository.EquipmentRepository;
import design.exam.Repository.PersonRepository;
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
import java.util.UUID;

@Controller
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentRepository equipmentRepo;

    @Autowired
    private PersonRepository personRepo;

    @GetMapping("/user/create")
    public String createUser(Model model) {
        model.addAttribute("person", new User());

        return "createUser";
    }

    @PostMapping("/user/create")
    public String createUser(@ModelAttribute User user) {

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
    public String approveUser(Model model) {
        if (SessionHelper.isAdmin()) {
            model.addAttribute("persons", userRepository.findAllByIsApprovedEquals(false));

            return "viewUser";
        } else {
            return "redirect:/forbidden";
        }
    }

    @GetMapping("/user/approve/{id}")
    public String approveUser(@PathVariable Long id) {
        if (SessionHelper.isAdmin()) {
            Optional<User> optionalUser = userRepository.findById(id);
            User user = optionalUser.get();
            user.setApproved(true);

            userRepository.save(user);

            return "redirect:/user/view";
        } else {
            return "redirect:/forbidden";
        }
    }

    @GetMapping("/wait")
    public String waitForApproval() {
        return "waitForApproval";
    }

    @GetMapping("/user/view")
    public String viewActiveUsers(Model model) {
        if (SessionHelper.isAdmin()) {
            model.addAttribute("persons", userRepository.findAllByIsApprovedEquals(true));

            return "viewApprovedUsers";
        } else {
            return "redirect:/forbidden";
        }
    }

    @GetMapping("/user/ban/{id}")
    public String banUser(@PathVariable Long id) {
        if (SessionHelper.isAdmin()) {
            Optional<User> optionalUser = userRepository.findById(id);
            User user = optionalUser.get();
            user.setApproved(false);

            userRepository.save(user);

            return "redirect:/user/view";
        } else {
            return "redirect:/forbidden";
        }
    }

    @GetMapping("/user/add/farvorit/{id}")
    public String addFavoritEquipment(@PathVariable Long id) {
        if (SessionHelper.isLoginSessionValid()) {
            Optional<Equipment> optionalEquipment = equipmentRepo.findById(id);
            Person person = SessionHelper.getCurrentUser();
            System.out.println(person.getId());
            person.addTofavorits(optionalEquipment.get());

            personRepo.save(person);

            return "redirect:/";
        } else {
            return "redirect:/forbidden";
        }
    }
}
