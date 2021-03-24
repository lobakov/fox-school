package ua.com.foxminded.sqljdbcschool.service;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.repository.CourseRepository;
import ua.com.foxminded.sqljdbcschool.repository.GroupRepository;
import ua.com.foxminded.sqljdbcschool.repository.StudentRepository;

public class TablesPopulationService {

    private Set<Group> groups;
    private Set<Student> students;
    private List<Course> courses;
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public TablesPopulationService setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
        return this;
    }

    public TablesPopulationService setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        return this;
    }

    public TablesPopulationService setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        return this;
    }

    public TablesPopulationService(Set<Group> groups, Set<Student> students, List<Course> courses) {
        this.groups = groups;
        this.students = students;
        this.courses = courses;
    }

    public void populateTables() throws InterruptedException {
        populate(groups, groupRepository.saveEntity);
        populate(courses.stream().collect(Collectors.toSet()), courseRepository.saveEntity);
        populate(students, studentRepository.saveEntity);
        assignStudentsToGroups();
        assignCoursesToStudents();
    }

    private void assignStudentsToGroups() throws InterruptedException {
        for (Student student: students) {
            Long groupId = student.getGroup().getId();
            student.setGroupId(groupId);
            studentRepository.assignStudentToGroup(student.getId(), groupId);
        }
    }

    private void assignCoursesToStudents() {
        for (Student student: students) {
            Set<Course> studentCourses = student.getCourses();
            Long id = student.getId();
            studentCourses.forEach(course -> {
                studentRepository.assignCourseToStudent(id, course.getId());
            });
        }
    }

    private <T> void populate(Set<T> entities, Consumer<T> consumer) {
        for (T entity: entities) {
            consumer.accept(entity);
        }
    }
}
