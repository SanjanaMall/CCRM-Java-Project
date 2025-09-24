package edu.ccrm.domain;

public enum Grade {
    A(4.0), B(3.0), C(2.0), D(1.0), F(0.0);
    
    private final double gradePoints;
    
    Grade(double gradePoints) {
        this.gradePoints = gradePoints;
    }
    
    public double getGradePoints() {
        return gradePoints;
    }
}