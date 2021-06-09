package ua.com.foxminded.sqljdbcschool.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ua.com.foxminded.sqljdbcschool.config.ConnectionConfig;

public class ConnectionServiceImpl implements ConnectionService {

    private String url;
    private String login;
    private String pass;

    public ConnectionServiceImpl(ConnectionConfig config) {
        this.url = config.getDbUrl();
        this.login = config.getLogin();
        this.pass = config.getPassword();
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, login, pass);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
        return connection;
    }
}
