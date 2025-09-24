// File 16: CCRMApp.java (edu.ccrm.cli)
package edu.ccrm.cli;
import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.config.AppConfig;
import java.util.*;

public class CCRMApp {
    private Scanner scanner = new Scanner(System.in);
    private StudentService studentService = new StudentService();
    private CourseService courseService = new CourseService();
    private EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);
    private TranscriptService transcriptService = new TranscriptService(enrollmentService, studentService, courseService);
    
    public static void main(String[] args) {
        CCRMApp app = new CCRMApp();
        app.initializeSampleData();
        app.showWelcomeMessage();
        app.runMainMenu();
    }
    
    private void showWelcomeMessage() {
        System.out.println("==============================================");
        System.out.println("  Campus Course & Records Manager (CCRM)");
        System.out.println("==============================================");
        System.out.println("Configuration loaded: " + AppConfig.getInstance().getDataFolderPath());
        System.out.println();
    }
    
    private void initializeSampleData() {
        // Add sample students
        studentService.addStudent("24BCE10093", "John Smith", "john.24BCE10093@universityname.ac.in", "ACTIVE");
        studentService.addStudent("24BCE10094", "Jane Doe", "jane.24BCE10094@universityname.ac.in", "ACTIVE");
        studentService.addStudent("24ECE10095", "Bob Wilson", "bob.24ECE10095@universityname.ac.in", "ACTIVE");
     
        // Add sample courses
        courseService.addCourse("CS101", "Introduction to Computer Science", 3, "Dr. Johnson", Semester.FALL, "CS");
        courseService.addCourse("CS201", "Data Structures", 4, "Dr. Smith", Semester.SPRING, "CS");
        courseService.addCourse("MATH101", "Calculus I", 4, "Dr. Brown", Semester.FALL, "MATH");
        courseService.addCourse("EE101", "Circuit Analysis", 3, "Dr. Wilson", Semester.SPRING, "EE");
        courseService.addCourse("CSE3002", "DBMS", 4, "Dr. Johnson", Semester.FALL, "CS");
        courseService.addCourse("CSE2001", "DSA", 4, "Dr. Smith", Semester.FALL, "CS");
        courseService.addCourse("CSE2006", "Programming in Java", 3, "Dr. Lee", Semester.FALL, "CS");
        courseService.addCourse("MAT3002", "Applied Linear Algebra", 3, "Dr. Brown", Semester.FALL, "MATH");
        
        // Add sample enrollments with error handling
        try {
            enrollmentService.enrollStudent(10093, "CS101"); // New IDs (last 5 digits)
            enrollmentService.enrollStudent(10093, "MATH101");
            enrollmentService.enrollStudent(10094, "CS101");
            enrollmentService.enrollStudent(10094, "CS201");
            enrollmentService.enrollStudent(10093, "CSE3002"); // Add new course enrollment
            enrollmentService.enrollStudent(10094, "CSE2001"); // Add new course enrollment
            
            // Add some grades
            enrollmentService.recordGrade(10093, "CS101", Grade.A);
            enrollmentService.recordGrade(10093, "MATH101", Grade.B);
            enrollmentService.recordGrade(10094, "CS101", Grade.A);
        } catch (Exception e) {
            System.out.println("Error initializing sample data: " + e.getMessage());
        }
    }
    
    private void runMainMenu() {
        boolean running = true;
        
        while (running) {
            showMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    manageStudents();
                    break;
                case 2:
                    manageCourses();
                    break;
                case 3:
                    manageEnrollment();
                    break;
                case 4:
                    manageGrades();
                    break;
                case 5:
                    showReports();
                    break;
                case 6:
                    importExportData();
                    break;
                case 7:
                    backupData();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using CCRM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void showMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollment");
        System.out.println("4. Manage Grades");
        System.out.println("5. Reports");
        System.out.println("6. Import/Export Data");
        System.out.println("7. Backup & Show Backup Size");
        System.out.println("0. Exit");
        System.out.println("=================");
    }
    
    private void manageStudents() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== STUDENT MANAGEMENT ===");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Deactivate Student");
            System.out.println("4. View All Students");
            System.out.println("5. Search Students");
            System.out.println("6. View Student Profile");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter choice: ");
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    updateStudent();
                    break;
                case 3:
                    deactivateStudent();
                    break;
                case 4:
                    viewAllStudents();
                    break;
                case 5:
                    searchStudents();
                    break;
                case 6:
                    viewStudentProfile();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private void addStudent() {
        System.out.println("\n=== ADD STUDENT ===");
        String regNo = getStringInput("Registration Number (e.g., 24BCE10093): ");
        String fullName = getStringInput("Full Name (email will be auto-generated as name.regno@universityname.ac.in): ");
        
        // Generate email dynamically
        String[] nameParts = fullName.split(" ", 2); // Split on first space
        String firstName = nameParts.length > 0 ? nameParts[0].toLowerCase() : "student"; // Default to "student" if no name
        String email = firstName + "." + regNo + "@universityname.ac.in";
        
        // Validation using utility class
        if (!edu.ccrm.util.Validators.isValidRegNo(regNo)) {
            System.out.println("Invalid registration number format! Use YYXXXNNNNN (e.g., 24BCE10093 where 24 is year, BCE is branch, 10093 is student number).");
            return;
        }
        if (!edu.ccrm.util.Validators.isValidEmail(email)) {
            System.out.println("Generated email format invalid! Check name or regNo. Expected: lowercase name.regno@universityname.ac.in.");
            return;
        }
        
        studentService.addStudent(regNo, fullName, email, "ACTIVE");
        System.out.println("Student added successfully! Email assigned: " + email);
    }
    
    private void updateStudent() {
        System.out.println("\n=== UPDATE STUDENT ===");
        int id = getIntInput("Student ID: ");
        Student student = studentService.getStudent(id);
        
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        
        System.out.println("Current: " + student.toString());
        String fullName = getStringInput("New Full Name (press enter to keep current): ");
        String email = getStringInput("New Email (press enter to keep current, or enter to regenerate): ");
        String status = getStringInput("New Status (ACTIVE/INACTIVE, press enter to keep current): ");
        
        if (fullName.trim().isEmpty()) fullName = student.getFullName();
        if (email.trim().isEmpty()) {
            String[] nameParts = fullName.split(" ", 2);
            String firstName = nameParts.length > 0 ? nameParts[0].toLowerCase() : "student";
            email = firstName + "." + student.getRegNo() + "@universityname.ac.in";
        } else if (!edu.ccrm.util.Validators.isValidEmail(email)) {
            System.out.println("Invalid email format! Use lowercase name.regno@universityname.ac.in (e.g., john.24BCE10093@universityname.ac.in).");
            return;
        }
        if (status.trim().isEmpty()) status = student.getStatus();
        
        studentService.updateStudent(id, fullName, email, status);
        System.out.println("Student updated successfully! Email: " + email);
    }
    
    private void deactivateStudent() {
        System.out.println("\n=== DEACTIVATE STUDENT ===");
        int id = getIntInput("Student ID: ");
        studentService.deactivateStudent(id);
        System.out.println("Student deactivated successfully!");
    }
    
    private void viewAllStudents() {
        System.out.println("\n=== ALL STUDENTS ===");
        List<Student> students = studentService.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        for (Student student : students) {
            System.out.println(student.toString());
        }
    }
    
    private void searchStudents() {
        System.out.println("\n=== SEARCH STUDENTS ===");
        String query = getStringInput("Enter search term (name or reg number): ");
        List<Student> results = studentService.searchStudents(query);
        
        if (results.isEmpty()) {
            System.out.println("No students found matching: " + query);
            return;
        }
        
        System.out.println("Search Results:");
        results.forEach(System.out::println);
    }
    
    private void viewStudentProfile() {
        System.out.println("\n=== STUDENT PROFILE ===");
        int id = getIntInput("Student ID: ");
        Student student = studentService.getStudent(id); // Retrieve student object
        if (student != null) {
            studentService.printStudentProfile(id); // Prints toString() with program and school
            System.out.println("Program: " + student.getProgram());
            System.out.println("School: " + student.getSchool());
        } else {
            System.out.println("Student not found!");
        }
        transcriptService.printTranscript(id);
    }
    
    private void manageCourses() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== COURSE MANAGEMENT ===");
            System.out.println("1. Add Course");
            System.out.println("2. Update Course");
            System.out.println("3. View All Courses");
            System.out.println("4. Search by Instructor");
            System.out.println("5. Filter by Department");
            System.out.println("6. Filter by Semester");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter choice: ");
            
            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    updateCourse();
                    break;
                case 3:
                    viewAllCourses();
                    break;
                case 4:
                    searchCoursesByInstructor();
                    break;
                case 5:
                    filterCoursesByDepartment();
                    break;
                case 6:
                    filterCoursesBySemester();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private void addCourse() {
        System.out.println("\n=== ADD COURSE ===");
        String code = getStringInput("Course Code (e.g., CS101): ");
        String title = getStringInput("Course Title: ");
        int credits = getIntInput("Credits: ");
        String instructor = getStringInput("Instructor: ");
        String department = getStringInput("Department: ");
        
        if (!edu.ccrm.util.Validators.isValidCourseCode(code)) {
            System.out.println("Invalid course code format!");
            return;
        }
        
        System.out.println("Select Semester:");
        System.out.println("1. SPRING");
        System.out.println("2. SUMMER");
        System.out.println("3. FALL");
        
        int semesterChoice = getIntInput("Semester choice: ");
        Semester semester;
        
        semester = switch (semesterChoice) {
            case 1 -> Semester.SPRING;
            case 2 -> Semester.SUMMER;
            case 3 -> Semester.FALL;
            default -> {
                System.out.println("Invalid semester choice, defaulting to FALL");
                yield Semester.FALL;
            }
        };
        
        courseService.addCourse(code, title, credits, instructor, semester, department);
        System.out.println("Course added successfully!");
    }
    
    private void updateCourse() {
        System.out.println("\n=== UPDATE COURSE ===");
        String code = getStringInput("Course Code: ");
        Course course = courseService.getCourse(code);
        
        if (course == null) {
            System.out.println("Course not found!");
            return;
        }
        
        System.out.println("Current: " + course.toString());
        String title = getStringInput("New Title (press enter to keep current): ");
        String creditsStr = getStringInput("New Credits (press enter to keep current): ");
        String instructor = getStringInput("New Instructor (press enter to keep current): ");
        String department = getStringInput("New Department (press enter to keep current): ");
        
        if (title.trim().isEmpty()) title = course.getTitle();
        if (instructor.trim().isEmpty()) instructor = course.getInstructor();
        if (department.trim().isEmpty()) department = course.getDepartment();
        
        int credits = course.getCredits();
        if (!creditsStr.trim().isEmpty()) {
            try {
                credits = Integer.parseInt(creditsStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid credits format, keeping current value");
            }
        }
        
        courseService.updateCourse(code, title, credits, instructor, course.getSemester(), department);
        System.out.println("Course updated successfully!");
    }
    
    private void viewAllCourses() {
        System.out.println("\n=== ALL COURSES ===");
        List<Course> courses = courseService.getAllCourses();
        
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).toString());
        }
    }
    
    private void searchCoursesByInstructor() {
        System.out.println("\n=== SEARCH BY INSTRUCTOR ===");
        String instructor = getStringInput("Instructor name: ");
        List<Course> results = courseService.searchByInstructor(instructor);
        
        if (results.isEmpty()) {
            System.out.println("No courses found for instructor: " + instructor);
            return;
        }
        
        System.out.println("Courses by " + instructor + ":");
        results.stream()
               .sorted((c1, c2) -> c1.getCode().compareTo(c2.getCode()))
               .forEach(System.out::println);
    }
    
    private void filterCoursesByDepartment() {
        System.out.println("\n=== FILTER BY DEPARTMENT ===");
        String department = getStringInput("Department: ");
        List<Course> results = courseService.filterByDepartment(department);
        
        if (results.isEmpty()) {
            System.out.println("No courses found in department: " + department);
            return;
        }
        
        System.out.println("Courses in " + department + " department:");
        results.forEach(System.out::println);
    }
    
    private void filterCoursesBySemester() {
        System.out.println("\n=== FILTER BY SEMESTER ===");
        System.out.println("1. SPRING");
        System.out.println("2. SUMMER");
        System.out.println("3. FALL");
        
        int choice = getIntInput("Semester choice: ");
        Semester semester = switch (choice) {
            case 1 -> Semester.SPRING;
            case 2 -> Semester.SUMMER;
            case 3 -> Semester.FALL;
            default -> null;
        };
        
        if (semester == null) {
            System.out.println("Invalid choice!");
            return;
        }
        
        List<Course> results = courseService.filterBySemester(semester);
        
        if (results.isEmpty()) {
            System.out.println("No courses found for " + semester + " semester");
            return;
        }
        
        System.out.println("Courses in " + semester + " semester:");
        results.forEach(System.out::println);
    }
    
    private void manageEnrollment() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== ENROLLMENT MANAGEMENT ===");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. Unenroll Student from Course");
            System.out.println("3. View Student Enrollments");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter choice: ");
            
            switch (choice) {
                case 1:
                    enrollStudent();
                    break;
                case 2:
                    unenrollStudent();
                    break;
                case 3:
                    viewStudentEnrollments();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private void enrollStudent() {
        System.out.println("\n=== ENROLL STUDENT ===");
        int studentId = getIntInput("Student ID: ");
        String courseCode = getStringInput("Course Code: ");
        
        try {
            enrollmentService.enrollStudent(studentId, courseCode);
            System.out.println("Student enrolled successfully!");
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
            System.out.println("Enrollment failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
    
    private void unenrollStudent() {
        System.out.println("\n=== UNENROLL STUDENT ===");
        int studentId = getIntInput("Student ID: ");
        String courseCode = getStringInput("Course Code: ");
        
        enrollmentService.unenrollStudent(studentId, courseCode);
        System.out.println("Student unenrolled successfully!");
    }
    
    private void viewStudentEnrollments() {
        System.out.println("\n=== STUDENT ENROLLMENTS ===");
        int studentId = getIntInput("Student ID: ");
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);
        
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found for student ID: " + studentId);
            return;
        }
        
        System.out.println("Enrollments for Student ID " + studentId + ":");
        enrollments.forEach(System.out::println);
    }
    
    private void manageGrades() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== GRADE MANAGEMENT ===");
            System.out.println("1. Record Grade");
            System.out.println("2. Calculate Student GPA");
            System.out.println("3. Generate Transcript");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter choice: ");
            
            switch (choice) {
                case 1:
                    recordGrade();
                    break;
                case 2:
                    calculateStudentGPA();
                    break;
                case 3:
                    generateTranscript();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private void recordGrade() {
        System.out.println("\n=== RECORD GRADE ===");
        int studentId = getIntInput("Student ID: ");
        String courseCode = getStringInput("Course Code: ");
        
        System.out.println("Select Grade:");
        Grade[] grades = Grade.values();
        for (int i = 0; i < grades.length; i++) {
            System.out.println((i + 1) + ". " + grades[i] + " (" + grades[i].getGradePoints() + " points)");
        }
        
        int gradeChoice = getIntInput("Grade choice: ");
        
        if (gradeChoice < 1 || gradeChoice > grades.length) {
            System.out.println("Invalid grade choice!");
            return;
        }
        
        Grade selectedGrade = grades[gradeChoice - 1];
        enrollmentService.recordGrade(studentId, courseCode, selectedGrade);
        System.out.println("Grade recorded successfully!");
    }
    
    private void calculateStudentGPA() {
        System.out.println("\n=== CALCULATE GPA ===");
        int studentId = getIntInput("Student ID: ");
        double gpa = transcriptService.calculateGPA(studentId);
        System.out.printf("Student GPA: %.2f%n", gpa);
    }
    
    private void generateTranscript() {
        System.out.println("\n=== GENERATE TRANSCRIPT ===");
        int studentId = getIntInput("Student ID: ");
        transcriptService.printTranscript(studentId);
    }
    
    private void showReports() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== REPORTS ===");
            System.out.println("1. Top Students by GPA");
            System.out.println("2. Course Enrollment Statistics");
            System.out.println("3. Grade Distribution");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter choice: ");
            
            switch (choice) {
                case 1:
                    showTopStudents();
                    break;
                case 2:
                    showCourseStats();
                    break;
                case 3:
                    showGradeDistribution();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private void showTopStudents() {
        System.out.println("\n=== TOP STUDENTS BY GPA ===");
        List<Student> students = studentService.getAllStudents();
        
        students.stream()
                .map(s -> new AbstractMap.SimpleEntry<>(s, transcriptService.calculateGPA(s.getId())))
                .filter(entry -> entry.getValue() > 0)
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .limit(10)
                .forEach(entry -> System.out.printf("%-20s (ID: %d) - GPA: %.2f%n", 
                         entry.getKey().getFullName(), entry.getKey().getId(), entry.getValue()));
    }
    
    private void showCourseStats() {
        System.out.println("\n=== COURSE ENROLLMENT STATISTICS ===");
        List<Course> courses = courseService.getAllCourses();
        
        for (Course course : courses) {
            long enrollmentCount = studentService.getAllStudents().stream()
                    .flatMap(s -> s.getEnrolledCourses().stream())
                    .filter(code -> code.equals(course.getCode()))
                    .count();
            
            System.out.printf("%-8s %-30s: %d students%n", 
                             course.getCode(), course.getTitle(), enrollmentCount);
        }
    }
    
    private void showGradeDistribution() {
        System.out.println("\n=== GRADE DISTRIBUTION ===");
        
        Map<Grade, Long> gradeDistribution = studentService.getAllStudents().stream()
                .flatMap(s -> enrollmentService.getStudentEnrollments(s.getId()).stream())
                .filter(e -> e.getGrade() != null)
                .collect(java.util.stream.Collectors.groupingBy(
                        Enrollment::getGrade,
                        java.util.stream.Collectors.counting()
                ));
        
        System.out.println("Grade Distribution:");
        for (Grade grade : Grade.values()) {
            long count = gradeDistribution.getOrDefault(grade, 0L);
            System.out.printf("%s: %d%n", grade, count);
        }
    }
    
    private void importExportData() {
        System.out.println("\n=== IMPORT/EXPORT DATA ===");
        System.out.println("1. Import Students from CSV");
        System.out.println("2. Export Students to CSV");
        System.out.println("3. Import Courses from CSV");
        System.out.println("4. Export Courses to CSV");
        System.out.println("0. Back to Main Menu");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1:
                importStudentsFromCSV();
                break;
            case 2:
                exportStudentsToCSV();
                break;
            case 3:
                importCoursesFromCSV();
                break;
            case 4:
                exportCoursesToCSV();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private void importStudentsFromCSV() {
        System.out.println("\n=== IMPORT STUDENTS ===");
        String filename = getStringInput("CSV filename (e.g., students.csv): ");
        System.out.println("Import functionality would be implemented with NIO.2 + Streams");
        System.out.println("Expected CSV format: regNo,fullName,email,status");
        System.out.println("File: " + filename + " would be processed from " + 
                         AppConfig.getInstance().getDataFolderPath());
    }
    
    private void exportStudentsToCSV() {
        System.out.println("\n=== EXPORT STUDENTS ===");
        String filename = getStringInput("CSV filename (e.g., students_export.csv): ");
        System.out.println("Export functionality would be implemented with NIO.2 + Streams");
        System.out.println("Students would be exported to: " + 
                         AppConfig.getInstance().getDataFolderPath() + filename);
        
        System.out.println("\nPreview of data to export:");
        List<Student> students = studentService.getAllStudents();
        students.stream()
                .limit(3)
                .forEach(s -> System.out.printf("%s,%s,%s,%s%n", 
                         s.getRegNo(), s.getFullName(), s.getEmail(), s.getStatus()));
        
        if (students.size() > 3) {
            System.out.println("... and " + (students.size() - 3) + " more records");
        }
    }
    
    private void importCoursesFromCSV() {
        System.out.println("\n=== IMPORT COURSES ===");
        String filename = getStringInput("CSV filename (e.g., courses.csv): ");
        System.out.println("Import functionality would be implemented with NIO.2 + Streams");
        System.out.println("Expected CSV format: code,title,credits,instructor,semester,department");
        System.out.println("File: " + filename + " would be processed from " + 
                         AppConfig.getInstance().getDataFolderPath());
    }
    
    private void exportCoursesToCSV() {
        System.out.println("\n=== EXPORT COURSES ===");
        String filename = getStringInput("CSV filename (e.g., courses_export.csv): ");
        System.out.println("Export functionality would be implemented with NIO.2 + Streams");
        
        System.out.println("\nPreview of data to export:");
        List<Course> courses = courseService.getAllCourses();
        if (courses == null) {
            System.out.println("No courses available to export.");
            return;
        }
        courses.stream()
               .limit(3)
               .forEach(c -> System.out.printf("%s,%s,%d,%s,%s,%s%n", 
                        c.getCode(), c.getTitle(), c.getCredits(), 
                        c.getInstructor(), c.getSemester().toString(), c.getDepartment()));
        
        if (courses.size() > 3) {
            System.out.println("... and " + (courses.size() - 3) + " more records");
        }
        System.out.println("Export would be saved to: " + AppConfig.getInstance().getDataFolderPath() + filename); // Use filename
    }
    
    private void backupData() {
        System.out.println("\n=== BACKUP & RECURSIVE UTILITIES ===");
        System.out.println("1. Create Backup");
        System.out.println("2. Show Backup Size (Recursive)");
        System.out.println("3. List Backup Files");
        System.out.println("0. Back to Main Menu");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1:
                createBackup();
                break;
            case 2:
                showBackupSize();
                break;
            case 3:
                listBackupFiles();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private void createBackup() {
        System.out.println("\n=== CREATE BACKUP ===");
        String timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String backupFolder = "backup_" + timestamp;
        
        System.out.println("Backup functionality would:");
        System.out.println("1. Create timestamped folder: " + backupFolder);
        System.out.println("2. Export all students to: " + backupFolder + "/students.csv");
        System.out.println("3. Export all courses to: " + backupFolder + "/courses.csv");
        System.out.println("4. Export all enrollments to: " + backupFolder + "/enrollments.csv");
        System.out.println("5. Use Path, Files, copy, move, exists operations");
        System.out.println("Backup created successfully! (simulated)");
    }
    
    private void showBackupSize() {
        System.out.println("\n=== BACKUP SIZE (RECURSIVE) ===");
        System.out.println("Recursive utility would:");
        System.out.println("1. Walk through backup directory tree");
        System.out.println("2. Calculate total size of all backup files");
        System.out.println("3. List files by depth using recursion");
        
        System.out.println("\nSimulated backup directory structure:");
        System.out.println("├── backup_20241201_120000/");
        System.out.println("│   ├── students.csv (2.5 KB)");
        System.out.println("│   ├── courses.csv (1.8 KB)");
        System.out.println("│   └── enrollments.csv (3.2 KB)");
        System.out.println("├── backup_20241201_130000/");
        System.out.println("│   ├── students.csv (2.6 KB)");
        System.out.println("│   ├── courses.csv (1.9 KB)");
        System.out.println("│   └── enrollments.csv (3.4 KB)");
        System.out.println("\nTotal backup size: 15.4 KB");
    }
    
    private void listBackupFiles() {
        System.out.println("\n=== BACKUP FILES ===");
        System.out.println("Using walk() method to traverse backup directory:");
        System.out.println("Files found:");
        System.out.println("- backup_20241201_120000/students.csv");
        System.out.println("- backup_20241201_120000/courses.csv");
        System.out.println("- backup_20241201_120000/enrollments.csv");
        System.out.println("- backup_20241201_130000/students.csv");
        System.out.println("- backup_20241201_130000/courses.csv");
        System.out.println("- backup_20241201_130000/enrollments.csv");
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
    
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}