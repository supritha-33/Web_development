# Student Management System

A comprehensive student management system built with Java, MySQL, and Bootstrap. Features full CRUD operations, advanced search functionality, and a modern responsive admin panel.

## 🌟 Features

### Core Functionality
- **Student Management**: Complete CRUD operations for student records
- **Course Management**: Full course lifecycle management
- **Advanced Search**: Filter students by name, course, and age range
- **Data Validation**: Comprehensive input validation and error handling
- **Responsive Design**: Modern Bootstrap-based admin panel

### Technical Features
- **DAO Pattern**: Clean separation of data access logic
- **MySQL JOINs**: Efficient data retrieval with related information
- **Exception Handling**: Robust error management
- **Form Validation**: Client-side and server-side validation
- **Confirmation Dialogs**: User-friendly deletion confirmations

## 🏗️ Architecture

### Backend (Java)
```
src/main/java/com/studentmanagement/
├── model/
│   ├── Student.java         # Student entity model
│   └── Course.java          # Course entity model
├── dao/
│   ├── StudentDAO.java      # Student data access interface
│   ├── CourseDAO.java       # Course data access interface
│   └── impl/
│       ├── StudentDAOImpl.java   # Student DAO implementation
│       └── CourseDAOImpl.java    # Course DAO implementation
├── service/
│   ├── StudentService.java  # Student business logic
│   └── CourseService.java   # Course business logic
├── util/
│   └── DatabaseConnection.java  # Database connection utility
├── exception/
│   └── StudentManagementException.java  # Custom exceptions
└── StudentManagementSystemApp.java     # Main application class
```

### Frontend
```
frontend/
├── admin.html              # Main admin panel
└── js/
    └── app.js             # JavaScript functionality
```

### Database
```
database/
├── init_db.sql           # Database schema creation
└── sample_data.sql       # Sample data insertion
```

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- Modern web browser

### Database Setup

1. **Create Database**
   ```sql
   mysql -u root -p < database/init_db.sql
   ```

2. **Load Sample Data**
   ```sql
   mysql -u root -p < database/sample_data.sql
   ```

3. **Update Database Configuration**
   Edit `src/main/java/com/studentmanagement/util/DatabaseConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/student_management_system";
   private static final String USERNAME = "your_username";
   private static final String PASSWORD = "your_password";
   ```

### Backend Setup

1. **Clone Repository**
   ```bash
   git clone <repository-url>
   cd student-management-system
   ```

2. **Build Project**
   ```bash
   mvn clean compile
   ```

3. **Run Application**
   ```bash
   mvn exec:java -Dexec.mainClass="com.studentmanagement.StudentManagementSystemApp"
   ```

### Frontend Setup

1. **Open Admin Panel**
   - Navigate to `frontend/admin.html`
   - Open in your web browser
   - No server setup required (uses sample data)

## 📊 Database Schema

### Students Table
```sql
CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL CHECK (age >= 16 AND age <= 100),
    email VARCHAR(150) UNIQUE NOT NULL,
    course_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE SET NULL
);
```

### Courses Table
```sql
CREATE TABLE courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## 🎯 Usage Examples

### Console Application

The system includes a console-based interface for testing:

```java
// Add a new student
Student student = new Student("John Doe", 20, "john.doe@email.com", 1);
int studentId = studentService.addStudent(student);

// Search students by name
List<Student> students = studentService.searchStudentsByName("John");

// Filter by age range
List<Student> youngStudents = studentService.getStudentsByAgeRange(18, 25);

// Get students in a specific course
List<Student> csStudents = studentService.getStudentsByCourseName("Computer Science");
```

### Web Interface

The admin panel provides:
- **Dashboard**: Overview with statistics and quick actions
- **Student Management**: Add, edit, delete, and search students
- **Course Management**: Manage course catalog
- **Reports**: Analytics and data visualization

## 🔍 Search & Filter Features

### Student Search Options
1. **By Name**: Partial name matching
2. **By Course**: Filter students enrolled in specific courses
3. **By Age Range**: Find students within age brackets
4. **Combined Filters**: Use multiple criteria simultaneously

### Example SQL Queries (Used Internally)
```sql
-- Search students with course information (JOIN)
SELECT s.student_id, s.name, s.age, s.email, s.course_id, c.course_name
FROM students s 
LEFT JOIN courses c ON s.course_id = c.course_id 
WHERE s.name LIKE '%search_term%'
ORDER BY s.name;

-- Students by age range
SELECT * FROM students 
WHERE age BETWEEN 20 AND 25 
ORDER BY age, name;
```

## 🛡️ Data Validation

### Student Validation
- **Name**: Required, 1-100 characters
- **Age**: Required, 16-100 years
- **Email**: Required, valid format, unique
- **Course**: Must exist in courses table

### Course Validation
- **Name**: Required, 3-100 characters, unique
- **Description**: Optional, max 1000 characters

## 🎨 Frontend Features

### Modern UI Components
- **Bootstrap 5**: Latest responsive framework
- **Bootstrap Icons**: Comprehensive icon set
- **Gradient Design**: Modern visual aesthetics
- **Hover Effects**: Interactive elements
- **Modal Dialogs**: Clean form interfaces

### JavaScript Functionality
- **Dynamic Forms**: Real-time validation
- **AJAX-Ready**: Prepared for backend integration
- **Search Filters**: Client-side filtering
- **Confirmation Dialogs**: User-friendly confirmations
- **Responsive Tables**: Mobile-friendly data display

## 🔧 Configuration

### Maven Dependencies
```xml
<dependencies>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
    <!-- Additional dependencies in pom.xml -->
</dependencies>
```

### Environment Profiles
- **Development**: Local MySQL setup
- **Production**: Environment variable configuration

## 📈 Sample Data

The system includes comprehensive sample data:

### Courses (8 samples)
- Computer Science
- Business Administration  
- Electrical Engineering
- Psychology
- Mechanical Engineering
- Data Science
- Marketing
- Nursing

### Students (15 samples)
- Diverse age range (18-26 years)
- Various course enrollments
- Realistic email addresses
- Balanced gender representation

## 🚨 Error Handling

### Exception Types
- **ValidationException**: Input validation errors
- **DatabaseException**: Database operation failures
- **EntityNotFoundException**: Record not found errors

### Error Messages
- User-friendly error descriptions
- Specific validation feedback
- Database constraint violations

## 🔮 Future Enhancements

### Planned Features
1. **REST API**: RESTful endpoints for frontend integration
2. **Authentication**: User login and role management
3. **File Upload**: Student photo and document management
4. **Email Integration**: Automated notifications
5. **Advanced Reports**: PDF generation and charts
6. **Bulk Operations**: CSV import/export functionality

### Technical Improvements
- Connection pooling for better performance
- Caching layer for frequently accessed data
- Logging framework integration
- Unit and integration test coverage

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📧 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation

---

**Built with ❤️ using Java, MySQL, and Bootstrap**