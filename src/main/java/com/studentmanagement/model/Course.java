package com.studentmanagement.model;

import java.sql.Timestamp;

/**
 * Course model class representing a course entity
 */
public class Course {
    private int courseId;
    private String courseName;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public Course() {}

    // Constructor with parameters
    public Course(String courseName, String description) {
        this.courseName = courseName;
        this.description = description;
    }

    // Constructor with all fields except timestamps
    public Course(int courseId, String courseName, String description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
    }

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return courseId == course.courseId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(courseId);
    }
}