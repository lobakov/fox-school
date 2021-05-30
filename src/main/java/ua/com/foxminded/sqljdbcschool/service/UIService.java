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
import ua.com.foxminded.sqljdbcschool.view.Text;
import ua.com.foxminded.sqljdbcschool.view.UIProvider;

public class UIService {

    private enum MainMenuItems {

        FIND_GROUPS,
        FIND_STUDENTS,
        ADD_STUDENT,
        DELETE_STUDENT,
        ADD_COURSE,
        REMOVE_COURSE,
        EXIT;
    };

    private Scanner scanner;
    private SqlJdbcSchoolService jdbcSchool;
    private MenuProvider menuProvider;
    private Map<MainMenuItems, Runnable> mainMenuItems;

    public UIService(SqlJdbcSchoolService jdbcSchool, MenuProvider menuProvider) {
        this.jdbcSchool = jdbcSchool;
        this.menuProvider = menuProvider;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        initMainMenu();
        int input = 0;
        while (input != MainMenuItems.EXIT.ordinal()) {
            UIProvider.display(menuProvider.getMainMenu());
            input = scanner.nextInt();
            MainMenuItems item = MainMenuItems.values()[input];
            if ((null != item) && (this.mainMenuItems.containsKey(item))) {
                this.mainMenuItems.get(item).run();
            }
        }
    }

    private void initMainMenu() {
        mainMenuItems = new HashMap<>();
        mainMenuItems.put(MainMenuItems.FIND_GROUPS, this::findAllGroupsByStudentCount);
        mainMenuItems.put(MainMenuItems.FIND_STUDENTS, this::findAllStudentsByCourseName);
        mainMenuItems.put(MainMenuItems.ADD_STUDENT, this::addNewStudent);
        mainMenuItems.put(MainMenuItems.DELETE_STUDENT, this::deleteStudentById);
        mainMenuItems.put(MainMenuItems.ADD_COURSE, this::addStudentToCourse);
        mainMenuItems.put(MainMenuItems.REMOVE_COURSE, this::removeStudentFromCourse);
        mainMenuItems.put(MainMenuItems.EXIT, this::exit);
    }

    private void addStudentToCourse() {
        String menu = menuProvider.getAddStudentToCourseMenu();
        Long studentId = getLongInput(menu);

        List<String> studentCourses = null;
        try {
            studentCourses = jdbcSchool.toListOfString(jdbcSchool.getAllCoursesByStudentId(studentId));
        } catch (RecordNotFoundException ex) {
            UIProvider.display(ex.getMessage());
            return;
        }

        List<String> allCourses = jdbcSchool.toListOfString(jdbcSchool.getAllCourses());
        menu = menuProvider.getAddStudentToCourseSubMenu(allCourses, studentCourses);
        Long courseId = getLongInput(menu);
        try {
            jdbcSchool.addStudentToCourse(studentId, courseId);
        } catch (RecordNotFoundException ex) {
            UIProvider.display(ex.getMessage());
            return;
        }
    }

    private void findAllGroupsByStudentCount() {
        String menu = menuProvider.getGroupsByStudentCountMenu();
        Long studentCount = getLongInput(menu);
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
            UIProvider.display(Text.NO_RECORDS);
            return;
        }
        menu = menuProvider.getGroupsByStudentCountSubMenu(groups, studentCount);
        UIProvider.display(menu);
        UIProvider.display(Text.ANY_KEY);
        scanner.next();
    }

    private void findAllStudentsByCourseName() {
        String menu = menuProvider.getStudentsByCourseNameMenu();
        UIProvider.display(menu);
        String courseName = scanner.next();

        List<String> students = jdbcSchool.toListOfString(jdbcSchool.findAllStudentsByCourseName(courseName));
        if (students == null || students.isEmpty()) {
            UIProvider.display(Text.NO_RECORDS);
            return;
        }
        menu = menuProvider.getStudentsByCourseNameSubMenu(students, courseName);
        UIProvider.display(menu);
        UIProvider.display(Text.ANY_KEY);
        scanner.next();
    }

    private void addNewStudent() {
        String menu = menuProvider.getAddNewStudentMenu();
        String name = "";
        String lastName = "";

        while (true) {
            UIProvider.display(menu);
            name = scanner.next();
            lastName = scanner.next();

            if (!((validateName(name, Text.NAME_PATTERN) && validateName(lastName, Text.NAME_PATTERN)))) {
                UIProvider.display(Text.NAME_INVALID);
                continue;
            }
            break;
        }
        String student = jdbcSchool.addNewStudent(name, lastName);
        menu = menuProvider.getAddNewStudentSubMenu(student);
        UIProvider.display(menu);
        UIProvider.display(Text.ANY_KEY);
        scanner.next();
    }

    private void deleteStudentById() {
        String menu = menuProvider.getDeleteStudentByIdMenu();
        Long id = getLongInput(menu);

        try {
            jdbcSchool.deleteStudentById(id);
        } catch (RecordNotFoundException ex) {
            UIProvider.display(ex.getMessage());
            return;
        }
        menu = menuProvider.getDeleteStudentByIdSubMenu();
        UIProvider.display(menu);
    }

    private void removeStudentFromCourse() {
        String menu = menuProvider.getRemoveStudentFromCourseMenu();
        Long studentId = getLongInput(menu);
        List<String> courses = jdbcSchool.toListOfString(jdbcSchool.getAllCoursesByStudentId(studentId));

        if (courses == null || courses.isEmpty()) {
            UIProvider.display(Text.NO_COURSES);
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
        UIProvider.display(Text.COURSE_DELETED);
        UIProvider.display(Text.ANY_KEY);
        scanner.next();
    }

    private Long getLongInput(String menu) {
        String rawInput = "";
        Long longInput = Long.MIN_VALUE;

        while (true) {
            UIProvider.display(menu);
            rawInput = scanner.next();
            try {
                longInput = Long.valueOf(rawInput);
            } catch (Exception ex) {
                UIProvider.display(Text.INVALID_INPUT + Text.LONG);
                continue;
            }
            break;
        }
        return longInput;
    }

    private void exit() {
        UIProvider.display(Text.BYE);
        scanner.close();
    }

    private boolean validateName(String name, String pattern) {
        Pattern formatPattern = Pattern.compile(pattern);
        return formatPattern.matcher(name).matches();
    }
}
