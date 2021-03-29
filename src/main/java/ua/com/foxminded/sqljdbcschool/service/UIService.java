package ua.com.foxminded.sqljdbcschool.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ua.com.foxminded.sqljdbcschool.exception.RecordNotFoundException;
import ua.com.foxminded.sqljdbcschool.view.MenuProvider;
import ua.com.foxminded.sqljdbcschool.view.UIProvider;

public class UIService {

    private static final String ANY_KEY = "Input any string and press enter to continue...";
    private static final String BYE = "Now exiting. Goodbye!";
    private static final String INVALID_INPUT = "Invalid input, please try again!";
    private static final String LONG = "Expected long value.";
    private static final String NO_RECORDS = "No records matching your criteria found";
    private static final String STRING = "Expected string value.";
    private static final String NAME_PATTERN = "^[a-zA-Z ]*$";
    private static final String NAME_INVALID = "Invalid name/last name format."
            + " Please ensure alphabetic characters only.";

    private SqlJdbcSchoolService jdbcSchool;
    private MenuProvider menuProvider;

    public UIService(SqlJdbcSchoolService jdbcSchool, MenuProvider menuProvider) {
        this.jdbcSchool = jdbcSchool;
        this.menuProvider = menuProvider;
    }

    public void run() {
        displayMainMenu();
    }

    private void displayMainMenu() {
        Scanner scanner = new Scanner(System.in);
        char key = ' ';
        while (!(key == 'g')) {
            UIProvider.display(menuProvider.getMainMenu());
            key = scanner.next().charAt(0);
            switch (key) {
                case 'a': findAllGroupsByStudentCount(scanner);
                    break;
                case 'b': findAllStudentsByCourseName(scanner);
                    break;
                case 'c': addNewStudent(scanner);
                    break;
                case 'd': deleteStudentById(scanner);
                    break;
                case 'e': addStudentToCourse(scanner);
                    break;
                case 'f': removeStudentFromCourse(scanner);
                    break;
                case 'g': UIProvider.display(BYE);
                    return;
                default: continue;
            }
        }
        scanner.close();
    }

    private void addStudentToCourse(Scanner scanner) {
        String menu = menuProvider.getAddStudentToCourseMenu();
        Long studentId = getLongInput(scanner, menu);

        List<String> studentCourses = null;
        try {
            studentCourses = jdbcSchool.toListOfString(jdbcSchool.getAllCoursesByStudentId(studentId));
        } catch (RecordNotFoundException ex) {
            UIProvider.display(ex.getMessage());
            return;
        }

        List<String> allCourses = jdbcSchool.toListOfString(jdbcSchool.getAllCourses());
        menu = menuProvider.getAddStudentToCourseSubMenu(allCourses, studentCourses);
        Long courseId = getLongInput(scanner, menu);
        try {
            jdbcSchool.addStudentToCourse(studentId, courseId);
        } catch (RecordNotFoundException ex) {
            UIProvider.display(ex.getMessage());
            return;
        }
    }

    private void findAllGroupsByStudentCount(Scanner scanner) {
        String menu = menuProvider.getGroupsByStudentCountMenu();
        Long studentCount = getLongInput(scanner, menu);
        Map<String, Long> groups = new HashMap<>();

        if (studentCount > 0) {
            jdbcSchool.findAllGroupsByStudentCount(studentCount).stream()
                .forEach(group ->
                    groups.put(group.toString(), jdbcSchool.getStudentCountByGroupId(group.getId()))
                );
        } else {
            jdbcSchool.findAllGroupsByStudentCount(studentCount).stream()
                .forEach(group ->
                    groups.put(group.toString(), 0l));
        }
        if (groups == null || groups.isEmpty()) {
            UIProvider.display(NO_RECORDS);
            return;
        }
        menu = menuProvider.getGroupsByStudentCountSubMenu(groups, studentCount);
        UIProvider.display(menu);
        UIProvider.display(ANY_KEY);
        scanner.next();
    }

    private void findAllStudentsByCourseName(Scanner scanner) {
        String menu = menuProvider.getStudentsByCourseNameMenu();
        UIProvider.display(menu);
        String courseName = scanner.next();

        List<String> students = jdbcSchool.toListOfString(jdbcSchool.findAllStudentsByCourseName(courseName));
        if (students == null || students.isEmpty()) {
            UIProvider.display(NO_RECORDS);
            return;
        }
        menu = menuProvider.getStudentsByCourseNameSubMenu(students, courseName);
        UIProvider.display(menu);
        UIProvider.display(ANY_KEY);
        scanner.next();
    }

    private void addNewStudent(Scanner scanner) {
        String menu = menuProvider.getAddNewStudentMenu();
        String name = "";
        String lastName = "";

        while (true) {
            UIProvider.display(menu);
            name = scanner.next();
            lastName = scanner.next();

            if (!((validateName(name, NAME_PATTERN) && validateName(lastName, NAME_PATTERN)))) {
                UIProvider.display(NAME_INVALID);
                continue;
            }
            break;
        }
        String student = jdbcSchool.addNewStudent(name, lastName);
        menu = menuProvider.getAddNewStudentSubMenu(student);
        UIProvider.display(menu);
        UIProvider.display(ANY_KEY);
        scanner.next();
    }

    private void deleteStudentById(Scanner scanner) {
        String menu = menuProvider.getDeleteStudentByIdMenu();
        Long id = getLongInput(scanner, menu);

        try {
            jdbcSchool.deleteStudentById(id);
        } catch (RecordNotFoundException ex) {
            UIProvider.display(ex.getMessage());
            return;
        }
        menu = menuProvider.getDeleteStudentByIdSubMenu();
        UIProvider.display(menu);
    }

    private void removeStudentFromCourse(Scanner scanner) {
        String menu = menuProvider.getRemoveStudentFromCourseMenu();
        Long studentId = getLongInput(scanner, menu);
        List<String> courses = jdbcSchool.toListOfString(jdbcSchool.getAllCoursesByStudentId(studentId));

        if (courses == null || courses.isEmpty()) {
            UIProvider.display("No available courses found.");
            return;
        }
        menu = menuProvider.getRemoveStudentFromCourseSubMenu(courses);
        UIProvider.display(menu);

        scanner.nextLine();
        String[] input = scanner.nextLine().split(" ");
        List<Long> courseIds = Arrays.asList(input)
                                     .stream()
                                     .map(id -> Long.valueOf(id))
                                     .collect(Collectors.toList());
        try {
            jdbcSchool.removeStudentFromCourse(studentId, courseIds);
        } catch (RecordNotFoundException ex) {
            UIProvider.display(ex.getMessage());
            return;
        }
        UIProvider.display("The course has been deleted");
        UIProvider.display(ANY_KEY);
        scanner.next();
    }

    private Long getLongInput(Scanner scanner, String menu) {
        String rawInput = "";
        Long longInput = Long.MIN_VALUE;

        while (true) {
            UIProvider.display(menu);
            rawInput = scanner.next();
            try {
                longInput = Long.valueOf(rawInput);
            } catch (Exception ex) {
                UIProvider.display(INVALID_INPUT + LONG);
                continue;
            }
            break;
        }
        return longInput;
    }

    private boolean validateName(String name, String pattern) {
        Pattern formatPattern = Pattern.compile(pattern);
        return formatPattern.matcher(name).matches();
    }
}
