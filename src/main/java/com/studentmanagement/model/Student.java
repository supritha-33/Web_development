package com.studentmanagement.model;

import java.sql.Timestamp;

/**
 * Student model class representing a student entity
 */
public class Student {
    private int studentId;
    private String name;
    private int age;
    private String email;
    private int courseId;
    private String courseName; // For JOIN operations
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public Student() {}

    // Constructor with basic fields
    public Student(String name, int age, String email, int courseId) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.courseId = courseId;
    }

    // Constructor with all fields except timestamps
    public Student(int studentId, String name, int age, String email, int courseId) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.courseId = courseId;
    }

    // Constructor with course name (for JOIN operations)
    public Student(int studentId, String name, int age, String email, int courseId, String courseName) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.courseId = courseId;
        this.courseName = courseName;
    }

    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return studentId == student.studentId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(studentId);
    }
}