package ua.com.foxminded.sqljdbcschool.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.StringJoiner;

import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;

public class TestDataGenerationService {

    private static final int GROUPS_AMOUNT = 10;
    private static final int STUDENTS_AMOUNT = 200;
    private static final int MIN = 0;
    private static final int MAX = 9;
    private static final int MIN_CHAR = 65;
    private static final int MAX_CHAR = 90;
    private static final int MIN_COURSES = 1;
    private static final int MAX_COURSES = 3;
    private static final int MIN_NAME_INDEX = 0;
    private static final int MAX_NAME_INDEX = 19;
    private static final int MIN_STUDENTS = 10;
    private static final int MAX_STUDENTS = 30;
    private static final int NUMBER_OF_COURSES = 10;
    private static final int RADIX = 10;
    private static final int ZERO = 0;
    private static final String HYPHEN = "-";
    private static final String[] COURSES = new String[] {
            "Algebra", "Biology", "Geography", "Physics", "Economics",
            "History", "English", "Geometry", "Chemistry", "Astronomy"
    };
    private static final String[] FIRST_NAMES = new String[] {
            "Alex", "Bob", "Charles", "David", "Ethan",
            "Frank", "George", "Harry", "Jack", "Luke",
            "Ann", "Betty", "Christine", "Emma", "Isabella",
            "Jenny", "Kate", "Mia", "Olivia", "Penny"
    };
    private static final String[] LAST_NAMES = new String[] {
            "Smith", "Jones", "Taylor", "Williams", "Brown",
            "White", "Harris", "Martin", "Davies", "Wilson",
            "Cooper", "Evans", "King", "Thomas", "Baker",
            "Green", "Wright", "Johnson", "Edwards", "Clark"
    };

    /*
     * * 10 groups with randomly generated names. The name should contain 2
     * characters, hyphen, 2 numbers
     *
     * Create 10 courses (math, biology, etc)
     *
     * 200 students. Take 20 first names and 20 last names and randomly combine them
     * to generate students.
     *
     * Randomly assign students to groups. Each group could contain from 10 to 30
     * students. It is possible that some groups will be without students or
     * students without groups
     *
     * Create relation MANY-TO-MANY between tables STUDENTS and COURSES. Randomly
     * assign from 1 to 3 courses for each student
     */

    public String[] getCoursesNames() {
        return COURSES;
    }

    public Set<Group> generateGroups() {
        Set<Group> groups = new HashSet<>();
        while (groups.size() < GROUPS_AMOUNT) {
            groups.add(new Group(getRandomGroupName()));
        }
        return groups;
    }

    public Set<Student> generateStudents() {
        Set<Student> students = new HashSet<>();
        while (students.size() < STUDENTS_AMOUNT) {
            students.add(getRandomStudent());
        }
        return students;
    }


    public void assignStudentsToGroups(Set<Group> groups, Set<Student> students) {
        int amount = 0;
        List<Student> studentsList = new ArrayList<>();
        studentsList.addAll(students);
        Collections.shuffle(studentsList);
        Stack<Student> stack = new Stack<>();
        stack.addAll(studentsList);
        for (Group group: groups) {
            amount = randomize(ZERO, MAX_STUDENTS - 1);
            if (amount < MIN_STUDENTS) {
                continue;
            }
            while (group.getStudents().size() < amount) {
                Student student = stack.pop();
                group.addStudent(student);
                student.setGroup(group);
            }
        }
    }

    public List<Course> assignCoursesToStudents(Set<Student> students) {
        List<Course> courses = createCourses();
        Collections.shuffle(courses);
        for (Student student: students) {
            int numberOfCourses = randomize(MIN_COURSES, MAX_COURSES);
            while (student.getCourses().size() < numberOfCourses) {
                int courseNum = randomize(ZERO, NUMBER_OF_COURSES - 1);
                Course course = courses.get(courseNum);
                student.getCourses().add(course);
                course.addStudent(student);
            }
        }
        return courses;
    };

    private List<Course> createCourses() {
        List<Course> courses = new ArrayList<>();
        for (String course: COURSES) {
            courses.add(new Course(course));
        }
        return courses;
    }

    private String getRandomGroupName() {
        StringJoiner joiner = new StringJoiner("");
        joiner.add(String.valueOf((char) randomize(MIN_CHAR, MAX_CHAR)))
              .add(String.valueOf((char) randomize(MIN_CHAR, MAX_CHAR)))
              .add(HYPHEN)
              .add(String.valueOf(randomize(MIN, MAX)))
              .add(String.valueOf(randomize(MIN, MAX)));
        return joiner.toString();
    }

    private Student getRandomStudent() {
        int firstNameIndex = randomize(MIN_NAME_INDEX, MAX_NAME_INDEX);
        String firstName = FIRST_NAMES[firstNameIndex];
        int lastNameIndex = randomize(MIN_NAME_INDEX, MAX_NAME_INDEX);
        String lastName = LAST_NAMES[lastNameIndex];
        return new Student(firstName, lastName);
    }

    private int randomize(int min, int max) {
        return new Random().ints(min, max + 1).findAny().getAsInt();
    }
}
