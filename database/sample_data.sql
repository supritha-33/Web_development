-- Sample Data for Student Management System
USE student_management_system;

-- Insert sample courses
INSERT INTO courses (course_name, description) VALUES
('Computer Science', 'Comprehensive program covering programming, algorithms, and software development'),
('Business Administration', 'Strategic management, finance, marketing, and entrepreneurship'),
('Electrical Engineering', 'Circuit design, power systems, and electronic device development'),
('Psychology', 'Human behavior, mental processes, and therapeutic techniques'),
('Mechanical Engineering', 'Design and manufacturing of mechanical systems and devices'),
('Data Science', 'Statistics, machine learning, and big data analytics'),
('Marketing', 'Brand management, digital marketing, and consumer behavior'),
('Nursing', 'Healthcare delivery, patient care, and medical procedures');

-- Insert sample students
INSERT INTO students (name, age, email, course_id) VALUES
('John Smith', 20, 'john.smith@email.com', 1),
('Emma Johnson', 22, 'emma.johnson@email.com', 2),
('Michael Brown', 19, 'michael.brown@email.com', 1),
('Sarah Davis', 21, 'sarah.davis@email.com', 4),
('David Wilson', 23, 'david.wilson@email.com', 3),
('Lisa Anderson', 20, 'lisa.anderson@email.com', 6),
('James Martinez', 24, 'james.martinez@email.com', 5),
('Jennifer Taylor', 22, 'jennifer.taylor@email.com', 8),
('Robert Garcia', 18, 'robert.garcia@email.com', 1),
('Ashley Rodriguez', 25, 'ashley.rodriguez@email.com', 7),
('Christopher Lee', 21, 'christopher.lee@email.com', 3),
('Amanda White', 19, 'amanda.white@email.com', 2),
('Matthew Thompson', 26, 'matthew.thompson@email.com', 5),
('Jessica Moore', 20, 'jessica.moore@email.com', 6),
('Daniel Clark', 22, 'daniel.clark@email.com', 4);