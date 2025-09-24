package edu.ccrm.domain;
import java.time.LocalDate;

public class Enrollment {
    private int enrollmentId;
    private int studentId;
    private String courseCode;
    private LocalDate enrollmentDate;
    private Grade grade;
    
    public Enrollment(int enrollmentId, int studentId, String courseCode, LocalDate enrollmentDate) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.enrollmentDate = enrollmentDate;
        this.grade = null; 
    }
    
    // Getters and Setters
    public int getEnrollmentId() { return enrollmentId; }
    public int getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public Grade getGrade() { return grade; }
    public void setGrade(Grade grade) { this.grade = grade; }
    
    @Override
    public String toString() {
        return String.format("Enrollment{id=%d, studentId=%d, course='%s', date=%s, grade=%s}",
                enrollmentId, studentId, courseCode, enrollmentDate, grade);
    }
}
