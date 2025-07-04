package com.studentmanagement.dao.impl;

import com.studentmanagement.dao.StudentDAO;
import com.studentmanagement.model.Student;
import com.studentmanagement.exception.StudentManagementException;
import com.studentmanagement.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of StudentDAO interface using JDBC with JOIN operations
 */
public class StudentDAOImpl implements StudentDAO {

    private static final String INSERT_STUDENT = 
        "INSERT INTO students (name, age, email, course_id) VALUES (?, ?, ?, ?)";
    
    private static final String SELECT_STUDENT_BY_ID = 
        "SELECT s.student_id, s.name, s.age, s.email, s.course_id, c.course_name, " +
        "s.created_at, s.updated_at FROM students s " +
        "LEFT JOIN courses c ON s.course_id = c.course_id WHERE s.student_id = ?";
    
    private static final String SELECT_STUDENT_BY_EMAIL = 
        "SELECT s.student_id, s.name, s.age, s.email, s.course_id, c.course_name, " +
        "s.created_at, s.updated_at FROM students s " +
        "LEFT JOIN courses c ON s.course_id = c.course_id WHERE s.email = ?";
    
    private static final String SELECT_ALL_STUDENTS = 
        "SELECT s.student_id, s.name, s.age, s.email, s.course_id, c.course_name, " +
        "s.created_at, s.updated_at FROM students s " +
        "LEFT JOIN courses c ON s.course_id = c.course_id ORDER BY s.name";
    
    private static final String UPDATE_STUDENT = 
        "UPDATE students SET name = ?, age = ?, email = ?, course_id = ? WHERE student_id = ?";
    
    private static final String DELETE_STUDENT = 
        "DELETE FROM students WHERE student_id = ?";
    
    private static final String SEARCH_STUDENTS_BY_NAME = 
        "SELECT s.student_id, s.name, s.age, s.email, s.course_id, c.course_name, " +
        "s.created_at, s.updated_at FROM students s " +
        "LEFT JOIN courses c ON s.course_id = c.course_id " +
        "WHERE s.name LIKE ? ORDER BY s.name";
    
    private static final String SELECT_STUDENTS_BY_COURSE_ID = 
        "SELECT s.student_id, s.name, s.age, s.email, s.course_id, c.course_name, " +
        "s.created_at, s.updated_at FROM students s " +
        "LEFT JOIN courses c ON s.course_id = c.course_id " +
        "WHERE s.course_id = ? ORDER BY s.name";
    
    private static final String SELECT_STUDENTS_BY_COURSE_NAME = 
        "SELECT s.student_id, s.name, s.age, s.email, s.course_id, c.course_name, " +
        "s.created_at, s.updated_at FROM students s " +
        "LEFT JOIN courses c ON s.course_id = c.course_id " +
        "WHERE c.course_name LIKE ? ORDER BY s.name";
    
    private static final String SELECT_STUDENTS_BY_AGE_RANGE = 
        "SELECT s.student_id, s.name, s.age, s.email, s.course_id, c.course_name, " +
        "s.created_at, s.updated_at FROM students s " +
        "LEFT JOIN courses c ON s.course_id = c.course_id " +
        "WHERE s.age BETWEEN ? AND ? ORDER BY s.age, s.name";
    
    private static final String CHECK_STUDENT_EXISTS = 
        "SELECT COUNT(*) FROM students WHERE student_id = ?";
    
    private static final String CHECK_EMAIL_TAKEN = 
        "SELECT COUNT(*) FROM students WHERE email = ? AND student_id != ?";

    @Override
    public int addStudent(Student student) throws StudentManagementException {
        validateStudent(student);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_STUDENT, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, student.getName().trim());
            statement.setInt(2, student.getAge());
            statement.setString(3, student.getEmail().trim().toLowerCase());
            statement.setInt(4, student.getCourseId());
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new StudentManagementException("Failed to add student");
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new StudentManagementException("Failed to get generated student ID");
                }
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("email")) {
                throw new StudentManagementException("Email already exists");
            }
            throw new StudentManagementException("Database error while adding student", e);
        }
    }

    @Override
    public Student getStudentById(int studentId) throws StudentManagementException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT_BY_ID)) {
            
            statement.setInt(1, studentId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToStudent(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while fetching student by ID", e);
        }
    }

    @Override
    public Student getStudentByEmail(String email) throws StudentManagementException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT_BY_EMAIL)) {
            
            statement.setString(1, email.trim().toLowerCase());
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToStudent(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while fetching student by email", e);
        }
    }

    @Override
    public List<Student> getAllStudents() throws StudentManagementException {
        List<Student> students = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STUDENTS);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                students.add(mapResultSetToStudent(resultSet));
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while fetching all students", e);
        }
        
        return students;
    }

    @Override
    public boolean updateStudent(Student student) throws StudentManagementException {
        validateStudent(student);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT)) {
            
            statement.setString(1, student.getName().trim());
            statement.setInt(2, student.getAge());
            statement.setString(3, student.getEmail().trim().toLowerCase());
            statement.setInt(4, student.getCourseId());
            statement.setInt(5, student.getStudentId());
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getMessage().contains("email")) {
                throw new StudentManagementException("Email already exists");
            }
            throw new StudentManagementException("Database error while updating student", e);
        }
    }

    @Override
    public boolean deleteStudent(int studentId) throws StudentManagementException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT)) {
            
            statement.setInt(1, studentId);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while deleting student", e);
        }
    }

    @Override
    public List<Student> searchStudentsByName(String namePattern) throws StudentManagementException {
        List<Student> students = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_STUDENTS_BY_NAME)) {
            
            statement.setString(1, "%" + namePattern + "%");
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(mapResultSetToStudent(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while searching students by name", e);
        }
        
        return students;
    }

    @Override
    public List<Student> getStudentsByCourse(int courseId) throws StudentManagementException {
        List<Student> students = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STUDENTS_BY_COURSE_ID)) {
            
            statement.setInt(1, courseId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(mapResultSetToStudent(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while fetching students by course", e);
        }
        
        return students;
    }

    @Override
    public List<Student> getStudentsByCourseName(String courseName) throws StudentManagementException {
        List<Student> students = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STUDENTS_BY_COURSE_NAME)) {
            
            statement.setString(1, "%" + courseName + "%");
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(mapResultSetToStudent(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while fetching students by course name", e);
        }
        
        return students;
    }

    @Override
    public List<Student> getStudentsByAgeRange(int minAge, int maxAge) throws StudentManagementException {
        if (minAge < 0 || maxAge < 0 || minAge > maxAge) {
            throw new StudentManagementException("Invalid age range");
        }

        List<Student> students = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STUDENTS_BY_AGE_RANGE)) {
            
            statement.setInt(1, minAge);
            statement.setInt(2, maxAge);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(mapResultSetToStudent(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while fetching students by age range", e);
        }
        
        return students;
    }

    @Override
    public boolean studentExists(int studentId) throws StudentManagementException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_STUDENT_EXISTS)) {
            
            statement.setInt(1, studentId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while checking student existence", e);
        }
    }

    @Override
    public boolean isEmailTaken(String email, int excludeStudentId) throws StudentManagementException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_EMAIL_TAKEN)) {
            
            statement.setString(1, email.trim().toLowerCase());
            statement.setInt(2, excludeStudentId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new StudentManagementException("Database error while checking email availability", e);
        }
    }

    /**
     * Map ResultSet to Student object with course information
     */
    private Student mapResultSetToStudent(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setStudentId(resultSet.getInt("student_id"));
        student.setName(resultSet.getString("name"));
        student.setAge(resultSet.getInt("age"));
        student.setEmail(resultSet.getString("email"));
        student.setCourseId(resultSet.getInt("course_id"));
        student.setCourseName(resultSet.getString("course_name"));
        student.setCreatedAt(resultSet.getTimestamp("created_at"));
        student.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        return student;
    }

    /**
     * Validate student data
     */
    private void validateStudent(Student student) throws StudentManagementException {
        if (student == null) {
            throw new StudentManagementException("Student cannot be null");
        }
        
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new StudentManagementException("Student name cannot be null or empty");
        }
        
        if (student.getAge() < 16 || student.getAge() > 100) {
            throw new StudentManagementException("Student age must be between 16 and 100");
        }
        
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            throw new StudentManagementException("Student email cannot be null or empty");
        }
        
        if (!isValidEmail(student.getEmail())) {
            throw new StudentManagementException("Invalid email format");
        }
        
        if (student.getCourseId() <= 0) {
            throw new StudentManagementException("Invalid course ID");
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