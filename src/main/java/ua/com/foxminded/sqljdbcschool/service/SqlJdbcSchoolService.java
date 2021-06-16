package ua.com.foxminded.sqljdbcschool.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.cache.EntityCache;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.exception.RecordNotFoundException;
import ua.com.foxminded.sqljdbcschool.repository.CourseRepository;
import ua.com.foxminded.sqljdbcschool.repository.GroupRepository;
import ua.com.foxminded.sqljdbcschool.repository.JdbcSchoolRepository;
import ua.com.foxminded.sqljdbcschool.repository.StudentRepository;

public class SqlJdbcSchoolService {

    private CourseRepository courseRepository;
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;

    private EntityCache<Course> courseCache = new EntityCache<>();
    private EntityCache<Student> studentCache = new EntityCache<>();
    private EntityCache<Group> groupCache = new EntityCache<>();

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
        checkIfExist(studentRepository, id);
        return courseRepository.findAllCoursesByStudentId(id);
    }

    public Long getStudentCountByGroupId(Long id) {
        checkIfExist(groupRepository, id);
        return groupRepository.getStudentCountByGroupId(id);
    }

    public void addStudentToCourse(Long studentId, Long courseId) {
        checkIfExist(studentRepository, studentId);
        checkIfExist(courseRepository, courseId);
        studentRepository.assignCourseToStudent(studentId, courseId);
    }

    public List<Group> findAllGroupsByStudentCount(Long count) {
        return groupRepository.findAllGroupsByStudentCount(count);
    }

    public List<Student> findAllStudentsByCourseName(String courseName) {
        return studentRepository.findAllStudentsByCourseName(courseName, groupRepository);
    }

    public String addNewStudent(String firstName, String lastName) {
        Student student = new Student(firstName, lastName);
        studentRepository.save(student);
        studentCache.put(student.getId(), student);
        return student.toString();
    }

    public void deleteStudentById(Long id) {
        checkIfExist(studentRepository, id);
        studentRepository.deleteStudentById(id);
        studentCache.remove(id);
    }

    public void removeStudentFromCourse(Long studentId, List<Long> courseIds) {
        checkIfExist(studentRepository, studentId);
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

    public <T> List<String> toListOfString(Collection<T> entities) {
        List<String> result = new ArrayList<>();
        entities.forEach(entity -> {
            result.add(entity.toString());
        });
        return result;
    }

    @SuppressWarnings("rawtypes")
    private void checkIfExist(JdbcSchoolRepository repository, Long id) {
        if (!repository.idExists(id)) {
            throw new RecordNotFoundException("Course id " + id + "not found!");
        }
    }
}
