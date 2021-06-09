package ua.com.foxminded.sqljdbcschool.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public boolean idExists(Long id) {
        boolean exist = false;
        String className = this.getClass().getSimpleName();
        String table = className.replace("Repository", "").toLowerCase() + "s";
        String request = "SELECT * FROM ? WHERE id = ?";
        try (Connection connection = connectionService.getConnection();
                        PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1, table);
            statement.setLong(2, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.isBeforeFirst()){
                    exist = true;
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return exist;
    }

    public abstract T save(T entity);
}
