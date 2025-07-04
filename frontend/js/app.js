// Student Management System - Frontend JavaScript

// Global variables to store data
let students = [];
let courses = [];
let currentStudents = [];

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    showDashboard();
    loadInitialData();
});

// Navigation functions
function showDashboard() {
    hideAllSections();
    document.getElementById('dashboard-section').style.display = 'block';
    updateActiveNav('dashboard');
    loadDashboardData();
}

function showStudentManagement() {
    hideAllSections();
    document.getElementById('student-section').style.display = 'block';
    updateActiveNav('students');
    loadStudents();
    loadCoursesForSelect();
}

function showCourseManagement() {
    hideAllSections();
    document.getElementById('course-section').style.display = 'block';
    updateActiveNav('courses');
    loadCourses();
}

function showReports() {
    hideAllSections();
    document.getElementById('reports-section').style.display = 'block';
    updateActiveNav('reports');
    generateReports();
}

function hideAllSections() {
    const sections = ['dashboard-section', 'student-section', 'course-section', 'reports-section'];
    sections.forEach(section => {
        document.getElementById(section).style.display = 'none';
    });
}

function updateActiveNav(active) {
    // Update navbar
    document.querySelectorAll('.navbar-nav .nav-link').forEach(link => {
        link.classList.remove('active');
    });
    
    // Update sidebar
    document.querySelectorAll('.sidebar .nav-link').forEach(link => {
        link.classList.remove('active');
    });
    
    // Set active based on section
    if (active === 'dashboard') {
        document.querySelector('.navbar-nav .nav-link[onclick="showDashboard()"]').classList.add('active');
        document.querySelector('.sidebar .nav-link[onclick="showDashboard()"]').classList.add('active');
    } else if (active === 'students') {
        document.querySelector('.navbar-nav .nav-link[onclick="showStudentManagement()"]').classList.add('active');
        document.querySelector('.sidebar .nav-link[onclick="showStudentManagement()"]').classList.add('active');
    } else if (active === 'courses') {
        document.querySelector('.navbar-nav .nav-link[onclick="showCourseManagement()"]').classList.add('active');
        document.querySelector('.sidebar .nav-link[onclick="showCourseManagement()"]').classList.add('active');
    } else if (active === 'reports') {
        document.querySelector('.sidebar .nav-link[onclick="showReports()"]').classList.add('active');
    }
}

// Data loading functions
async function loadInitialData() {
    try {
        await Promise.all([loadStudents(), loadCourses()]);
    } catch (error) {
        console.error('Error loading initial data:', error);
        showAlert('Error loading data. Using sample data.', 'warning');
        loadSampleData();
    }
}

function loadSampleData() {
    // Sample courses
    courses = [
        { courseId: 1, courseName: 'Computer Science', description: 'Programming and software development' },
        { courseId: 2, courseName: 'Business Administration', description: 'Management and business strategy' },
        { courseId: 3, courseName: 'Electrical Engineering', description: 'Electronics and circuit design' },
        { courseId: 4, courseName: 'Psychology', description: 'Human behavior and mental processes' },
        { courseId: 5, courseName: 'Mechanical Engineering', description: 'Mechanical systems and design' },
        { courseId: 6, courseName: 'Data Science', description: 'Statistics and machine learning' }
    ];

    // Sample students
    students = [
        { studentId: 1, name: 'John Smith', age: 20, email: 'john.smith@email.com', courseId: 1, courseName: 'Computer Science' },
        { studentId: 2, name: 'Emma Johnson', age: 22, email: 'emma.johnson@email.com', courseId: 2, courseName: 'Business Administration' },
        { studentId: 3, name: 'Michael Brown', age: 19, email: 'michael.brown@email.com', courseId: 1, courseName: 'Computer Science' },
        { studentId: 4, name: 'Sarah Davis', age: 21, email: 'sarah.davis@email.com', courseId: 4, courseName: 'Psychology' },
        { studentId: 5, name: 'David Wilson', age: 23, email: 'david.wilson@email.com', courseId: 3, courseName: 'Electrical Engineering' },
        { studentId: 6, name: 'Lisa Anderson', age: 20, email: 'lisa.anderson@email.com', courseId: 6, courseName: 'Data Science' },
        { studentId: 7, name: 'James Martinez', age: 24, email: 'james.martinez@email.com', courseId: 5, courseName: 'Mechanical Engineering' },
        { studentId: 8, name: 'Jennifer Taylor', age: 22, email: 'jennifer.taylor@email.com', courseId: 4, courseName: 'Psychology' }
    ];

    currentStudents = [...students];
}

async function loadStudents() {
    try {
        // In a real application, this would be an API call
        // const response = await fetch('/api/students');
        // students = await response.json();
        
        // For demo purposes, use sample data
        if (students.length === 0) {
            loadSampleData();
        }
        currentStudents = [...students];
        displayStudents(currentStudents);
    } catch (error) {
        console.error('Error loading students:', error);
        showAlert('Error loading students', 'error');
    }
}

async function loadCourses() {
    try {
        // In a real application, this would be an API call
        // const response = await fetch('/api/courses');
        // courses = await response.json();
        
        // For demo purposes, use sample data
        if (courses.length === 0) {
            loadSampleData();
        }
        displayCourses(courses);
    } catch (error) {
        console.error('Error loading courses:', error);
        showAlert('Error loading courses', 'error');
    }
}

function loadCoursesForSelect() {
    const selectElements = ['student-course', 'edit-student-course', 'filter-course'];
    
    selectElements.forEach(elementId => {
        const select = document.getElementById(elementId);
        if (select) {
            // Clear existing options except the first one
            while (select.children.length > 1) {
                select.removeChild(select.lastChild);
            }
            
            courses.forEach(course => {
                const option = document.createElement('option');
                option.value = course.courseId;
                option.textContent = course.courseName;
                select.appendChild(option);
            });
        }
    });
}

// Display functions
function displayStudents(studentsToShow) {
    const tbody = document.getElementById('students-table-body');
    tbody.innerHTML = '';

    if (studentsToShow.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="text-center">No students found</td></tr>';
        return;
    }

    studentsToShow.forEach(student => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${student.studentId}</td>
            <td>${student.name}</td>
            <td>${student.age}</td>
            <td>${student.email}</td>
            <td>${student.courseName || 'N/A'}</td>
            <td>
                <button class="btn btn-sm btn-outline-primary me-1" onclick="editStudent(${student.studentId})">
                    <i class="bi bi-pencil"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger" onclick="deleteStudent(${student.studentId})">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function displayCourses(coursesToShow) {
    const tbody = document.getElementById('courses-table-body');
    tbody.innerHTML = '';

    if (coursesToShow.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="text-center">No courses found</td></tr>';
        return;
    }

    coursesToShow.forEach(course => {
        const studentCount = students.filter(s => s.courseId === course.courseId).length;
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${course.courseId}</td>
            <td>${course.courseName}</td>
            <td>${course.description || 'N/A'}</td>
            <td><span class="badge bg-primary">${studentCount}</span></td>
            <td>
                <button class="btn btn-sm btn-outline-primary me-1" onclick="editCourse(${course.courseId})">
                    <i class="bi bi-pencil"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger" onclick="deleteCourse(${course.courseId})">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

// Dashboard functions
function loadDashboardData() {
    // Update statistics
    document.getElementById('total-students').textContent = students.length;
    document.getElementById('total-courses').textContent = courses.length;
    
    // Calculate average age
    if (students.length > 0) {
        const avgAge = (students.reduce((sum, student) => sum + student.age, 0) / students.length).toFixed(1);
        document.getElementById('avg-age').textContent = avgAge;
    } else {
        document.getElementById('avg-age').textContent = '0';
    }
    
    // Find most popular course
    const courseCounts = {};
    students.forEach(student => {
        courseCounts[student.courseName] = (courseCounts[student.courseName] || 0) + 1;
    });
    
    let popularCourse = '-';
    let maxCount = 0;
    for (const [course, count] of Object.entries(courseCounts)) {
        if (count > maxCount) {
            maxCount = count;
            popularCourse = course;
        }
    }
    
    document.getElementById('popular-course').textContent = popularCourse;
}

// Search and filter functions
function searchStudents() {
    const name = document.getElementById('search-name').value.toLowerCase();
    const courseId = document.getElementById('filter-course').value;
    const minAge = parseInt(document.getElementById('min-age').value) || 0;
    const maxAge = parseInt(document.getElementById('max-age').value) || 999;

    let filtered = students.filter(student => {
        const nameMatch = !name || student.name.toLowerCase().includes(name);
        const courseMatch = !courseId || student.courseId.toString() === courseId;
        const ageMatch = student.age >= minAge && student.age <= maxAge;
        
        return nameMatch && courseMatch && ageMatch;
    });

    currentStudents = filtered;
    displayStudents(currentStudents);
}

// Student CRUD operations
function showAddStudentModal() {
    loadCoursesForSelect();
    const modal = new bootstrap.Modal(document.getElementById('addStudentModal'));
    document.getElementById('add-student-form').reset();
    modal.show();
}

function addStudent() {
    const form = document.getElementById('add-student-form');
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    const name = document.getElementById('student-name').value;
    const age = parseInt(document.getElementById('student-age').value);
    const email = document.getElementById('student-email').value;
    const courseId = parseInt(document.getElementById('student-course').value);

    // Validate email uniqueness
    if (students.some(s => s.email.toLowerCase() === email.toLowerCase())) {
        showAlert('Email address already exists', 'error');
        return;
    }

    // Find course name
    const course = courses.find(c => c.courseId === courseId);
    const courseName = course ? course.courseName : 'Unknown';

    // Create new student
    const newStudent = {
        studentId: Math.max(...students.map(s => s.studentId), 0) + 1,
        name,
        age,
        email,
        courseId,
        courseName
    };

    students.push(newStudent);
    currentStudents = [...students];
    
    // Close modal and refresh display
    bootstrap.Modal.getInstance(document.getElementById('addStudentModal')).hide();
    displayStudents(currentStudents);
    loadDashboardData();
    
    showAlert('Student added successfully!', 'success');
}

function editStudent(studentId) {
    const student = students.find(s => s.studentId === studentId);
    if (!student) {
        showAlert('Student not found', 'error');
        return;
    }

    // Create edit modal if it doesn't exist
    let editModal = document.getElementById('editStudentModal');
    if (!editModal) {
        editModal = createEditStudentModal();
    }

    // Populate form
    document.getElementById('edit-student-id').value = student.studentId;
    document.getElementById('edit-student-name').value = student.name;
    document.getElementById('edit-student-age').value = student.age;
    document.getElementById('edit-student-email').value = student.email;
    
    loadCoursesForSelect();
    document.getElementById('edit-student-course').value = student.courseId;

    const modal = new bootstrap.Modal(editModal);
    modal.show();
}

function updateStudent() {
    const studentId = parseInt(document.getElementById('edit-student-id').value);
    const name = document.getElementById('edit-student-name').value;
    const age = parseInt(document.getElementById('edit-student-age').value);
    const email = document.getElementById('edit-student-email').value;
    const courseId = parseInt(document.getElementById('edit-student-course').value);

    // Validate email uniqueness (excluding current student)
    if (students.some(s => s.studentId !== studentId && s.email.toLowerCase() === email.toLowerCase())) {
        showAlert('Email address already exists', 'error');
        return;
    }

    // Find and update student
    const studentIndex = students.findIndex(s => s.studentId === studentId);
    if (studentIndex === -1) {
        showAlert('Student not found', 'error');
        return;
    }

    // Find course name
    const course = courses.find(c => c.courseId === courseId);
    const courseName = course ? course.courseName : 'Unknown';

    students[studentIndex] = {
        ...students[studentIndex],
        name,
        age,
        email,
        courseId,
        courseName
    };

    currentStudents = [...students];
    
    // Close modal and refresh display
    bootstrap.Modal.getInstance(document.getElementById('editStudentModal')).hide();
    displayStudents(currentStudents);
    loadDashboardData();
    
    showAlert('Student updated successfully!', 'success');
}

function deleteStudent(studentId) {
    const student = students.find(s => s.studentId === studentId);
    if (!student) {
        showAlert('Student not found', 'error');
        return;
    }

    if (confirm(`Are you sure you want to delete ${student.name}? This action cannot be undone.`)) {
        students = students.filter(s => s.studentId !== studentId);
        currentStudents = [...students];
        displayStudents(currentStudents);
        loadDashboardData();
        showAlert('Student deleted successfully!', 'success');
    }
}

// Course CRUD operations
function showAddCourseModal() {
    const modal = new bootstrap.Modal(document.getElementById('addCourseModal'));
    document.getElementById('add-course-form').reset();
    modal.show();
}

function addCourse() {
    const form = document.getElementById('add-course-form');
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    const courseName = document.getElementById('course-name').value;
    const description = document.getElementById('course-description').value;

    // Validate course name uniqueness
    if (courses.some(c => c.courseName.toLowerCase() === courseName.toLowerCase())) {
        showAlert('Course name already exists', 'error');
        return;
    }

    // Create new course
    const newCourse = {
        courseId: Math.max(...courses.map(c => c.courseId), 0) + 1,
        courseName,
        description
    };

    courses.push(newCourse);
    
    // Close modal and refresh display
    bootstrap.Modal.getInstance(document.getElementById('addCourseModal')).hide();
    displayCourses(courses);
    loadCoursesForSelect();
    loadDashboardData();
    
    showAlert('Course added successfully!', 'success');
}

function editCourse(courseId) {
    const course = courses.find(c => c.courseId === courseId);
    if (!course) {
        showAlert('Course not found', 'error');
        return;
    }

    // Create edit modal if it doesn't exist
    let editModal = document.getElementById('editCourseModal');
    if (!editModal) {
        editModal = createEditCourseModal();
    }

    // Populate form
    document.getElementById('edit-course-id').value = course.courseId;
    document.getElementById('edit-course-name').value = course.courseName;
    document.getElementById('edit-course-description').value = course.description || '';

    const modal = new bootstrap.Modal(editModal);
    modal.show();
}

function updateCourse() {
    const courseId = parseInt(document.getElementById('edit-course-id').value);
    const courseName = document.getElementById('edit-course-name').value;
    const description = document.getElementById('edit-course-description').value;

    // Validate course name uniqueness (excluding current course)
    if (courses.some(c => c.courseId !== courseId && c.courseName.toLowerCase() === courseName.toLowerCase())) {
        showAlert('Course name already exists', 'error');
        return;
    }

    // Find and update course
    const courseIndex = courses.findIndex(c => c.courseId === courseId);
    if (courseIndex === -1) {
        showAlert('Course not found', 'error');
        return;
    }

    const oldCourseName = courses[courseIndex].courseName;
    courses[courseIndex] = {
        ...courses[courseIndex],
        courseName,
        description
    };

    // Update course name in students
    students.forEach(student => {
        if (student.courseId === courseId) {
            student.courseName = courseName;
        }
    });

    currentStudents = [...students];
    
    // Close modal and refresh display
    bootstrap.Modal.getInstance(document.getElementById('editCourseModal')).hide();
    displayCourses(courses);
    displayStudents(currentStudents);
    loadCoursesForSelect();
    loadDashboardData();
    
    showAlert('Course updated successfully!', 'success');
}

function deleteCourse(courseId) {
    const course = courses.find(c => c.courseId === courseId);
    if (!course) {
        showAlert('Course not found', 'error');
        return;
    }

    const enrolledStudents = students.filter(s => s.courseId === courseId);
    if (enrolledStudents.length > 0) {
        showAlert(`Cannot delete course "${course.courseName}" because ${enrolledStudents.length} student(s) are enrolled in it.`, 'error');
        return;
    }

    if (confirm(`Are you sure you want to delete the course "${course.courseName}"? This action cannot be undone.`)) {
        courses = courses.filter(c => c.courseId !== courseId);
        displayCourses(courses);
        loadCoursesForSelect();
        loadDashboardData();
        showAlert('Course deleted successfully!', 'success');
    }
}

// Utility functions
function createEditStudentModal() {
    const modalHTML = `
        <div class="modal fade" id="editStudentModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Student</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <form id="edit-student-form">
                            <input type="hidden" id="edit-student-id">
                            <div class="mb-3">
                                <label for="edit-student-name" class="form-label">Name *</label>
                                <input type="text" class="form-control" id="edit-student-name" required>
                            </div>
                            <div class="mb-3">
                                <label for="edit-student-age" class="form-label">Age *</label>
                                <input type="number" class="form-control" id="edit-student-age" min="16" max="100" required>
                            </div>
                            <div class="mb-3">
                                <label for="edit-student-email" class="form-label">Email *</label>
                                <input type="email" class="form-control" id="edit-student-email" required>
                            </div>
                            <div class="mb-3">
                                <label for="edit-student-course" class="form-label">Course *</label>
                                <select class="form-control" id="edit-student-course" required>
                                    <option value="">Select a course</option>
                                </select>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-custom" onclick="updateStudent()">Update Student</button>
                    </div>
                </div>
            </div>
        </div>
    `;
    
    document.body.insertAdjacentHTML('beforeend', modalHTML);
    return document.getElementById('editStudentModal');
}

function createEditCourseModal() {
    const modalHTML = `
        <div class="modal fade" id="editCourseModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Course</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <form id="edit-course-form">
                            <input type="hidden" id="edit-course-id">
                            <div class="mb-3">
                                <label for="edit-course-name" class="form-label">Course Name *</label>
                                <input type="text" class="form-control" id="edit-course-name" required>
                            </div>
                            <div class="mb-3">
                                <label for="edit-course-description" class="form-label">Description</label>
                                <textarea class="form-control" id="edit-course-description" rows="3"></textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-custom" onclick="updateCourse()">Update Course</button>
                    </div>
                </div>
            </div>
        </div>
    `;
    
    document.body.insertAdjacentHTML('beforeend', modalHTML);
    return document.getElementById('editCourseModal');
}

function showAlert(message, type) {
    const alertClass = type === 'success' ? 'alert-success' : 
                     type === 'error' ? 'alert-danger' : 
                     type === 'warning' ? 'alert-warning' : 'alert-info';
    
    const alertHTML = `
        <div class="alert ${alertClass} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `;
    
    // Remove existing alerts
    const existingAlerts = document.querySelectorAll('.alert');
    existingAlerts.forEach(alert => alert.remove());
    
    // Add new alert at the top of main content
    const mainContent = document.getElementById('main-content');
    mainContent.insertAdjacentHTML('afterbegin', alertHTML);
    
    // Auto-remove after 5 seconds
    setTimeout(() => {
        const alert = document.querySelector('.alert');
        if (alert) {
            alert.remove();
        }
    }, 5000);
}

function generateReports() {
    // Age distribution
    const ageDistribution = {};
    students.forEach(student => {
        const ageGroup = Math.floor(student.age / 5) * 5; // Group by 5-year ranges
        const range = `${ageGroup}-${ageGroup + 4}`;
        ageDistribution[range] = (ageDistribution[range] || 0) + 1;
    });
    
    let ageHtml = '<h6>Age Groups:</h6><ul class="list-unstyled">';
    for (const [range, count] of Object.entries(ageDistribution)) {
        ageHtml += `<li><span class="badge bg-primary me-2">${count}</span>${range} years</li>`;
    }
    ageHtml += '</ul>';
    document.getElementById('age-distribution').innerHTML = ageHtml;
    
    // Course distribution
    const courseDistribution = {};
    students.forEach(student => {
        courseDistribution[student.courseName] = (courseDistribution[student.courseName] || 0) + 1;
    });
    
    let courseHtml = '<h6>Students per Course:</h6><ul class="list-unstyled">';
    for (const [course, count] of Object.entries(courseDistribution)) {
        courseHtml += `<li><span class="badge bg-success me-2">${count}</span>${course}</li>`;
    }
    courseHtml += '</ul>';
    document.getElementById('course-distribution').innerHTML = courseHtml;
}