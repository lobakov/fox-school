package ua.com.foxminded.sqljdbcschool.service;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import ua.com.foxminded.sqljdbcschool.view.MenuProvider;
import ua.com.foxminded.sqljdbcschool.view.UIProvider;

public class UIService {

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
//                case 'a': findAllGroupsByStudentCount();
//                    break;
//                case 'b': findAllStudentsByCourseName();
//                    break;
//                case 'c': addNewStudent();
//                    break;
//                case 'd': deleteStudentById();
//                    break;
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
        List<String> courses = jdbcSchool.toListOfString(jdbcSchool.getAllCourses());
        String menu = menuProvider.getAddStudentToCourseMenu(courses);
        Scanner scanner = new Scanner(System.in);
        UIProvider.display(menu);
        Long studentId = scanner.nextLong();
        Long courseId = scanner.nextLong();
        jdbcSchool.addStudentToCourse(studentId, courseId);
        scanner.close();
    }

//    private void findAllGroupsByStudentCount() {
//
//        Scanner scanner = new Scanner(System.in);
//        UIProvider.display(menuProvider.getGroupsByStudentCountMenu());
//
//        scanner.close();
//    }
//
//    private void findAllStudentsByCourseName() {
//
//        Scanner scanner = new Scanner(System.in);
//        UIProvider.display(menuProvider.getStudentsByCourseNameMenu());
//
//        scanner.close();
//    }
//
//    private void addNewStudent() {
//
//        Scanner scanner = new Scanner(System.in);
//        UIProvider.display(menuProvider.getAddNewStudentMenu());
//
//        scanner.close();
//    }
//
//    private void deleteStudentById() {
//
//        Scanner scanner = new Scanner(System.in);
//        UIProvider.display(menuProvider.getDeleteStudentByIdMenu());
//
//        scanner.close();
//    }

    private void removeStudentFromCourse() {
        String menu = menuProvider.getRemoveStudentFromCourseMenu();
        UIProvider.display(menu);
        Scanner scanner = new Scanner(System.in);

        Long studentId = scanner.nextLong();
        List<String> courses = jdbcSchool.toListOfString(jdbcSchool.getAllCoursesByStudentId(studentId));

        String subMenu = menuProvider.getRemoveStudentFromCourseSubMenu(courses);
        UIProvider.display(subMenu);

        String[] input = scanner.nextLine().split(" ");
        List<Long> courseIds = Arrays.asList(input)
                                     .stream()
                                     .map(id -> Long.valueOf(id))
                                     .collect(Collectors.toList());
        jdbcSchool.removeStudentFromCourse(studentId, courseIds);
        courses = jdbcSchool.toListOfString(jdbcSchool.getAllCoursesByStudentId(studentId));
        subMenu = menuProvider.getRemoveStudentFromCourseSubMenu(courses);
        UIProvider.display(subMenu);
        UIProvider.display(ANY_KEY);
        scanner.next();
        scanner.close();
    }
}
