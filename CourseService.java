package edu.ccrm.service;
import edu.ccrm.domain.*;
import java.util.*;
import java.util.stream.Collectors;

public class CourseService {
    private Map<String, Course> courses = new HashMap<>();
    
    public void addCourse(String code, String title, int credits, String instructor, 
                         Semester semester, String department) {
        Course course = new Course.Builder()
                .code(code)
                .title(title)
                .credits(credits)
                .instructor(instructor)
                .semester(semester)
                .department(department)
                .build();
        courses.put(code, course);
    }
    
    public void updateCourse(String code, String title, int credits, String instructor, 
                           Semester semester, String department) {
        Course course = courses.get(code);
        if (course != null) {
            Course updatedCourse = new Course.Builder()
                    .code(code)
                    .title(title)
                    .credits(credits)
                    .instructor(instructor)
                    .semester(semester)
                    .department(department)
                    .build();
            courses.put(code, updatedCourse);
        }
    }
    
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }
    
    public Course getCourse(String code) {
        return courses.get(code);
    }
    
    public List<Course> searchByInstructor(String instructor) {
        return courses.values().stream()
                .filter(c -> c.getInstructor().toLowerCase().contains(instructor.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public List<Course> filterByDepartment(String department) {
        return courses.values().stream()
                .filter(c -> c.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }
    
    public List<Course> filterBySemester(Semester semester) {
        return courses.values().stream()
                .filter(c -> c.getSemester() == semester)
                .collect(Collectors.toList());
    }

	public List<Course> getAllStudents() {
		// TODO Auto-generated method stub
		return null;
	}
}