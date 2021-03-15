package ua.com.foxminded.sqljdbcschool.service;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public interface EntityGenerator {

    static <T> Set<T> generate(Supplier<T> create, int amount) {
        Set<T> entities = new HashSet<>();
        while (entities.size() < amount) {
            entities.add(create.get());
        }
        return entities;
    }
}
