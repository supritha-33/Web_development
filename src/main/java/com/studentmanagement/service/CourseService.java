package com.studentmanagement.service;

import com.studentmanagement.dao.CourseDAO;
import com.studentmanagement.dao.impl.CourseDAOImpl;
import com.studentmanagement.model.Course;
import com.studentmanagement.exception.StudentManagementException;

import java.util.List;

/**
 * Service class for Course management with business logic
 */
public class CourseService {
    
    private final CourseDAO courseDAO;
    
    public CourseService() {
        this.courseDAO = new CourseDAOImpl();
    }
    
    public CourseService(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }
    
    /**
     * Add a new course
     */
    public int addCourse(Course course) throws StudentManagementException {
        // Check if course name already exists
        Course existingCourse = courseDAO.getCourseByName(course.getCourseName());
        if (existingCourse != null) {
            throw new StudentManagementException("Course with name '" + course.getCourseName() + "' already exists");
        }
        
        return courseDAO.addCourse(course);
    }
    
    /**
     * Get course by ID
     */
    public Course getCourseById(int courseId) throws StudentManagementException {
        return courseDAO.getCourseById(courseId);
    }
    
    /**
     * Get course by name
     */
    public Course getCourseByName(String courseName) throws StudentManagementException {
        return courseDAO.getCourseByName(courseName);
    }
    
    /**
     * Get all courses
     */
    public List<Course> getAllCourses() throws StudentManagementException {
        return courseDAO.getAllCourses();
    }
    
    /**
     * Update course
     */
    public boolean updateCourse(Course course) throws StudentManagementException {
        // Check if course exists
        if (!courseDAO.courseExists(course.getCourseId())) {
            throw new StudentManagementException("Course with ID " + course.getCourseId() + " does not exist");
        }
        
        // Check if another course with the same name exists
        Course existingCourse = courseDAO.getCourseByName(course.getCourseName());
        if (existingCourse != null && existingCourse.getCourseId() != course.getCourseId()) {
            throw new StudentManagementException("Another course with name '" + course.getCourseName() + "' already exists");
        }
        
        return courseDAO.updateCourse(course);
    }
    
    /**
     * Delete course
     */
    public boolean deleteCourse(int courseId) throws StudentManagementException {
        if (!courseDAO.courseExists(courseId)) {
            throw new StudentManagementException("Course with ID " + courseId + " does not exist");
        }
        
        return courseDAO.deleteCourse(courseId);
    }
    
    /**
     * Check if course exists
     */
    public boolean courseExists(int courseId) throws StudentManagementException {
        return courseDAO.courseExists(courseId);
    }
    
    /**
     * Search courses by name
     */
    public List<Course> searchCoursesByName(String namePattern) throws StudentManagementException {
        if (namePattern == null || namePattern.trim().isEmpty()) {
            return getAllCourses();
        }
        return courseDAO.searchCoursesByName(namePattern.trim());
    }
    
    /**
     * Validate course data for forms
     */
    public void validateCourseData(String courseName, String description) throws StudentManagementException {
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new StudentManagementException("Course name is required");
        }
        
        if (courseName.trim().length() < 3) {
            throw new StudentManagementException("Course name must be at least 3 characters long");
        }
        
        if (courseName.trim().length() > 100) {
            throw new StudentManagementException("Course name cannot exceed 100 characters");
        }
        
        if (description != null && description.length() > 1000) {
            throw new StudentManagementException("Course description cannot exceed 1000 characters");
        }
    }
}