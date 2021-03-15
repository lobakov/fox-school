package ua.com.foxminded.sqljdbcschool.service;

import java.util.function.Supplier;

import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.utility.Randomizer;

public class StudentGenerator {

    private static final int MIN_NAME_INDEX = 0;
    private static final int MAX_NAME_INDEX = 19;

    private static final String[] FIRST_NAMES = new String[] {
            "Alex", "Bob", "Charles", "David", "Ethan",
            "Frank", "George", "Harry", "Jack", "Luke",
            "Ann", "Betty", "Christine", "Emma", "Isabella",
            "Jenny", "Kate", "Mia", "Olivia", "Penny"
    };
    private static final String[] LAST_NAMES = new String[] {
            "Smith", "Jones", "Taylor", "Williams", "Brown",
            "White", "Harris", "Martin", "Davies", "Wilson",
            "Cooper", "Evans", "King", "Thomas", "Baker",
            "Green", "Wright", "Johnson", "Edwards", "Clark"
    };

    public static Supplier<Student> random = () -> {
        int firstNameIndex = Randomizer.randomize(MIN_NAME_INDEX, MAX_NAME_INDEX);
        int lastNameIndex = Randomizer.randomize(MIN_NAME_INDEX, MAX_NAME_INDEX);
        return new Student(FIRST_NAMES[firstNameIndex], LAST_NAMES[lastNameIndex]);
    };
}
