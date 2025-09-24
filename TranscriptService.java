package edu.ccrm.service;
import edu.ccrm.domain.*;
import java.util.*;

public class TranscriptService {
    private EnrollmentService enrollmentService;
    private StudentService studentService;
    private CourseService courseService;
    
    public TranscriptService(EnrollmentService enrollmentService, 
                           StudentService studentService, CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
    }
    
    public double calculateGPA(int studentId) {
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);
        
        double totalGradePoints = 0;
        int totalCredits = 0;
        
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getGrade() != null) {
                Course course = courseService.getCourse(enrollment.getCourseCode());
                if (course != null) {
                    totalGradePoints += enrollment.getGrade().getGradePoints() * course.getCredits();
                    totalCredits += course.getCredits();
                }
            }
        }
        
        return totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;
    }
    
    public void printTranscript(int studentId) {
        Student student = studentService.getStudent(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        
        System.out.println("\n=== OFFICIAL TRANSCRIPT ===");
        System.out.println("Student: " + student.getFullName());
        System.out.println("Registration Number: " + student.getRegNo());
        System.out.println("Email: " + student.getEmail());
        System.out.println("\n=== COURSE GRADES ===");
        
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);
        for (Enrollment enrollment : enrollments) {
            Course course = courseService.getCourse(enrollment.getCourseCode());
            if (course != null) {
                System.out.printf("%-8s %-30s %2d credits Grade: %s%n",
                        course.getCode(), course.getTitle(), course.getCredits(), 
                        enrollment.getGrade() != null ? enrollment.getGrade() : "N/A");
            }
        }
        
        System.out.printf("\nOverall GPA: %.2f%n", calculateGPA(studentId));
        System.out.println("==========================\n");
    }
}