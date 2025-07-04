-- Student Management System Database Schema
-- Create database
CREATE DATABASE IF NOT EXISTS student_management_system;
USE student_management_system;

-- Create courses table
CREATE TABLE IF NOT EXISTS courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create students table with foreign key reference to courses
CREATE TABLE IF NOT EXISTS students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL CHECK (age >= 16 AND age <= 100),
    email VARCHAR(150) UNIQUE NOT NULL,
    course_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE SET NULL
);

-- Create indexes for better performance
CREATE INDEX idx_student_name ON students(name);
CREATE INDEX idx_student_age ON students(age);
CREATE INDEX idx_student_email ON students(email);
CREATE INDEX idx_course_name ON courses(course_name);