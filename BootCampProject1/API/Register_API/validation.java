package com.BootCampProject1.BootCampProject1.API.Register_API;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class validation {

    List<String> errorList = new ArrayList<String>();

    public static boolean isValid(String passwordhere, String confirmhere, List<String> errorList) {

        Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
        errorList.clear();

        boolean flag = true;

        if (!passwordhere.equals(confirmhere)) {
            errorList.add("password and confirm password does not match");
            flag = false;
        }
        if (passwordhere.length() < 8) {
            errorList.add("Password length must have at-least 8 character !!");
            flag = false;
        }
        if (!specialCharPatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at-least one special character !!");
            flag = false;
        }
        if (!UpperCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at-least one uppercase character !!");
            flag = false;
        }
        if (!lowerCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at-least one lowercase character !!");
            flag = false;
        }
        if (!digitCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at-least one digit character !!");
            flag = false;
        }

        return flag;
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isMobileNumberValid(long phoneNumber)
    {
        String p = String.valueOf(phoneNumber);
        String regex = "[7-9][0-9]{9}";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        System.out.println("The phone number "+phoneNumber+" is");
        java.util.regex.Matcher pN = pattern.matcher(p);
        System.out.println(pN.matches());
        return pN.matches();
    }

    public static boolean isValidCompanyName(String companyname) {
        String ePattern = "/^[.@&]?[a-zA-Z0-9 ]+[ !.@&()]?[ a-zA-Z0-9!()]+/";
        java.util.regex.Pattern p2 = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m2 = p2.matcher(companyname);
        return m2.matches();
    }

    public static boolean isValidGST(String gst) {
        String ePattern = "/^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/";
        java.util.regex.Pattern p3 = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m3 = p3.matcher(gst);
        return true;
//        return m3.matches();
    }
}
