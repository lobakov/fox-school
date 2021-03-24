package ua.com.foxminded.sqljdbcschool.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
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
     * V e. Add a student to the course (from a list)
     *
     * V f. Remove the student from one of his or her courses
     */
    private CourseRepository courseRepository;
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;

    public SqlJdbcSchoolService(CourseRepository courseRepository,
                                GroupRepository groupRepository,
                                StudentRepository studentRepository) throws InterruptedException {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        init();
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getAllCoursesByStudentId(Long id) {
        return courseRepository.findAllCoursesByStudentId(id);
    }

    public <T> List<String> toListOfString(Collection<T> entities) {
        List<String> result = new ArrayList<>();
        entities.forEach(entity -> {
            result.add(entity.toString());
        });
        return result;
    }

    public Long getStudentCountByGroupId(Long id) {
        return groupRepository.getStudentCountByGroupId(id);
    }

    public void addStudentToCourse(Long studentId, Long courseId) {
        studentRepository.assignCourseToStudent(studentId, courseId);
    }

    public List<Group> findAllGroupsByStudentCount(Long count) {
        return groupRepository.findAllGroupsByStudentCount(count);
    }

    public void findAllStudentsByCourseName(String courseName) {

    }

    public void addNewStudent() {

    }

    public void deleteStudentById(Long id) {

    }

    public void removeStudentFromCourse(Long studentId, List<Long> courseIds) {
        studentRepository.removeStudentFromCourses(studentId, courseIds);
    }

    private void init() throws InterruptedException {
        TestDataGenerationService dataService = new TestDataGenerationService(GroupGenerator.random,
                StudentGenerator.random, new CourseCreator());

        TablesPopulationService tablesPopulationService = new TablesPopulationService(
                dataService.getGroups(), dataService.getStudents(), dataService.getCourses());
        tablesPopulationService.setGroupRepository(groupRepository)
                               .setStudentRepository(studentRepository)
                               .setCourseRepository(courseRepository);
        tablesPopulationService.populateTables();
    }
}
