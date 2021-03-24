package ua.com.foxminded.sqljdbcschool.view;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class MenuProvider {

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
    private static final String NL = System.lineSeparator();
    private static final String COURSES_HEADER = "id. Course - Description";
    private static final String EMPTY_LINE = "";

    public String getMainMenu() {
        StringJoiner joiner = new StringJoiner(NL);
        joiner.add("a. Find all groups with less or equals student count")
              .add(EMPTY_LINE)
              .add("b. Find all students related to course with given name")
              .add(EMPTY_LINE)
              .add("c. Add new student")
              .add(EMPTY_LINE)
              .add("d. Delete student by STUDENT_ID")
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

    public String getGroupsByStudentCountMenu() {
        final String legend = "To see groups with less or equal amount of students, enter amount of students: ";
        return legend;
    }

    public String getGroupsByStudentCountSubMenu(Map<String, Long> groups, Long count) {
        StringJoiner joiner = new StringJoiner(NL);
        final String legend = "List of groups with desired student count: ";
        final String header = "id. Group name Count";
        final String emptyGroupsLegend = "List of groups with no students: ";
        final String emptyGroupsHeader = "id. Group name";

        if (count > 0) {
            joiner.add(legend).add(EMPTY_LINE).add(header);
            for (Map.Entry<String, Long> entry: groups.entrySet()) {
                joiner.add(entry.getKey() + entry.getValue());
            }
        } else {
            joiner.add(emptyGroupsLegend).add(EMPTY_LINE).add(emptyGroupsHeader);
            for (Map.Entry<String, Long> entry: groups.entrySet()) {
                joiner.add(entry.getKey());
            }
        }
        return joiner.toString();
    }

//    public String getStudentsByCourseNameMenu() {
//
//    }
//
//    public String getAddNewStudentMenu() {
//
//    }
//
//    public String getDeleteStudentByIdMenu() {
//
//    }
}
