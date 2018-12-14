package design.exam.Helpers;

import design.exam.Model.Admin;

public class TypeChecker {

    public static boolean isAdmin(Object o){
        return o instanceof Admin;
    }

}
