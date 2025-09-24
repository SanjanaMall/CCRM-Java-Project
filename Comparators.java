package edu.ccrm.util;
import edu.ccrm.domain.*;
import java.util.Comparator;

public class Comparators {
    
    // Lambda expressions for sorting
    public static final Comparator<Student> BY_NAME = 
        (s1, s2) -> s1.getFullName().compareToIgnoreCase(s2.getFullName());
    
    public static final Comparator<Student> BY_REG_NO = 
        (s1, s2) -> s1.getRegNo().compareTo(s2.getRegNo());
    
    public static final Comparator<Course> BY_CODE = 
        (c1, c2) -> c1.getCode().compareTo(c2.getCode());
    
    public static final Comparator<Course> BY_CREDITS = 
        (c1, c2) -> Integer.compare(c2.getCredits(), c1.getCredits()); // Descending
    
    // Method references
    public static final Comparator<Course> BY_TITLE = 
        Comparator.comparing(Course::getTitle);
    
    public static final Comparator<Student> BY_ID = 
        Comparator.comparing(Student::getId);
    
    // Chained comparators
    public static final Comparator<Course> BY_DEPT_THEN_CODE = 
        Comparator.comparing(Course::getDepartment)
                  .thenComparing(Course::getCode);
}