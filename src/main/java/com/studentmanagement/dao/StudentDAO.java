package com.studentmanagement.dao;

import com.studentmanagement.model.Student;
import com.studentmanagement.exception.StudentManagementException;
import java.util.List;

/**
 * Data Access Object interface for Student entity
 */
public interface StudentDAO {
    
    /**
     * Add a new student
     * @param student Student object to add
     * @return generated student ID
     * @throws StudentManagementException if operation fails
     */
    int addStudent(Student student) throws StudentManagementException;
    
    /**
     * Get student by ID with course information
     * @param studentId student ID
     * @return Student object with course name or null if not found
     * @throws StudentManagementException if operation fails
     */
    Student getStudentById(int studentId) throws StudentManagementException;
    
    /**
     * Get student by email
     * @param email student email
     * @return Student object or null if not found
     * @throws StudentManagementException if operation fails
     */
    Student getStudentByEmail(String email) throws StudentManagementException;
    
    /**
     * Get all students with course information using JOIN
     * @return List of all students with course names
     * @throws StudentManagementException if operation fails
     */
    List<Student> getAllStudents() throws StudentManagementException;
    
    /**
     * Update an existing student
     * @param student Student object with updated information
     * @return true if update successful
     * @throws StudentManagementException if operation fails
     */
    boolean updateStudent(Student student) throws StudentManagementException;
    
    /**
     * Delete a student by ID
     * @param studentId student ID to delete
     * @return true if deletion successful
     * @throws StudentManagementException if operation fails
     */
    boolean deleteStudent(int studentId) throws StudentManagementException;
    
    /**
     * Search students by name pattern
     * @param namePattern pattern to search for
     * @return List of matching students with course information
     * @throws StudentManagementException if operation fails
     */
    List<Student> searchStudentsByName(String namePattern) throws StudentManagementException;
    
    /**
     * Get students by course ID
     * @param courseId course ID
     * @return List of students in the specified course
     * @throws StudentManagementException if operation fails
     */
    List<Student> getStudentsByCourse(int courseId) throws StudentManagementException;
    
    /**
     * Get students by course name
     * @param courseName course name
     * @return List of students in the specified course
     * @throws StudentManagementException if operation fails
     */
    List<Student> getStudentsByCourseName(String courseName) throws StudentManagementException;
    
    /**
     * Get students by age range
     * @param minAge minimum age (inclusive)
     * @param maxAge maximum age (inclusive)
     * @return List of students within the age range
     * @throws StudentManagementException if operation fails
     */
    List<Student> getStudentsByAgeRange(int minAge, int maxAge) throws StudentManagementException;
    
    /**
     * Check if student exists by ID
     * @param studentId student ID
     * @return true if student exists
     * @throws StudentManagementException if operation fails
     */
    boolean studentExists(int studentId) throws StudentManagementException;
    
    /**
     * Check if email is already taken
     * @param email email to check
     * @param excludeStudentId student ID to exclude from check (for updates)
     * @return true if email is already taken
     * @throws StudentManagementException if operation fails
     */
    boolean isEmailTaken(String email, int excludeStudentId) throws StudentManagementException;
}