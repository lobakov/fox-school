package ua.com.foxminded.sqljdbcschool.service;

import java.util.StringJoiner;
import java.util.function.Supplier;

import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.utility.Randomizer;

public class GroupGenerator {

    private static final int MIN = 0;
    private static final int MAX = 9;
    private static final int MIN_CHAR = 65;
    private static final int MAX_CHAR = 90;
    private static final String HYPHEN = "-";

    public static Supplier<Group> random = () -> {
        StringJoiner joiner = new StringJoiner("");
        joiner.add(getRandomChar())
              .add(getRandomChar())
              .add(HYPHEN)
              .add(getRandomNumber())
              .add(getRandomNumber());
        return new Group(joiner.toString());
    };

    private static String getRandomChar() {
        return String.valueOf((char) Randomizer.randomize(MIN_CHAR, MAX_CHAR));
    }

    private static String getRandomNumber() {
        return String.valueOf(Randomizer.randomize(MIN, MAX));
    }
}
