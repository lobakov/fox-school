package ua.com.foxminded.sqljdbcschool.service;

import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.repository.CourseRepository;
import ua.com.foxminded.sqljdbcschool.repository.GroupRepository;
import ua.com.foxminded.sqljdbcschool.repository.StudentRepository;

public class SqlJdbcSchoolService {

    /*
     * a. Find all groups with less or equals student count
     *
     * b. Find all students related to course with given name
     *
     * c. Add new student
     *
     * d. Delete student by STUDENT_ID
     *
     * e. Add a student to the course (from a list)
     *
     * f. Remove the student from one of his or her courses
     */
    ConnectionService connectionService;

    public SqlJdbcSchoolService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public void init() {
        TestDataGenerationService dataService = new TestDataGenerationService(GroupGenerator.random,
                StudentGenerator.random, new CourseCreator());

        GroupRepository groupRepository = new GroupRepository(connectionService);
        StudentRepository studentRepository = new StudentRepository(connectionService);
        CourseRepository courseRepository = new CourseRepository(connectionService);

        TablesPopulationService tablesPopulationService = new TablesPopulationService(
                dataService.getGroups(), dataService.getStudents(), dataService.getCourses());
        tablesPopulationService.setGroupRepository(groupRepository)
                               .setStudentRepository(studentRepository)
                               .setCourseRepository(courseRepository);
        tablesPopulationService.populateTables();
    }

    public void run() {

    }

    private void addStudentToCourse(Student student, Course course) {

    }

    private void addStudentToCourse(Long studentId, Long courseId) {

    }

    private void findAllGroupsByStudentCount(Long count) {

    }

    private void findAllStudentsByCourseName(String courseName) {

    }

    private void addNewStudent() {

    }

    private void deleteStudentById(Long id) {

    }

    private void removeStudentFromACourse() {

    }
}
