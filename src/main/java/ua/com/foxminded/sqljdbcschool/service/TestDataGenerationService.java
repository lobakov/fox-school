package ua.com.foxminded.sqljdbcschool.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Supplier;

import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.utility.Randomizer;

public class TestDataGenerationService {

    private static final int GROUPS_AMOUNT = 10;
    private static final int STUDENTS_AMOUNT = 200;
    private static final int MIN_COURSES = 1;
    private static final int MAX_COURSES = 3;
    private static final int MIN_STUDENTS = 10;
    private static final int MAX_STUDENTS = 30;

    private Supplier<Group> groupSupplier;
    private Supplier<Student> studentSupplier;
    private CourseCreator courseCreator;
    private Set<Group> groups;
    private Set<Student> students;
    private List<Course> courses;

    public TestDataGenerationService(Supplier<Group> groupSupplier,
                                     Supplier<Student> studentSupplier,
                                     CourseCreator courseCreator) {
        this.groupSupplier = groupSupplier;
        this.studentSupplier = studentSupplier;
        this.courseCreator = courseCreator;
        generateTestData();
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void refreshTestData() {
        generateTestData();
    }

    private void generateTestData() {
        this.groups = generate(groupSupplier, GROUPS_AMOUNT);
        this.students = generate(studentSupplier, STUDENTS_AMOUNT);
        this.courses = courseCreator.getCourses();
        assignStudentsToGroups(students, groups);
        assignCoursesToStudents(students);
    }

    private <T> Set<T> generate(Supplier<T> supplier,  int amount) {
        return EntityGenerator.generate(supplier, amount);
    }

    private void assignStudentsToGroups(Set<Student> students, Set<Group> groups) {
        Stack<Student> studentStack = setToShuffledStack(students);
        for (Group group: groups) {
            int targetAmount = Randomizer.randomize(0, MAX_STUDENTS - 1);
            if (targetAmount < MIN_STUDENTS) {
                continue;
            }
            int numberOfStudents = 0;
            while (numberOfStudents < targetAmount) {
                if (studentStack.isEmpty()) {
                    break;
                }
                studentStack.pop().setGroup(group);
                ++numberOfStudents;
            }
        }
    }

    private void assignCoursesToStudents(Set<Student> students) {
        for (Student student: students) {
            int numberOfCourses = Randomizer.randomize(MIN_COURSES, MAX_COURSES);
            Set<Course> studentCourses = student.getCourses();
            while (studentCourses.size() < numberOfCourses) {
                Course course = courseCreator.random.get();
                if (!studentCourses.contains(course)) {
                    studentCourses.add(course);
                }
            }
        }
    };

    private <T> Stack<T> setToShuffledStack(Set<T> set) {
        List<T> list = new ArrayList<>(set);
        Collections.shuffle(list);
        Stack<T> stack = new Stack<>();
        stack.addAll(list);
        return stack;
    }
}
