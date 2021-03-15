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
    private CourseGenerator courseGenerator;

    public TestDataGenerationService(Supplier<Group> groupSupplier,
                                     Supplier<Student> studentSupplier,
                                     CourseGenerator courseGenerator) {
        this.groupSupplier = groupSupplier;
        this.studentSupplier = studentSupplier;
        this.courseGenerator = courseGenerator;
    }

    public void generateTestData() {
        Set<Group> groups = generate(groupSupplier, GROUPS_AMOUNT);
        Set<Student> students = generate(studentSupplier, STUDENTS_AMOUNT);
        assignStudentsToGroups(students, groups);
        assignCoursesToStudents(students);

        groups.forEach(group -> {
            System.out.println("Group: " + group.getName());
            System.out.println("Number of students: " + group.getStudents().size());
            System.out.println("Students: " + group.getStudents());
        });

        students.forEach(student -> {
            System.out.println("Student: " + student);
            System.out.println("Group: " + student.getGroup().getName());
            System.out.println("Courses: " + student.getCourses());
        });

        List<Course> courses = courseGenerator.getCourses();
        courses.forEach(course -> {
            System.out.println("Course: " + course);
            System.out.println("Course students: " + course.getStudents());
        });
    }

    private <T> Set<T> generate(Supplier<T> supplier,  int amount) {
        return EntityGenerator.generate(supplier, amount);
    }

    private void assignStudentsToGroups(Set<Student> students, Set<Group> groups) {
        Stack<Student> studentStack = setToShuffledStack(students);
        for (Group group: groups) {
            int amount = Randomizer.randomize(0, MAX_STUDENTS - 1);
            if (amount < MIN_STUDENTS) {
                continue;
            }
            while (group.getStudents().size() < amount) {
                Student student = studentStack.pop();
                group.addStudent(student);
                student.setGroup(group);
            }
        }
    }

    private void assignCoursesToStudents(Set<Student> students) {
        for (Student student: students) {
            int numberOfCourses = Randomizer.randomize(MIN_COURSES, MAX_COURSES);
            Set<Course> studentsCourses = student.getCourses();
            while (studentsCourses.size() < numberOfCourses) {
                Course course = courseGenerator.random.get();
                if (!studentsCourses.contains(course)) {
                    studentsCourses.add(course);
                    course.addStudent(student);
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
