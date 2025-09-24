package edu.ccrm.service;
import edu.ccrm.domain.*;
import edu.ccrm.config.AppConfig;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EnrollmentService {
    private Map<Integer, Enrollment> enrollments = new HashMap<>();
    private int nextEnrollmentId = 1;
    private StudentService studentService;
    private CourseService courseService;
    
    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }
    
    public void enrollStudent(int studentId, String courseCode) 
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        
        if (isStudentEnrolled(studentId, courseCode)) {
            throw new DuplicateEnrollmentException("Student already enrolled in course: " + courseCode);
        }
        
        int currentCredits = getCurrentSemesterCredits(studentId);
        Course course = courseService.getCourse(courseCode);
        if (course != null && currentCredits + course.getCredits() > AppConfig.getInstance().getMaxCreditsPerSemester()) {
            throw new MaxCreditLimitExceededException("Enrollment would exceed maximum credits per semester");
        }
        
        Enrollment enrollment = new Enrollment(nextEnrollmentId++, studentId, courseCode, LocalDate.now());
        enrollments.put(enrollment.getEnrollmentId(), enrollment);
        
        Student student = studentService.getStudent(studentId);
        if (student != null) {
            student.enrollInCourse(courseCode);
        }
    }
    
    public void unenrollStudent(int studentId, String courseCode) {
        Optional<Enrollment> enrollment = enrollments.values().stream()
                .filter(e -> e.getStudentId() == studentId && e.getCourseCode().equals(courseCode))
                .findFirst();
        
        if (enrollment.isPresent()) {
            enrollments.remove(enrollment.get().getEnrollmentId());
            Student student = studentService.getStudent(studentId);
            if (student != null) {
                student.unenrollFromCourse(courseCode);
            }
        }
    }
    
    public void recordGrade(int studentId, String courseCode, Grade grade) {
        Optional<Enrollment> enrollment = enrollments.values().stream()
                .filter(e -> e.getStudentId() == studentId && e.getCourseCode().equals(courseCode))
                .findFirst();
        
        if (enrollment.isPresent()) {
            enrollment.get().setGrade(grade);
        }
    }
    
    private boolean isStudentEnrolled(int studentId, String courseCode) {
        return enrollments.values().stream()
                .anyMatch(e -> e.getStudentId() == studentId && e.getCourseCode().equals(courseCode));
    }
    
    private int getCurrentSemesterCredits(int studentId) {
        return enrollments.values().stream()
                .filter(e -> e.getStudentId() == studentId)
                .mapToInt(e -> {
                    Course course = courseService.getCourse(e.getCourseCode());
                    return course != null ? course.getCredits() : 0;
                })
                .sum();
    }
    
    public List<Enrollment> getStudentEnrollments(int studentId) {
        return enrollments.values().stream()
                .filter(e -> e.getStudentId() == studentId)
                .collect(Collectors.toList());
    }
}