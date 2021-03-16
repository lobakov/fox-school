package ua.com.foxminded.sqljdbcschool.service;

import java.util.Scanner;
import java.util.StringJoiner;

public class UIService {

    private static final String NL = System.lineSeparator();

    public String getMenu() {
        StringJoiner joiner = new StringJoiner(NL);
        joiner.add("a. Find all groups with less or equals student count")
              .add("")
              .add("b. Find all students related to course with given name")
              .add("")
              .add("c. Add new student")
              .add("")
              .add("d. Delete student by STUDENT_ID")
              .add("")
              .add("e. Add a student to the course")
              .add("")
              .add("f. Remove the student from one of its courses")
              .add("")
              .add("g. Exit program");
        return joiner.toString();
    }

    public void displayMainMenu() {
        Scanner scanner = new Scanner(System.in);
        char key = ' ';
        while (!(key == 'g')) {
            System.out.println(getMenu());
            key = scanner.next().charAt(0);
            switch (key) {
                case 'a':
                    break;
                case 'b':
                    break;
                case 'c':
                    break;
                case 'd':
                    break;
                case 'e':
                    break;
                case 'f':
                    break;
                case 'g': return;
                default: continue;
            }
        }
    }
}
