package ua.com.foxminded.sqljdbcschool.view;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class MenuProvider {

    private static final String NL = System.lineSeparator();
    private static final String COURSES_HEADER = "id. Course - Description";
    private static final String EMPTY_LINE = "";

    public String getMainMenu() {
        StringJoiner joiner = new StringJoiner(NL);
        joiner.add("SqlJdbcShcool Application")
              .add(EMPTY_LINE)
              .add("Please enter corresponding letter for desired action:")
              .add(EMPTY_LINE)
              .add("a. Find all groups with less or equals student count")
              .add(EMPTY_LINE)
              .add("b. Find all students related to course with given name")
              .add(EMPTY_LINE)
              .add("c. Add new student")
              .add(EMPTY_LINE)
              .add("d. Delete student by student id")
              .add(EMPTY_LINE)
              .add("e. Add a student to the course")
              .add(EMPTY_LINE)
              .add("f. Remove the student from one of its courses")
              .add(EMPTY_LINE)
              .add("g. Exit program");
        return joiner.toString();
    }

    public String getAddStudentToCourseMenu() {
        return "Assign course to student. Please enter student id: ";
    }

    public String getAddStudentToCourseSubMenu(List<String> allCourses, List<String> studentCourses) {
        final String legend = "List of available courses: ";
        StringJoiner joiner = new StringJoiner(NL);

        joiner.add(legend).add(EMPTY_LINE).add(COURSES_HEADER);
        for (String course: allCourses) {
            joiner.add(course);
        }
        final String currentCoursesInfo = "List of courses currently assigned to student: ";
        joiner.add(EMPTY_LINE).add(currentCoursesInfo).add(COURSES_HEADER);
        for (String course: studentCourses) {
            joiner.add(course);
        }
        final String info = "Enter course id to assign course to student:";
        joiner.add(EMPTY_LINE).add(info);
        return joiner.toString();
    }

    public String getRemoveStudentFromCourseMenu() {
        return "Remove student from course. Please enter student id for the list of available courses: ";
    }

    public String getRemoveStudentFromCourseSubMenu(List<String> courses) {
        final String legend = "List of chosen student courses: ";
        StringJoiner joiner = new StringJoiner(NL);

        joiner.add(legend).add(EMPTY_LINE).add(COURSES_HEADER);
        for (String course: courses) {
            joiner.add(course);
        }
        final String info = "To remove student from one or more courseses,"
                + "please provide course ids separated by spaces: <id 1> <id 2> ... <id n>: ";
        joiner.add(EMPTY_LINE).add(info);
        return joiner.toString();
    }

    public String getStudentsByCourseNameMenu() {
        final String legend = "Provide course name to see list of assigned students: ";
        return legend;
    }

    public String getStudentsByCourseNameSubMenu(List<String> students, String courseName) {
        final String legend = "List of students assigned to " + courseName + " course";
        final String header = "id. First Name Last Name Group";
        StringJoiner joiner = new StringJoiner(NL);

        joiner.add(legend).add(EMPTY_LINE).add(header);
        for (String student: students) {
            joiner.add(student);
        }
        return joiner.toString();
    }

    public String getAddNewStudentMenu() {
        final String legend = "To add new student, please provide space separated first and last name: ";
        return legend;
    }

    public String getAddNewStudentSubMenu(String student) {
        final String legend = "Student was created: " + NL + student;
        return legend;
    }

    public String getDeleteStudentByIdMenu() {
        return "To delete student, please provide student id: ";
    }

    public String getDeleteStudentByIdSubMenu() {
        return "Student has been deleted";
    }

    public String getGroupsByStudentCountMenu() {
        final String legend = "To see groups with less or equal amount of students, enter amount of students: ";
        return legend;
    }

    public String getGroupsByStudentCountSubMenu(Map<String, Long> groups, Long count) {
        final String legend = "List of groups with desired student count: ";
        final String header = "id. Group name Count";
        final String emptyGroupsLegend = "List of groups with no students: ";
        final String emptyGroupsHeader = "id. Group name";

        StringJoiner joiner = new StringJoiner(NL);
        boolean isEmpty = (count <= 0);
        if (isEmpty) {
            joiner.add(groupsToString(emptyGroupsLegend, emptyGroupsHeader, groups, isEmpty));
        } else {
            joiner.add(groupsToString(legend, header, groups, isEmpty));
        }
        return joiner.toString();
    }

    private String groupsToString(String legend, String header, Map<String, Long> groups, boolean isEmpty) {
        StringJoiner joiner = new StringJoiner(NL);
        joiner.add(legend).add(EMPTY_LINE).add(header);
        for (Map.Entry<String, Long> entry: groups.entrySet()) {
            String group = entry.getKey();
            String value = isEmpty ? "" : (" " + entry.getValue().toString());
            joiner.add(group + value);
        }
        return joiner.toString();
    }
}
