package edu.ccrm.domain;

public class Course {
    private String code;
    private String title;
    private int credits;
    private String instructor;
    private Semester semester;
    private String department;
    
    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
        this.department = builder.department;
    }
    
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }
    
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public void setSemester(Semester semester) { this.semester = semester; }
    
    public static class Builder {
        private String code;
        private String title;
        private int credits;
        private String instructor;
        private Semester semester;
        private String department;
        
        public Builder code(String code) {
            this.code = code;
            return this;
        }
        
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        
        public Builder credits(int credits) {
            this.credits = credits;
            return this;
        }
        
        public Builder instructor(String instructor) {
            this.instructor = instructor;
            return this;
        }
        
        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }
        
        public Builder department(String department) {
            this.department = department;
            return this;
        }
        
        public Course build() {
            return new Course(this);
        }
    }
    
    @Override
    public String toString() {
        return String.format("Course{code='%s', title='%s', credits=%d, instructor='%s', semester=%s, dept='%s'}",
                code, title, credits, instructor, semester, department);
    }
}