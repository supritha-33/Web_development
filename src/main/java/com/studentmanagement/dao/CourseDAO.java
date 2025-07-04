package com.studentmanagement.dao;

import com.studentmanagement.model.Course;
import com.studentmanagement.exception.StudentManagementException;
import java.util.List;

/**
 * Data Access Object interface for Course entity
 */
public interface CourseDAO {
    
    /**
     * Add a new course
     * @param course Course object to add
     * @return generated course ID
     * @throws StudentManagementException if operation fails
     */
    int addCourse(Course course) throws StudentManagementException;
    
    /**
     * Get course by ID
     * @param courseId course ID
     * @return Course object or null if not found
     * @throws StudentManagementException if operation fails
     */
    Course getCourseById(int courseId) throws StudentManagementException;
    
    /**
     * Get course by name
     * @param courseName course name
     * @return Course object or null if not found
     * @throws StudentManagementException if operation fails
     */
    Course getCourseByName(String courseName) throws StudentManagementException;
    
    /**
     * Get all courses
     * @return List of all courses
     * @throws StudentManagementException if operation fails
     */
    List<Course> getAllCourses() throws StudentManagementException;
    
    /**
     * Update an existing course
     * @param course Course object with updated information
     * @return true if update successful
     * @throws StudentManagementException if operation fails
     */
    boolean updateCourse(Course course) throws StudentManagementException;
    
    /**
     * Delete a course by ID
     * @param courseId course ID to delete
     * @return true if deletion successful
     * @throws StudentManagementException if operation fails
     */
    boolean deleteCourse(int courseId) throws StudentManagementException;
    
    /**
     * Check if course exists by ID
     * @param courseId course ID
     * @return true if course exists
     * @throws StudentManagementException if operation fails
     */
    boolean courseExists(int courseId) throws StudentManagementException;
    
    /**
     * Search courses by name pattern
     * @param namePattern pattern to search for
     * @return List of matching courses
     * @throws StudentManagementException if operation fails
     */
    List<Course> searchCoursesByName(String namePattern) throws StudentManagementException;
}