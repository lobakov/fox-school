package ua.com.foxminded.sqljdbcschool.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.config.ConnectionConfig;

public class TablesCreationService {

    private String url;
    private String login;
    private String pass;

    public TablesCreationService(ConnectionConfig config) {
        this.url = config.getDbUrl();
        this.login = config.getLogin();
        this.pass = config.getPassword();
    }

    public void createTablesFromFile(List<String> lines) {
        try (Connection connection = DriverManager.getConnection(url, login, pass);
             Statement statement = connection.createStatement()){
                String sql = "";
                for (String line: lines) {
                    sql += line;
                    if (line.contains(";")) {
                        statement.execute(sql);
                        sql = "";
                    }
                }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
