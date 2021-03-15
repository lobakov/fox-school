package ua.com.foxminded.sqljdbcschool.config;

import java.io.IOException;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.utility.FileReader;

public class ConnectionConfig {

    private static final String CRED_FILE = "credentials";
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String PREFIX = "jdbc";
    private static final String DBMS = "postgresql";
    private static final String URL = "//localhost:5432/jdbc_school_db";
    private static final String DB_URL = PREFIX + ":" + DBMS + ":" + URL;
    private static final int LOGIN_INDEX = 0;
    private static final int PASS_INDEX = 1;

    private String login;
    private String password;

    public ConnectionConfig(FileReader reader) {
        try {
            List<String> credentials = reader.read(CRED_FILE);
            this.login = credentials.get(LOGIN_INDEX);
            this.password = credentials.get(PASS_INDEX);
        } catch (IOException ioex) {
            System.out.println("ERROR: Could not read file " + CRED_FILE);
            ioex.printStackTrace();
        }
    }

    public String getDbDriver() {
        return DB_DRIVER;
    }

    public String getDbUrl() {
        return DB_URL;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }
}
