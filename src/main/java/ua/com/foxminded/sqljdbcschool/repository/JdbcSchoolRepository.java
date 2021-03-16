package ua.com.foxminded.sqljdbcschool.repository;

import java.util.function.Consumer;

import ua.com.foxminded.sqljdbcschool.service.ConnectionService;

public abstract class JdbcSchoolRepository<T> {

    protected ConnectionService connectionService;

    protected JdbcSchoolRepository(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public Consumer<T> saveEntity = entity -> {
        save(entity);
    };

    public abstract void save(T entity);
}
