package design.exam.Helpers;

import design.exam.Model.Admin;
import design.exam.Model.Person;
import design.exam.Model.User;
import design.exam.Repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class SessionHelper {

    //private static HttpServletRequest request;
    private static HttpSession session;


    public static HttpSession getSession() {
        return session;
    }

    public static void setSession(HttpSession session) {
        //SessionHelper.request = request;
        SessionHelper.session = session;
    }

    public static boolean isLoginSessionValid() {
        if (session != null) {
            return session.getAttribute("login") != null;
        } else {
            return false;
        }
    }

    public static boolean isAdmin() {
        if (isLoginSessionValid()) {
            if (session.getAttribute("login") instanceof Admin) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public static boolean isUser() {
        if (isLoginSessionValid()) {
            if (session.getAttribute("login") instanceof User) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String invalidRequestRedirect() {
        if (isLoginSessionValid()) {
            return "redirect:/forbidden";
        } else {
            return "redirect:/login";
        }
    }

    public static Person getCurrentUser() {
        return (Person) session.getAttribute("login");
    }

    public static String getCurrentUserName() {
        if (isLoginSessionValid()) {
            Person person = (Person) session.getAttribute("login");
            return person.getFirstName() + " " + person.getLastName();
        } else {
            return "Ikke logget ind";
        }

    }

    /*

    public static boolean isStudent(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user") instanceof Student) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String loginRedirect(User user){
        if(user instanceof Student){
            return "redirect:/student/index";
        } else if(user instanceof Teacher){
            return "redirect:/teacher/index";
        } else if(user instanceof Administrator){
            return "redirect:/administrator/index";
        } else{
            return "redirect:/login";
        }
    }

    public static String redirectAdministrator(HttpServletRequest request, String view) {
        if (isSessionValid(request)) {
            if (isAdministrator(request)) {
                return "administrator/"+ view;
            } else {
                return "all/forbidden";
            }
        } else{
            return "redirect:/login";
        }
    }

    public static String redirectTeacher(HttpServletRequest request, String view) {
        if (isSessionValid(request)) {
            if (isTeacher(request)) {
                return "teacher/"+ view;
            } else {
                return "all/forbidden";
            }
        } else{
            return "redirect:/login";
        }
    }
    public static String redirectStudent(HttpServletRequest request, String view) {
        if (isSessionValid(request)) {
            if (isStudent(request)) {
                return "student/"+ view;
            } else {
                return "all/forbidden";
            }
        } else{
            return "redirect:/login";
        }
    }*/

    public static void logout() {
        //HttpSession session = request.getSession();
        session.invalidate();
        session = null;
    }
}
