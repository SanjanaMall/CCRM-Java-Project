package edu.ccrm.util;

import java.util.regex.Pattern;

public class Validators {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[a-z]+\\.\\d{2}[A-Z]{3}\\d{5}@universityname\\.ac\\.in$"); // Fixed line 6
    
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    private static final Pattern REGNO_PATTERN = 
        Pattern.compile("^\\d{2}[A-Z]{3}\\d{5}$"); // Fixed line 13
    
    public static boolean isValidRegNo(String regNo) {
        return regNo != null && REGNO_PATTERN.matcher(regNo).matches();
    }
    
    public static boolean isValidCourseCode(String code) {
        return code != null && code.matches("^[A-Z]{2,4}\\d{3,4}$"); // Fixed line 23
    }
}