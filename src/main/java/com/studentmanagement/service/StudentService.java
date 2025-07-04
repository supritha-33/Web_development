package com.studentmanagement.service;

import com.studentmanagement.dao.StudentDAO;
import com.studentmanagement.dao.CourseDAO;
import com.studentmanagement.dao.impl.StudentDAOImpl;
import com.studentmanagement.dao.impl.CourseDAOImpl;
import com.studentmanagement.model.Student;
import com.studentmanagement.model.Course;
import com.studentmanagement.exception.StudentManagementException;

import java.util.List;

/**
 * Service class for Student management with business logic
 */
public class StudentService {
    
    private final StudentDAO studentDAO;
    private final CourseDAO courseDAO;
    
    public StudentService() {
        this.studentDAO = new StudentDAOImpl();
        this.courseDAO = new CourseDAOImpl();
    }
    
    public StudentService(StudentDAO studentDAO, CourseDAO courseDAO) {
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
    }
    
    /**
     * Add a new student with validation
     */
    public int addStudent(Student student) throws StudentManagementException {
        // Validate that the course exists
        if (!courseDAO.courseExists(student.getCourseId())) {
            throw new StudentManagementException("Course with ID " + student.getCourseId() + " does not exist");
        }
        
        // Check if email is already taken
        if (studentDAO.isEmailTaken(student.getEmail(), 0)) {
            throw new StudentManagementException("Email address is already in use");
        }
        
        return studentDAO.addStudent(student);
    }
    
    /**
     * Get student by ID
     */
    public Student getStudentById(int studentId) throws StudentManagementException {
        return studentDAO.getStudentById(studentId);
    }
    
    /**
     * Get student by email
     */
    public Student getStudentByEmail(String email) throws StudentManagementException {
        return studentDAO.getStudentByEmail(email);
    }
    
    /**
     * Get all students
     */
    public List<Student> getAllStudents() throws StudentManagementException {
        return studentDAO.getAllStudents();
    }
    
    /**
     * Update student with validation
     */
    public boolean updateStudent(Student student) throws StudentManagementException {
        // Check if student exists
        if (!studentDAO.studentExists(student.getStudentId())) {
            throw new StudentManagementException("Student with ID " + student.getStudentId() + " does not exist");
        }
        
        // Validate that the course exists
        if (!courseDAO.courseExists(student.getCourseId())) {
            throw new StudentManagementException("Course with ID " + student.getCourseId() + " does not exist");
        }
        
        // Check if email is already taken by another student
        if (studentDAO.isEmailTaken(student.getEmail(), student.getStudentId())) {
            throw new StudentManagementException("Email address is already in use by another student");
        }
        
        return studentDAO.updateStudent(student);
    }
    
    /**
     * Delete student
     */
    public boolean deleteStudent(int studentId) throws StudentManagementException {
        if (!studentDAO.studentExists(studentId)) {
            throw new StudentManagementException("Student with ID " + studentId + " does not exist");
        }
        
        return studentDAO.deleteStudent(studentId);
    }
    
    /**
     * Search students by name
     */
    public List<Student> searchStudentsByName(String namePattern) throws StudentManagementException {
        if (namePattern == null || namePattern.trim().isEmpty()) {
            return getAllStudents();
        }
        return studentDAO.searchStudentsByName(namePattern.trim());
    }
    
    /**
     * Get students by course
     */
    public List<Student> getStudentsByCourse(int courseId) throws StudentManagementException {
        if (!courseDAO.courseExists(courseId)) {
            throw new StudentManagementException("Course with ID " + courseId + " does not exist");
        }
        return studentDAO.getStudentsByCourse(courseId);
    }
    
    /**
     * Get students by course name
     */
    public List<Student> getStudentsByCourseName(String courseName) throws StudentManagementException {
        if (courseName == null || courseName.trim().isEmpty()) {
            return getAllStudents();
        }
        return studentDAO.getStudentsByCourseName(courseName.trim());
    }
    
    /**
     * Get students by age range
     */
    public List<Student> getStudentsByAgeRange(int minAge, int maxAge) throws StudentManagementException {
        return studentDAO.getStudentsByAgeRange(minAge, maxAge);
    }
    
    /**
     * Check if student exists
     */
    public boolean studentExists(int studentId) throws StudentManagementException {
        return studentDAO.studentExists(studentId);
    }
    
    /**
     * Validate student data for forms
     */
    public void validateStudentData(String name, String ageStr, String email, String courseIdStr) 
            throws StudentManagementException {
        
        if (name == null || name.trim().isEmpty()) {
            throw new StudentManagementException("Name is required");
        }
        
        if (ageStr == null || ageStr.trim().isEmpty()) {
            throw new StudentManagementException("Age is required");
        }
        
        int age;
        try {
            age = Integer.parseInt(ageStr.trim());
        } catch (NumberFormatException e) {
            throw new StudentManagementException("Age must be a valid number");
        }
        
        if (age < 16 || age > 100) {
            throw new StudentManagementException("Age must be between 16 and 100");
        }
        
        if (email == null || email.trim().isEmpty()) {
            throw new StudentManagementException("Email is required");
        }
        
        if (!isValidEmail(email)) {
            throw new StudentManagementException("Invalid email format");
        }
        
        if (courseIdStr == null || courseIdStr.trim().isEmpty()) {
            throw new StudentManagementException("Course selection is required");
        }
        
        int courseId;
        try {
            courseId = Integer.parseInt(courseIdStr.trim());
        } catch (NumberFormatException e) {
            throw new StudentManagementException("Invalid course selection");
        }
        
        if (courseId <= 0) {
            throw new StudentManagementException("Invalid course selection");
        }
    }
    
    /**
     * Simple email validation
     */
    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".") && 
               email.length() > 5 && !email.startsWith("@") && !email.endsWith("@");
    }
}