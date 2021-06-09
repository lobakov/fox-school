package ua.com.foxminded.sqljdbcschool.utility;

import java.util.Random;

public class Randomizer {

    private static final Random random = new Random();

    public static int randomize(int min, int max) {
        return random.ints(min, max + 1).findAny().getAsInt();
    }
}
