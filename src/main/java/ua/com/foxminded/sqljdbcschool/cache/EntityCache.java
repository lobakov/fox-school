package ua.com.foxminded.sqljdbcschool.cache;

import java.util.HashMap;
import java.util.Map;

public class EntityCache<E> {

    private Map<Long, E> map = new HashMap<>();

    public void put(Long id, E entity) {
        map.put(id, entity);
    }

    public E get(Long id) {
        return map.get(id);
    }

    public void remove(Long id) {
        map.remove(id);
    }
}
