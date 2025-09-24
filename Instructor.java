package edu.ccrm.domain;
import java.util.*;

public class Instructor extends Person {
    private String department;
    private List<String> assignedCourses;
    
    public Instructor(int id, String regNo, String fullName, String email, String status, String department) {
        super(id, regNo, fullName, email, status);
        this.department = department;
        this.assignedCourses = new ArrayList<>();
    }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public List<String> getAssignedCourses() { return assignedCourses; }
    public void setAssignedCourses(List<String> assignedCourses) { this.assignedCourses = assignedCourses; }
    
    public void assignCourse(String courseCode) {
        if (!assignedCourses.contains(courseCode)) {
            assignedCourses.add(courseCode);
        }
    }
    
    @Override
    public String toString() {
        return String.format("Instructor{id=%d, regNo='%s', name='%s', email='%s', status='%s', dept='%s', courses=%s}",
                id, regNo, fullName, email, status, department, assignedCourses);
    }
}