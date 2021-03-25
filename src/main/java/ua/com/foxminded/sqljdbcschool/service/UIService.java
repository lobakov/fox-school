package ua.com.foxminded.sqljdbcschool.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import ua.com.foxminded.sqljdbcschool.view.MenuProvider;
import ua.com.foxminded.sqljdbcschool.view.UIProvider;

public class UIService {

    //TODO: add user input checks
    private static final String ANY_KEY = "Press any key to continue...";
    private static final String BYE = "Now exiting. Goodbye!";

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
                case 'a': findAllGroupsByStudentCount();
                    break;
                case 'b': findAllStudentsByCourseName();
                    break;
                case 'c': addNewStudent();
                    break;
                case 'd': deleteStudentById();
                    break;
                case 'e': addStudentToCourse();
                    break;
                case 'f': removeStudentFromCourse();
                    break;
                case 'g': UIProvider.display(BYE);
                        return;
                default: continue;
            }
        }
        scanner.close();
    }

    private void addStudentToCourse() {
        String menu = menuProvider.getAddStudentToCourseMenu();
        UIProvider.display(menu);
        Scanner scanner = new Scanner(System.in);
        Long studentId = scanner.nextLong();
        List<String> allCourses = jdbcSchool.toListOfString(jdbcSchool.getAllCourses());
        List<String> studentCourses = jdbcSchool.toListOfString(jdbcSchool.getAllCoursesByStudentId(studentId));
        menu = menuProvider.getAddStudentToCourseSubMenu(allCourses, studentCourses);
        UIProvider.display(menu);
        Long courseId = scanner.nextLong();
        jdbcSchool.addStudentToCourse(studentId, courseId);
        scanner.close();
        displayMainMenu();
    }

    private void findAllGroupsByStudentCount() {
        String menu = menuProvider.getGroupsByStudentCountMenu();
        UIProvider.display(menu);
        Scanner scanner = new Scanner(System.in);
        Long studentCount = scanner.nextLong();
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
        menu = menuProvider.getGroupsByStudentCountSubMenu(groups, studentCount);
        scanner.close();
        displayMainMenu();
    }

    private void findAllStudentsByCourseName() {
        String menu = menuProvider.getStudentsByCourseNameMenu();
        UIProvider.display(menu);
        Scanner scanner = new Scanner(System.in);
        String courseName = scanner.next();
        List<String> students = jdbcSchool.toListOfString(jdbcSchool.findAllStudentsByCourseName(courseName));
        menu = menuProvider.getStudentsByCourseNameSubMenu(students, courseName);
        UIProvider.display(menu);
        UIProvider.display(ANY_KEY);
        scanner.next();
        scanner.close();
        displayMainMenu();
    }

    private void addNewStudent() {
        String menu = menuProvider.getAddNewStudentMenu();
        UIProvider.display(menu);
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        String lastName = scanner.next();
        String student = jdbcSchool.addNewStudent(name, lastName);
        menu = menuProvider.getAddNewStudentSubMenu(student);
        UIProvider.display(menu);
        UIProvider.display(ANY_KEY);
        scanner.next();
        scanner.close();
        displayMainMenu();
    }

    private void deleteStudentById() {
        String menu = menuProvider.getDeleteStudentByIdMenu();
        UIProvider.display(menu);
        Scanner scanner = new Scanner(System.in);
        Long id = scanner.nextLong();
        jdbcSchool.deleteStudentById(id);
        menu = menuProvider.getDeleteStudentByIdSubMenu();
        UIProvider.display(menu);
        scanner.close();
        displayMainMenu();
    }

    private void removeStudentFromCourse() {
        String menu = menuProvider.getRemoveStudentFromCourseMenu();
        UIProvider.display(menu);
        Scanner scanner = new Scanner(System.in);

        Long studentId = scanner.nextLong();
        List<String> courses = jdbcSchool.toListOfString(jdbcSchool.getAllCoursesByStudentId(studentId));

        menu = menuProvider.getRemoveStudentFromCourseSubMenu(courses);
        UIProvider.display(menu);

        String[] input = scanner.nextLine().split(" ");
        List<Long> courseIds = Arrays.asList(input)
                                     .stream()
                                     .map(id -> Long.valueOf(id))
                                     .collect(Collectors.toList());
        jdbcSchool.removeStudentFromCourse(studentId, courseIds);
        courses = jdbcSchool.toListOfString(jdbcSchool.getAllCoursesByStudentId(studentId));
        menu = menuProvider.getRemoveStudentFromCourseSubMenu(courses);
        UIProvider.display(menu);
        UIProvider.display(ANY_KEY);
        scanner.next();
        scanner.close();
        displayMainMenu();
    }
}
