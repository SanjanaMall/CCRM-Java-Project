package edu.ccrm.service;
import edu.ccrm.domain.*;
import java.util.*;
import java.util.stream.Collectors;

public class StudentService {
    private Map<Integer, Student> students = new HashMap<>();
    
    public void addStudent(String regNo, String fullName, String email, String status) {
        int id;
        try {
            id = Integer.parseInt(regNo.substring(regNo.length() - 5)); // Extract last 5 digits as int
        } catch (NumberFormatException e) {
            System.out.println("Invalid regNo format: Last 5 characters must be digits.");
            return;
        }
        Student student = new Student(id, regNo, fullName, email, status);
        if (students.containsKey(id)) {
            System.out.println("Duplicate ID detected: " + id + ". Student not added.");
            return;
        }
        students.put(id, student);
    }
    
    
    public void updateStudent(int id, String fullName, String email, String status) {
        Student student = students.get(id);
        if (student != null) {
            student.setFullName(fullName);
            student.setEmail(email);
            student.setStatus(status);
        }
    }
    
    public void deactivateStudent(int id) {
        Student student = students.get(id);
        if (student != null) {
            student.setStatus("INACTIVE");
        }
    }
    
    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
    
    public Student getStudent(int id) {
        return students.get(id);
    }
    
    public List<Student> searchStudents(String query) {
        return students.values().stream()
                .filter(s -> s.getFullName().toLowerCase().contains(query.toLowerCase()) ||
                           s.getRegNo().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public void printStudentProfile(int id) {
        Student student = students.get(id);
        if (student != null) {
            System.out.println("=== STUDENT PROFILE ===");
            System.out.println(student.toString());
            System.out.println("Enrolled Courses: " + student.getEnrolledCourses());
        } else {
            System.out.println("Student not found!");
        }
    }
}