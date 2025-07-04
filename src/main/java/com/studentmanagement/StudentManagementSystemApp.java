package com.studentmanagement;

import com.studentmanagement.service.StudentService;
import com.studentmanagement.service.CourseService;
import com.studentmanagement.model.Student;
import com.studentmanagement.model.Course;
import com.studentmanagement.exception.StudentManagementException;
import com.studentmanagement.util.DatabaseConnection;

import java.util.List;
import java.util.Scanner;

/**
 * Main application class for Student Management System
 * This class provides a console-based interface for testing the system
 */
public class StudentManagementSystemApp {
    
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Student Management System ===");
        System.out.println("Initializing application...");
        
        // Test database connection
        if (!DatabaseConnection.testConnection()) {
            System.err.println("Failed to connect to database. Please check your MySQL configuration.");
            System.err.println("Make sure MySQL is running and the database 'student_management_system' exists.");
            return;
        }
        
        System.out.println("Database connection successful!");
        
        // Show main menu
        showMainMenu();
    }
    
    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Student Management");
            System.out.println("2. Course Management");
            System.out.println("3. Reports");
            System.out.println("4. Demo Data");
            System.out.println("5. Frontend Instructions");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    studentManagementMenu();
                    break;
                case 2:
                    courseManagementMenu();
                    break;
                case 3:
                    showReports();
                    break;
                case 4:
                    showDemoData();
                    break;
                case 5:
                    showFrontendInstructions();
                    break;
                case 0:
                    System.out.println("Thank you for using Student Management System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void studentManagementMenu() {
        while (true) {
            System.out.println("\n=== STUDENT MANAGEMENT ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by Name");
            System.out.println("4. Filter Students by Course");
            System.out.println("5. Filter Students by Age Range");
            System.out.println("6. Update Student");
            System.out.println("7. Delete Student");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudentByName();
                    break;
                case 4:
                    filterStudentsByCourse();
                    break;
                case 5:
                    filterStudentsByAge();
                    break;
                case 6:
                    updateStudent();
                    break;
                case 7:
                    deleteStudent();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void courseManagementMenu() {
        while (true) {
            System.out.println("\n=== COURSE MANAGEMENT ===");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Update Course");
            System.out.println("4. Delete Course");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    viewAllCourses();
                    break;
                case 3:
                    updateCourse();
                    break;
                case 4:
                    deleteCourse();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Student operations
    private static void addStudent() {
        try {
            System.out.println("\n--- Add New Student ---");
            
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter student age: ");
            int age = getIntInput();
            
            System.out.print("Enter student email: ");
            String email = scanner.nextLine();
            
            // Show available courses
            List<Course> courses = courseService.getAllCourses();
            if (courses.isEmpty()) {
                System.out.println("No courses available. Please add courses first.");
                return;
            }
            
            System.out.println("Available courses:");
            for (Course course : courses) {
                System.out.println(course.getCourseId() + ". " + course.getCourseName());
            }
            
            System.out.print("Enter course ID: ");
            int courseId = getIntInput();
            
            Student student = new Student(name, age, email, courseId);
            int studentId = studentService.addStudent(student);
            
            System.out.println("Student added successfully with ID: " + studentId);
            
        } catch (StudentManagementException e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }
    
    private static void viewAllStudents() {
        try {
            System.out.println("\n--- All Students ---");
            List<Student> students = studentService.getAllStudents();
            
            if (students.isEmpty()) {
                System.out.println("No students found.");
                return;
            }
            
            System.out.printf("%-5s %-20s %-5s %-30s %-20s%n", "ID", "Name", "Age", "Email", "Course");
            System.out.println("================================================================================");
            
            for (Student student : students) {
                System.out.printf("%-5d %-20s %-5d %-30s %-20s%n",
                    student.getStudentId(),
                    student.getName(),
                    student.getAge(),
                    student.getEmail(),
                    student.getCourseName() != null ? student.getCourseName() : "N/A"
                );
            }
            
        } catch (StudentManagementException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }
    }
    
    private static void searchStudentByName() {
        try {
            System.out.print("Enter name to search: ");
            String name = scanner.nextLine();
            
            List<Student> students = studentService.searchStudentsByName(name);
            
            if (students.isEmpty()) {
                System.out.println("No students found with name containing: " + name);
                return;
            }
            
            System.out.println("Search results:");
            System.out.printf("%-5s %-20s %-5s %-30s %-20s%n", "ID", "Name", "Age", "Email", "Course");
            System.out.println("================================================================================");
            
            for (Student student : students) {
                System.out.printf("%-5d %-20s %-5d %-30s %-20s%n",
                    student.getStudentId(),
                    student.getName(),
                    student.getAge(),
                    student.getEmail(),
                    student.getCourseName() != null ? student.getCourseName() : "N/A"
                );
            }
            
        } catch (StudentManagementException e) {
            System.err.println("Error searching students: " + e.getMessage());
        }
    }
    
    private static void filterStudentsByCourse() {
        try {
            System.out.print("Enter course name to filter by: ");
            String courseName = scanner.nextLine();
            
            List<Student> students = studentService.getStudentsByCourseName(courseName);
            
            if (students.isEmpty()) {
                System.out.println("No students found in course: " + courseName);
                return;
            }
            
            System.out.println("Students in course '" + courseName + "':");
            System.out.printf("%-5s %-20s %-5s %-30s%n", "ID", "Name", "Age", "Email");
            System.out.println("========================================================");
            
            for (Student student : students) {
                System.out.printf("%-5d %-20s %-5d %-30s%n",
                    student.getStudentId(),
                    student.getName(),
                    student.getAge(),
                    student.getEmail()
                );
            }
            
        } catch (StudentManagementException e) {
            System.err.println("Error filtering students: " + e.getMessage());
        }
    }
    
    private static void filterStudentsByAge() {
        try {
            System.out.print("Enter minimum age: ");
            int minAge = getIntInput();
            
            System.out.print("Enter maximum age: ");
            int maxAge = getIntInput();
            
            List<Student> students = studentService.getStudentsByAgeRange(minAge, maxAge);
            
            if (students.isEmpty()) {
                System.out.println("No students found in age range: " + minAge + "-" + maxAge);
                return;
            }
            
            System.out.println("Students aged " + minAge + "-" + maxAge + ":");
            System.out.printf("%-5s %-20s %-5s %-30s %-20s%n", "ID", "Name", "Age", "Email", "Course");
            System.out.println("================================================================================");
            
            for (Student student : students) {
                System.out.printf("%-5d %-20s %-5d %-30s %-20s%n",
                    student.getStudentId(),
                    student.getName(),
                    student.getAge(),
                    student.getEmail(),
                    student.getCourseName() != null ? student.getCourseName() : "N/A"
                );
            }
            
        } catch (StudentManagementException e) {
            System.err.println("Error filtering students: " + e.getMessage());
        }
    }
    
    private static void updateStudent() {
        try {
            System.out.print("Enter student ID to update: ");
            int studentId = getIntInput();
            
            Student student = studentService.getStudentById(studentId);
            if (student == null) {
                System.out.println("Student not found with ID: " + studentId);
                return;
            }
            
            System.out.println("Current student information:");
            System.out.println("Name: " + student.getName());
            System.out.println("Age: " + student.getAge());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Course: " + (student.getCourseName() != null ? student.getCourseName() : "N/A"));
            
            System.out.print("Enter new name (or press Enter to keep current): ");
            String name = scanner.nextLine();
            if (!name.trim().isEmpty()) {
                student.setName(name);
            }
            
            System.out.print("Enter new age (or 0 to keep current): ");
            int age = getIntInput();
            if (age > 0) {
                student.setAge(age);
            }
            
            System.out.print("Enter new email (or press Enter to keep current): ");
            String email = scanner.nextLine();
            if (!email.trim().isEmpty()) {
                student.setEmail(email);
            }
            
            // Show available courses
            List<Course> courses = courseService.getAllCourses();
            System.out.println("Available courses:");
            for (Course course : courses) {
                System.out.println(course.getCourseId() + ". " + course.getCourseName());
            }
            
            System.out.print("Enter new course ID (or 0 to keep current): ");
            int courseId = getIntInput();
            if (courseId > 0) {
                student.setCourseId(courseId);
            }
            
            boolean updated = studentService.updateStudent(student);
            if (updated) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Failed to update student.");
            }
            
        } catch (StudentManagementException e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
    }
    
    private static void deleteStudent() {
        try {
            System.out.print("Enter student ID to delete: ");
            int studentId = getIntInput();
            
            Student student = studentService.getStudentById(studentId);
            if (student == null) {
                System.out.println("Student not found with ID: " + studentId);
                return;
            }
            
            System.out.println("Student to delete: " + student.getName() + " (" + student.getEmail() + ")");
            System.out.print("Are you sure you want to delete this student? (y/N): ");
            String confirmation = scanner.nextLine();
            
            if ("y".equalsIgnoreCase(confirmation) || "yes".equalsIgnoreCase(confirmation)) {
                boolean deleted = studentService.deleteStudent(studentId);
                if (deleted) {
                    System.out.println("Student deleted successfully!");
                } else {
                    System.out.println("Failed to delete student.");
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
            
        } catch (StudentManagementException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }
    
    // Course operations
    private static void addCourse() {
        try {
            System.out.println("\n--- Add New Course ---");
            
            System.out.print("Enter course name: ");
            String courseName = scanner.nextLine();
            
            System.out.print("Enter course description: ");
            String description = scanner.nextLine();
            
            Course course = new Course(courseName, description);
            int courseId = courseService.addCourse(course);
            
            System.out.println("Course added successfully with ID: " + courseId);
            
        } catch (StudentManagementException e) {
            System.err.println("Error adding course: " + e.getMessage());
        }
    }
    
    private static void viewAllCourses() {
        try {
            System.out.println("\n--- All Courses ---");
            List<Course> courses = courseService.getAllCourses();
            
            if (courses.isEmpty()) {
                System.out.println("No courses found.");
                return;
            }
            
            System.out.printf("%-5s %-30s %-50s%n", "ID", "Course Name", "Description");
            System.out.println("=================================================================================");
            
            for (Course course : courses) {
                System.out.printf("%-5d %-30s %-50s%n",
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getDescription() != null ? course.getDescription() : "N/A"
                );
            }
            
        } catch (StudentManagementException e) {
            System.err.println("Error retrieving courses: " + e.getMessage());
        }
    }
    
    private static void updateCourse() {
        try {
            System.out.print("Enter course ID to update: ");
            int courseId = getIntInput();
            
            Course course = courseService.getCourseById(courseId);
            if (course == null) {
                System.out.println("Course not found with ID: " + courseId);
                return;
            }
            
            System.out.println("Current course information:");
            System.out.println("Name: " + course.getCourseName());
            System.out.println("Description: " + (course.getDescription() != null ? course.getDescription() : "N/A"));
            
            System.out.print("Enter new course name (or press Enter to keep current): ");
            String courseName = scanner.nextLine();
            if (!courseName.trim().isEmpty()) {
                course.setCourseName(courseName);
            }
            
            System.out.print("Enter new description (or press Enter to keep current): ");
            String description = scanner.nextLine();
            if (!description.trim().isEmpty()) {
                course.setDescription(description);
            }
            
            boolean updated = courseService.updateCourse(course);
            if (updated) {
                System.out.println("Course updated successfully!");
            } else {
                System.out.println("Failed to update course.");
            }
            
        } catch (StudentManagementException e) {
            System.err.println("Error updating course: " + e.getMessage());
        }
    }
    
    private static void deleteCourse() {
        try {
            System.out.print("Enter course ID to delete: ");
            int courseId = getIntInput();
            
            Course course = courseService.getCourseById(courseId);
            if (course == null) {
                System.out.println("Course not found with ID: " + courseId);
                return;
            }
            
            System.out.println("Course to delete: " + course.getCourseName());
            System.out.print("Are you sure you want to delete this course? (y/N): ");
            String confirmation = scanner.nextLine();
            
            if ("y".equalsIgnoreCase(confirmation) || "yes".equalsIgnoreCase(confirmation)) {
                boolean deleted = courseService.deleteCourse(courseId);
                if (deleted) {
                    System.out.println("Course deleted successfully!");
                } else {
                    System.out.println("Failed to delete course.");
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
            
        } catch (StudentManagementException e) {
            System.err.println("Error deleting course: " + e.getMessage());
        }
    }
    
    private static void showReports() {
        try {
            System.out.println("\n=== REPORTS ===");
            
            List<Student> students = studentService.getAllStudents();
            List<Course> courses = courseService.getAllCourses();
            
            System.out.println("Total Students: " + students.size());
            System.out.println("Total Courses: " + courses.size());
            
            if (!students.isEmpty()) {
                double avgAge = students.stream().mapToInt(Student::getAge).average().orElse(0.0);
                System.out.printf("Average Student Age: %.1f years%n", avgAge);
                
                // Course enrollment statistics
                System.out.println("\nCourse Enrollment:");
                for (Course course : courses) {
                    long count = students.stream().filter(s -> s.getCourseId() == course.getCourseId()).count();
                    System.out.println("- " + course.getCourseName() + ": " + count + " students");
                }
            }
            
        } catch (StudentManagementException e) {
            System.err.println("Error generating reports: " + e.getMessage());
        }
    }
    
    private static void showDemoData() {
        System.out.println("\n=== DEMO DATA ===");
        System.out.println("This system comes with sample data that gets populated when you run the SQL scripts.");
        System.out.println("To load sample data:");
        System.out.println("1. Run database/init_db.sql to create tables");
        System.out.println("2. Run database/sample_data.sql to populate with sample data");
        System.out.println("\nSample data includes:");
        System.out.println("- 8 sample courses (Computer Science, Business Administration, etc.)");
        System.out.println("- 15 sample students with various ages and courses");
    }
    
    private static void showFrontendInstructions() {
        System.out.println("\n=== FRONTEND INSTRUCTIONS ===");
        System.out.println("The system includes a modern web-based admin panel:");
        System.out.println();
        System.out.println("1. Open frontend/admin.html in your web browser");
        System.out.println("2. The interface includes:");
        System.out.println("   - Dashboard with statistics and quick actions");
        System.out.println("   - Student management with search and filtering");
        System.out.println("   - Course management");
        System.out.println("   - Reports and analytics");
        System.out.println();
        System.out.println("Features:");
        System.out.println("- Responsive Bootstrap design");
        System.out.println("- CRUD operations for students and courses");
        System.out.println("- Search by name, course, and age range");
        System.out.println("- Confirmation dialogs for deletions");
        System.out.println("- Form validation");
        System.out.println("- Modern UI with icons and animations");
        System.out.println();
        System.out.println("Note: The frontend currently uses sample data.");
        System.out.println("To connect to the Java backend, you would need to implement REST APIs.");
    }
    
    // Utility methods
    private static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}