package ua.com.foxminded.sqljdbcschool.view;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class MenuProvider {

    public String getMainMenu() {
        StringJoiner joiner = new StringJoiner(Text.NL.repeat(2));
        joiner.add(Text.APP_NAME)
              .add(Text.MAIN_MENU_INFO)
              .add(Text.MAIN_MENU_FIND_GROUPS)
              .add(Text.MAIN_MENU_FIND_STUDENTS)
              .add(Text.MAIN_MENU_ADD_STUDENT)
              .add(Text.MAIN_MENU_DELETE_STUDENT)
              .add(Text.MAIN_MENU_ADD_COURSE)
              .add(Text.MAIN_MENU_REMOVE_COURSE)
              .add(Text.MAIN_MENU_EXIT);
        return joiner.toString();
    }

    public String getAddStudentToCourseMenu() {
        return Text.ADD_STUDENT_TO_COURSE_INFO;
    }

    public String getAddStudentToCourseSubMenu(List<String> allCourses, List<String> studentCourses) {
        StringJoiner joiner = new StringJoiner(Text.NL);
        joiner.add(Text.AVAILABLE_COURSES).add(Text.NL).add(Text.COURSES_HEADER);
        for (String course: allCourses) {
            joiner.add(course);
        }
        joiner.add(Text.NL).add(Text.CURRENT_COURSES).add(Text.COURSES_HEADER);
        for (String course: studentCourses) {
            joiner.add(course);
        }
        joiner.add(Text.NL).add(Text.ADD_STUDENT_TO_COURSE_SUB_INFO);
        return joiner.toString();
    }

    public String getRemoveStudentFromCourseMenu() {
        return Text.REMOVE_STUDENT_FROM_COURSE_INFO;
    }

    public String getRemoveStudentFromCourseSubMenu(List<String> courses) {
        StringJoiner joiner = new StringJoiner(Text.NL);
        joiner.add(Text.REMOVE_STUDENT_FROM_COURSE_LEGEND).add(Text.NL).add(Text.COURSES_HEADER);
        for (String course: courses) {
            joiner.add(course);
        }
        joiner.add(Text.NL).add(Text.REMOVE_STUDENT_FROM_COURSE_SUB_INFO);
        return joiner.toString();
    }

    public String getStudentsByCourseNameMenu() {
        return Text.STUDENTS_BY_COURSE_NAME_INFO;
    }

    public String getStudentsByCourseNameSubMenu(List<String> students, String courseName) {
        StringJoiner joiner = new StringJoiner(Text.NL);
        joiner.add(Text.STUDENTS_BY_COURSE_NAME_LEGEND)
              .add(Text.NL)
              .add(Text.STUDENTS_BY_COURSE_NAME_HEADER);
        for (String student: students) {
            joiner.add(student);
        }
        return joiner.toString();
    }

    public String getAddNewStudentMenu() {
        return Text.ADD_NEW_STUDENT_INFO;
    }

    public String getAddNewStudentSubMenu(String student) {
        return Text.STUDENT_CREATED;
    }

    public String getDeleteStudentByIdMenu() {
        return Text.STUDENT_DELETE_INFO;
    }

    public String getDeleteStudentByIdSubMenu() {
        return Text.STUDENT_DELETE_SUCCESS;
    }

    public String getGroupsByStudentCountMenu() {
        return Text.GROUPS_BY_STUDENT_COUNT_INFO;
    }

    public String getGroupsByStudentCountSubMenu(Map<String, Long> groups, Long count) {
        StringJoiner joiner = new StringJoiner(Text.NL);
        boolean isEmpty = (count <= 0);
        if (isEmpty) {
            joiner.add(groupsToString(Text.GROUPS_BY_STUDENT_COUNT_LEGEND_EMPTY,
                    Text.GROUPS_BY_STUDENT_COUNT_HEADER_EMPTY, groups, isEmpty));
        } else {
            joiner.add(groupsToString(Text.GROUPS_BY_STUDENT_COUNT_LEGEND,
                    Text.GROUPS_BY_STUDENT_COUNT_HEADER, groups, isEmpty));
        }
        return joiner.toString();
    }

    private String groupsToString(String legend, String header, Map<String, Long> groups, boolean isEmpty) {
        StringJoiner joiner = new StringJoiner(Text.NL);
        joiner.add(legend).add(Text.NL).add(header);
        for (Map.Entry<String, Long> entry: groups.entrySet()) {
            String group = entry.getKey();
            String value = isEmpty ? "" : (" " + entry.getValue().toString());
            joiner.add(group + value);
        }
        return joiner.toString();
    }
}
