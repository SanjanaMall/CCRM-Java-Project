package edu.ccrm.domain;

public abstract class Person {
    protected int id;
    protected String regNo;
    protected String fullName;
    protected String email;
    protected String status;
    
    public Person(int id, String regNo, String fullName, String email, String status) {
        this.id = id;
        this.regNo = regNo;
        this.fullName = fullName;
        this.email = email;
        this.status = status;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    // Abstract method - must be implemented by subclasses
    public abstract String toString();
}