# CCRM-Java-Project
Campus Course &amp; Records Manager - Java SE Project
Project Overview

The *Campus Course & Records Manager (CCRM)* is a comprehensive console-based Java SE application designed to manage student records, course information, enrollments, and academic transcripts for educational institutions. This project demonstrates advanced object-oriented programming concepts, design patterns, functional programming, and modern Java features.
## How to Run
### Prerequisites
- *JDK Version*: Java 11 or higher
- *IDE*: Eclipse IDE (recommended)
- *Operating System*: Windows, macOS, or Linux
### Commands
bash
# Compile (if using command line)
javac -d bin src/edu/ccrm/**/*.java
# Run
java -cp bin edu.ccrm.cli.CCRMApp
# Enable assertions (recommended for development)
java -ea -cp bin edu.ccrm.cli.CCRMApp

### Using Eclipse
1. Import project into Eclipse workspace
2. Right-click CCRMApp.java → Run As → Java Application
3. Follow the console menu prompts

## Evolution of Java

### Timeline
- *1995*: Java 1.0 - Initial release by Sun Microsystems
- *1997*: Java 1.1 - Inner classes, JavaBeans, JDBC
- *1998*: Java 2 (J2SE 1.2) - Swing, Collections Framework
- *2000*: J2SE 1.3 - HotSpot JVM, JavaSound
- *2002*: J2SE 1.4 - Assertions, NIO, XML processing
- *2004*: J2SE 5.0 - Generics, annotations, enhanced for loops
- *2006*: Java SE 6 - Performance improvements, scripting support
- *2011*: Java SE 7 - Try-with-resources, diamond operator
- *2014*: Java SE 8 - Lambda expressions, Stream API, Optional
- *2017*: Java SE 9 - Modules, JShell
- *2018*: Java SE 10 - Local variable type inference (var)
- *2018*: Java SE 11 - String methods, HTTP client (LTS)
- *2019*: Java SE 12 - Switch expressions (preview)
- *2020*: Java SE 14 - Pattern matching, records (preview)
- *2021*: Java SE 17 - Sealed classes, pattern matching (LTS)
- *Present*: Continued 6-month release cycle

<img width="428" height="455" alt="image" src="https://github.com/user-attachments/assets/83870c6f-68be-44fe-a9df-55840d7388ad" />

Java Architecture: JDK, JRE, JVM
Architecture Overview
┌─────────────────────────────────────────────┐
│                   JDK                       │
│  ┌─────────────────────────────────────────┐│
│  │               JRE                       ││
│  │  ┌─────────────────────────────────────┐││
│  │  │              JVM                    │││
│  │  │  - Class Loader                     │││
│  │  │  - Bytecode Verifier                │││
│  │  │  - Execution Engine                 │││
│  │  │  - Memory Areas (Heap, Stack)       │││
│  │  └─────────────────────────────────────┘││
│  │  - Core Libraries (java.lang, etc.)    ││
│  │  - Runtime Environment                 ││
│  └─────────────────────────────────────────┘│
│  - Development Tools (javac, jar, etc.)    │
│  - Documentation                           │
└─────────────────────────────────────────────┘
Component Interaction

JVM (Java Virtual Machine): Executes Java bytecode, manages memory
JRE (Java Runtime Environment): JVM + Core libraries needed to run Java applications
JDK (Java Development Kit): JRE + Development tools (compiler, debugger, etc.)
Windows Java Installation & Eclipse Setup
Java Installation Steps

Download JDK:

Visit Oracle JDK Downloads
Select Java 17 LTS for Windows x64
Download the installer (.exe file)


Install JDK:

Run the downloaded installer
Follow installation wizard (use default settings)
Note installation path (typically C:\Program Files\Java\jdk-17)

Verify Installation:
java -version
javac -version

Eclipse IDE Setup

Download Eclipse:

Visit Eclipse Downloads
Download "Eclipse IDE for Java Developers"


Eclipse Project Creation:

File → New → Java Project
Project name: CCRM
Use default JRE
Create packages: edu.ccrm.domain, edu.ccrm.service, etc.


Run Configuration:

Right-click CCRMApp.java
Run As → Java Application
Console shows application menu

<img width="227" height="494" alt="image" src="https://github.com/user-attachments/assets/e9a51ae1-6456-4ee2-8435-dee43f63c086" />

Assertion Usage
Enabling Assertions
# Enable all assertions
java -ea edu.ccrm.cli.CCRMApp

# Enable specific package assertions
java -ea:edu.ccrm.domain... edu.ccrm.cli.CCRMApp

Implementation Examples
<img width="602" height="177" alt="image" src="https://github.com/user-attachments/assets/ae02ca81-4ea1-40be-80e7-f9d5bad523be" />

Features Demonstrated
Core Java Concepts

Primitive Variables: int id, double gradePoints in domain classes
Objects: Student, Course, Enrollment entities
Operators: Arithmetic (totalGradePoints += grade * credits), Relational (credits > 0), Logical (email != null && pattern.matches())
Decision Structures: if-else in validation, switch in menu handling
Loops: for-each in student listing, while in menu loop
Arrays: Grade[] grades = Grade.values()
Strings: Email generation, formatting operations

Advanced OOP Features

Access Modifiers: private fields, protected Person fields, public methods
Constructor Inheritance: super() calls in Student/Instructor
Method Overriding: toString() implementations
Interfaces: Functional interfaces with lambdas
Inner Classes: Course.Builder static nested class
Immutability: Final fields in Grade enum

Modern Java Features

Generics: Map<Integer, Student>, List<Course>
Optional: Return types in search operations
Stream API: Data filtering and transformation
Lambda Expressions: students.stream().filter(s -> s.getName().contains(query))
Method References: System.out::println
Enhanced Switch: Pattern matching in semester selection

src/
└── edu/ccrm/
    ├── cli/                    # Command-line interface
    │   └── CCRMApp.java       # Main application with menu system
    ├── domain/                 # Core business entities
    │   ├── Person.java        # Abstract base class (inheritance)
    │   ├── Student.java       # Student entity with program/school fields
    │   ├── Instructor.java    # Instructor entity
    │   ├── Course.java        # Course with Builder pattern
    │   ├── Enrollment.java    # Enrollment association
    │   ├── Grade.java         # Grade enumeration with points
    │   ├── Semester.java      # Semester enumeration
    │   ├── DuplicateEnrollmentException.java
    │   └── MaxCreditLimitExceededException.java
    ├── service/               # Business logic layer
    │   ├── StudentService.java    # CRUD operations for students
    │   ├── CourseService.java     # Course management with Stream API
    │   ├── EnrollmentService.java # Enrollment with business rules
    │   └── TranscriptService.java # GPA calculation and transcript generation
    ├── io/                    # File I/O operations
    │   └── CSVFileHandler.java    # NIO.2 CSV import/export
    ├── util/                  # Utility classes
    │   ├── Validators.java        # Input validation with regex
    │   └── recursion.java         # Recursive directory operations
    └── config/                # Configuration management
        └── AppConfig.java         # Singleton configuration

    <img width="840" height="545" alt="Screenshot 2025-09-24 194948" src="https://github.com/user-attachments/assets/4ca65086-7890-4ff7-ac3a-5921adbe7223" />
    <img width="881" height="525" alt="Screenshot 2025-09-24 195015" src="https://github.com/user-attachments/assets/b3218c41-18f7-45fc-8cf5-2658937740be" />
    
Test Data
Sample CSV Format
students.csv:
<img width="916" height="502" alt="Screenshot 2025-09-24 234205" src="https://github.com/user-attachments/assets/ce900ee8-1216-4b77-9c88-139c3ed90d0a" />

courses.csv:

Platform Summary
Java SE vs ME vs EE: This CCRM project uses Java SE (Standard Edition), providing the complete Java platform suitable for desktop applications with full access to core libraries, file I/O, networking, and GUI capabilities. Unlike Java ME (limited for mobile/embedded devices) or Java EE (enterprise web applications with application servers), Java SE offers the ideal balance of functionality and simplicity for educational management systems.

Known Issues & Future Enhancements
Current Limitations

In-memory storage (data lost on restart)
Basic console interface
Limited CSV validation

Planned Improvements

Database persistence (JDBC)
Web interface (Spring Boot)
Advanced reporting features
Unit testing with JUnit

Academic Integrity Statement
This project represents original work demonstrating comprehensive understanding of Java SE concepts including OOP principles, design patterns, functional programming, file I/O, and modern Java features. All implementations follow academic best practices and coding standards.
Acknowledgments

Java SE documentation and Oracle tutorials
Eclipse IDE development environment
Academic course requirements and specifications
Design pattern references and best practices

*Author*: [Sanjana Mall]  
*Student ID*: [24BCE11218]  
*Course*: CSE2006: Programming in Java  
*Date*: [25/09/2025]

