package ua.com.foxminded.sqljdbcschool.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TablesCreationService {

    private static final String STATEMENT_DELIMITER = ";";

    private ConnectionService connectionService;

    public TablesCreationService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public void createTablesFromFile(List<String> lines) {
        try (Connection connection = connectionService.getConnection();
             Statement statement = connection.createStatement()){
                String sql = "";
                for (String line: lines) {
                    sql += line;
                    if (line.contains(STATEMENT_DELIMITER)) {
                        statement.execute(sql);
                        sql = "";
                    }
                }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
