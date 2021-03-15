package ua.com.foxminded.sqljdbcschool.utility;

import java.util.Random;

public class Randomizer {

    public static int randomize(int min, int max) {
        return new Random().ints(min, max + 1).findAny().getAsInt();
    }
}
