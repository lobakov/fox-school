package ua.com.foxminded.sqljdbcschool.view;

import java.util.List;
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

    public String getAddStudentToCourseMenu(List<String> courses) {
        final String legend = "List of available courses: ";
        final String header = "id. Course - Description";
        StringJoiner joiner = new StringJoiner(NL);

        joiner.add(legend).add(EMPTY_LINE).add(header);

        for (String course: courses) {
            joiner.add(course);
        }
        final String info = "Add student to course. Input format: <student id> <course id>";
        joiner.add(EMPTY_LINE).add(info);
        return joiner.toString();
    }

    public String getRemoveStudentFromCourseMenu() {
        return "Remove student from course. Please enter student id for the list of available courses: ";
    }

    public String getRemoveStudentFromCourseSubMenu(List<String> courses) {
        final String legend = "List of chosen student courses: ";
        final String header = "id. Course - Description";
        StringJoiner joiner = new StringJoiner(NL);

        joiner.add(legend).add(EMPTY_LINE).add(header);
        for (String course: courses) {
            joiner.add(course);
        }
        final String info = "To remove student from one or more courseses,"
                + "please provide course ids separated by spaces: <id 1> <id 2> ... <id n>: ";
        joiner.add(EMPTY_LINE).add(info);
        return joiner.toString();
    }

//    public String getGroupsByStudentCountMenu() {
//
//    }
//
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
