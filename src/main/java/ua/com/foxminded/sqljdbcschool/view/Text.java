package ua.com.foxminded.sqljdbcschool.view;

public final class Text {

    public static final String ANY_KEY = "Input any string and press enter to continue...";
    public static final String BYE = "Now exiting. Goodbye!";
    public static final String COURSE_DELETED = "The course has been deleted";
    public static final String INVALID_INPUT = "Invalid input, please try again!";
    public static final String LONG = "Expected long value.";
    public static final String MAIN_MENU_FIND_GROUPS = "Find all groups with less or equals student count";
    public static final String MAIN_MENU_FIND_STUDENTS = "Find all students related to course with given name";
    public static final String MAIN_MENU_ADD_STUDENT = "Add new student";
    public static final String MAIN_MENU_DELETE_STUDENT = "Delete student by student id";
    public static final String MAIN_MENU_ADD_COURSE = "Add a student to the course";
    public static final String MAIN_MENU_REMOVE_COURSE = "Remove the student from one of its courses";
    public static final String MAIN_MENU_EXIT = "Exit program";
    public static final String NAME_PATTERN = "^[a-zA-Z ]*$";
    public static final String NAME_INVALID = "Invalid name/last name format."
            + " Please ensure alphabetic characters only.";
    public static final String NL = System.lineSeparator();
    public static final String NO_COURSES = "No available courses found.";
    public static final String NO_RECORDS = "No records matching your criteria found";


    static final String ADD_NEW_STUDENT_INFO = "To add new student, please provide space "
            + "separated first and last name: ";
    static final String ADD_STUDENT_TO_COURSE_INFO = "Assign course to student. Please enter student id: ";
    static final String ADD_STUDENT_TO_COURSE_SUB_INFO = "Enter course id to assign course to student:";
    static final String APP_NAME = "SqlJdbcShcool Application";
    static final String AVAILABLE_COURSES = "List of available courses: ";
    static final String COURSES_HEADER = "id. Course - Description";
    static final String CURRENT_COURSES = "List of courses currently assigned to student: ";
    static final String GROUPS_BY_STUDENT_COUNT_HEADER = "id. Group name Count";
    static final String GROUPS_BY_STUDENT_COUNT_HEADER_EMPTY = "id. Group name";
    static final String GROUPS_BY_STUDENT_COUNT_INFO = "To see groups with less or equal amount of students, "
            + "enter amount of students: ";
    static final String GROUPS_BY_STUDENT_COUNT_LEGEND = "List of groups with desired student count: ";
    static final String GROUPS_BY_STUDENT_COUNT_LEGEND_EMPTY = "List of groups with no students: ";
    static final String MAIN_MENU_INFO = "Please enter corresponding number for desired action:";
    static final String REMOVE_STUDENT_FROM_COURSE_INFO = "Remove student from course. Please enter student id "
            + "for the list of available courses: ";
    static final String REMOVE_STUDENT_FROM_COURSE_LEGEND = "List of chosen student courses: ";
    static final String REMOVE_STUDENT_FROM_COURSE_SUB_INFO = "To remove student from one or more courseses, "
            + "please provide course ids separated by spaces: <id 1> <id 2> ... <id n>: ";
    static final String STRING = "Expected string value.";
    static final String STUDENTS_BY_COURSE_NAME_HEADER = "id. First Name Last Name Group";
    static final String STUDENTS_BY_COURSE_NAME_INFO = "Provide course name to see list of assigned students: ";
    static final String STUDENTS_BY_COURSE_NAME_LEGEND = "List of students assigned to course";
    static final String STUDENT_CREATED = "Student was created.";
    static final String STUDENT_DELETE_INFO = "To delete student, please provide student id: ";
    static final String STUDENT_DELETE_SUCCESS = "Student has been deleted";
}
