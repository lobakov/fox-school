package ua.com.foxminded.sqljdbcschool.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ua.com.foxminded.sqljdbcschool.config.ConnectionConfig;

public class ConnectionService {

    private String url;
    private String login;
    private String pass;

    public ConnectionService(ConnectionConfig config) {
        this.url = config.getDbUrl();
        this.login = config.getLogin();
        this.pass = config.getPassword();
    }

    public Connection getConnection() throws SQLException {
            return DriverManager.getConnection(url, login, pass);
    }
}
