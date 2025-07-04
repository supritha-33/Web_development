package com.studentmanagement.dao.impl;

import com.studentmanagement.dao.CourseDAO;
import com.studentmanagement.model.Course;
import com.studentmanagement.exception.StudentManagementException;
import com.studentmanagement.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of CourseDAO interface using JDBC
 */
public class CourseDAOImpl implements CourseDAO {

    private static final String INSERT_COURSE = 
        "INSERT INTO courses (course_name, description) VALUES (?, ?)";
    
    private static final String SELECT_COURSE_BY_ID = 
        "SELECT course_id, course_name, description, created_at, updated_at FROM courses WHERE course_id = ?";
    
    private static final String SELECT_COURSE_BY_NAME = 
        "SELECT course_id, course_name, description, created_at, updated_at FROM courses WHERE course_name = ?";
    
    private static final String SELECT_ALL_COURSES = 
        "SELECT course_id, course_name, description, created_at, updated_at FROM courses ORDER BY course_name";
    
    private static final String UPDATE_COURSE = 
        "UPDATE courses SET course_name = ?, description = ? WHERE course_id = ?";
    
    private static final String DELETE_COURSE = 
        "DELETE FROM courses WHERE course_id = ?";
    
    private static final String CHECK_COURSE_EXISTS = 
        "SELECT COUNT(*) FROM courses WHERE course_id = ?";
    
    private static final String SEARCH_COURSES_BY_NAME = 
        "SELECT course_id, course_name, description, created_at, updated_at FROM courses WHERE course_name LIKE ? ORDER BY course_name";

    @Override
    public int addCourse(Course course) throws StudentManagementException {
        if (course == null || course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            throw new StudentManagementException("Course name cannot be null or empty");
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_COURSE, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, course.getCourseName().trim());
            statement.setString(2, course.getDescription());
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new StudentManagementException("Failed to add course");
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new StudentManagementException("Failed to get generated course ID");
                }
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while adding course", e);
        }
    }

    @Override
    public Course getCourseById(int courseId) throws StudentManagementException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_COURSE_BY_ID)) {
            
            statement.setInt(1, courseId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCourse(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while fetching course by ID", e);
        }
    }

    @Override
    public Course getCourseByName(String courseName) throws StudentManagementException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_COURSE_BY_NAME)) {
            
            statement.setString(1, courseName);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCourse(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while fetching course by name", e);
        }
    }

    @Override
    public List<Course> getAllCourses() throws StudentManagementException {
        List<Course> courses = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_COURSES);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                courses.add(mapResultSetToCourse(resultSet));
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while fetching all courses", e);
        }
        
        return courses;
    }

    @Override
    public boolean updateCourse(Course course) throws StudentManagementException {
        if (course == null || course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            throw new StudentManagementException("Course name cannot be null or empty");
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_COURSE)) {
            
            statement.setString(1, course.getCourseName().trim());
            statement.setString(2, course.getDescription());
            statement.setInt(3, course.getCourseId());
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while updating course", e);
        }
    }

    @Override
    public boolean deleteCourse(int courseId) throws StudentManagementException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_COURSE)) {
            
            statement.setInt(1, courseId);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while deleting course", e);
        }
    }

    @Override
    public boolean courseExists(int courseId) throws StudentManagementException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_COURSE_EXISTS)) {
            
            statement.setInt(1, courseId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while checking course existence", e);
        }
    }

    @Override
    public List<Course> searchCoursesByName(String namePattern) throws StudentManagementException {
        List<Course> courses = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_COURSES_BY_NAME)) {
            
            statement.setString(1, "%" + namePattern + "%");
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    courses.add(mapResultSetToCourse(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while searching courses", e);
        }
        
        return courses;
    }

    /**
     * Map ResultSet to Course object
     */
    private Course mapResultSetToCourse(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setCourseId(resultSet.getInt("course_id"));
        course.setCourseName(resultSet.getString("course_name"));
        course.setDescription(resultSet.getString("description"));
        course.setCreatedAt(resultSet.getTimestamp("created_at"));
        course.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        return course;
    }
}