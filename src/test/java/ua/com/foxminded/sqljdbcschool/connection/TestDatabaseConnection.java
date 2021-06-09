package ua.com.foxminded.sqljdbcschool.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ua.com.foxminded.sqljdbcschool.service.ConnectionService;

public class TestDatabaseConnection implements ConnectionService {

    public TestDatabaseConnection() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("FAILED TO LOAD DATABASE DRIVER");
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:~/test", "sa","");
        } catch (SQLException e) {
            System.out.println("FAILED TO CONNECT TO TEST DB");
            e.printStackTrace();
        }
        return connection;
    }
}
