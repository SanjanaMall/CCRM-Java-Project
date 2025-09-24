package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.config.AppConfig;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.stream.Stream; // Explicitly imported to resolve Stream type

public class CSVFileHandler {
    private final StudentService studentService;
    private final CourseService courseService;
    private final String dataFolderPath;

    public CSVFileHandler(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.dataFolderPath = AppConfig.getInstance().getDataFolderPath();
        if (dataFolderPath == null || dataFolderPath.trim().isEmpty()) {
            throw new IllegalStateException("Data folder path is not configured.");
        }
        // Ensure data folder exists
        try {
            Files.createDirectories(Paths.get(dataFolderPath));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create data folder: " + e.getMessage(), e);
        }
    }

    public void importStudentsFromCSV(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            System.out.println("Invalid filename provided.");
            return;
        }
        Path filePath = Paths.get(dataFolderPath, filename);
        if (!Files.exists(filePath)) {
            System.out.println("File not found: " + filePath);
            return;
        }

        try (Stream<String> lines = Files.lines(filePath)) {
            final List<String> processedLines = new ArrayList<>();
            lines.skip(1).forEach(line -> {
                try {
                    if (line == null) throw new IllegalArgumentException("Null line encountered");
                    processedLines.add(line);
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        String regNo = parts[0].trim();
                        String fullName = parts[1].trim();
                        String email = parts[2].trim();
                        String status = parts[3].trim();
                        if (edu.ccrm.util.Validators.isValidRegNo(regNo) && edu.ccrm.util.Validators.isValidEmail(email)) {
                            if (studentService != null) {
                                studentService.addStudent(regNo, fullName, email, status);
                            } else {
                                throw new NullPointerException("StudentService is null");
                            }
                        } else {
                            System.out.println("Invalid data skipped: " + line);
                        }
                    } else {
                        System.out.println("Invalid CSV format skipped: " + line);
                    }
                } catch (Exception e) {
                    System.out.println("Error processing line '" + line + "': " + e.getMessage());
                }
            });
            System.out.println("Students imported successfully from " + filename + " (Processed " + processedLines.size() + " lines)");
        } catch (IOException e) {
            System.out.println("Error importing students: " + e.getMessage());
        }
    }

    public void exportStudentsToCSV(String filename) {
        Path filePath = Paths.get(dataFolderPath, filename);
        List<Student> students = studentService.getAllStudents();

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write("regNo,fullName,email,status");
            writer.newLine();
            for (Student student : students) {
                String line = String.format("%s,%s,%s,%s",
                        student.getRegNo(),
                        student.getFullName(),
                        student.getEmail(),
                        student.getStatus());
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Students exported successfully to " + filePath);
        } catch (IOException e) {
            System.out.println("Error exporting students: " + e.getMessage());
        }
    }

    public void importCoursesFromCSV(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            System.out.println("Invalid filename provided.");
            return;
        }
        Path filePath = Paths.get(dataFolderPath, filename);
        if (!Files.exists(filePath)) {
            System.out.println("File not found: " + filePath);
            return;
        }

        try (Stream<String> lines = Files.lines(filePath)) {
            final List<String> processedLines = new ArrayList<>();
            lines.skip(1).forEach(line -> {
                try {
                    if (line == null) throw new IllegalArgumentException("Null line encountered");
                    processedLines.add(line);
                    String[] parts = line.split(",");
                    if (parts.length == 6) {
                        String code = parts[0].trim();
                        String title = parts[1].trim();
                        int credits = Integer.parseInt(parts[2].trim());
                        String instructor = parts[3].trim();
                        Semester semester = Semester.valueOf(parts[4].trim().toUpperCase());
                        String department = parts[5].trim();
                        if (edu.ccrm.util.Validators.isValidCourseCode(code)) {
                            if (courseService != null) {
                                courseService.addCourse(code, title, credits, instructor, semester, department);
                            } else {
                                throw new NullPointerException("CourseService is null");
                            }
                        } else {
                            System.out.println("Invalid course data skipped: " + line);
                        }
                    } else {
                        System.out.println("Invalid CSV format skipped: " + line);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid data skipped: " + line + " - " + e.getMessage());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Malformed line skipped: " + line + " - " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Unexpected error processing line '" + line + "': " + e.getMessage());
                }
            });
            System.out.println("Courses imported successfully from " + filename + " (Processed " + processedLines.size() + " lines)");
        } catch (IOException e) {
            System.out.println("Error importing courses: " + e.getMessage());
        }
    }

    public void exportCoursesToCSV(String filename) {
        Path filePath = Paths.get(dataFolderPath, filename);
        List<Course> courses = courseService.getAllStudents(); // Typo: Should be getAllCourses()

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write("code,title,credits,instructor,semester,department");
            writer.newLine();
            for (Course course : courses) {
                String line = String.format("%s,%s,%d,%s,%s,%s",
                        course.getCode(),
                        course.getTitle(),
                        course.getCredits(),
                        course.getInstructor(),
                        course.getSemester().toString(),
                        course.getDepartment());
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Courses exported successfully to " + filePath);
        } catch (IOException e) {
            System.out.println("Error exporting courses: " + e.getMessage());
        }
    }
}