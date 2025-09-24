package edu.ccrm.domain;
import java.util.*;

public class Student extends Person {
    private List<String> enrolledCourses;
    private String program;
    private String school;

    public Student(int id, String regNo, String fullName, String email, String status) {
        super(id, regNo, fullName, email, status);
        this.enrolledCourses = new ArrayList<>();
        this.program = "BTech"; // Default program for all bachelor's students
        String branch = regNo.substring(2, 5); // Extract branch code (e.g., "BCE" from "24BCE10093")
        this.school = branch.equals("BCE") ? "SCOPE" : "Other"; // SCOPE for Computer Science, else Other
    }
    
    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }
    
    public void setEnrolledCourses(List<String> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
    
    public void enrollInCourse(String courseCode) {
        if (!enrolledCourses.contains(courseCode)) {
            enrolledCourses.add(courseCode);
        }
    }
    
    public void unenrollFromCourse(String courseCode) {
        enrolledCourses.remove(courseCode);
    }
    
    public String getProgram() {
        return program;
    }
    
    public String getSchool() {
        return school;
    }
    
    public void setProgram(String program) {
        this.program = program;
    }
    
    public void setSchool(String school) {
        this.school = school;
    }
    
    @Override
    public String toString() {
        return String.format("Student{id=%d, regNo='%s', name='%s', email='%s', status='%s', program='%s', school='%s', courses=%s}",
                id, regNo, fullName, email, status, program, school, enrolledCourses);
    }
}