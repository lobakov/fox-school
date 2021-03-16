package ua.com.foxminded.sqljdbcschool;

import ua.com.foxminded.sqljdbcschool.service.ConfigurationService;
import ua.com.foxminded.sqljdbcschool.service.SqlJdbcSchoolService;
import ua.com.foxminded.sqljdbcschool.utility.FileReader;

public class SqlJdbcSchoolApplication {
    //TODO: 1. Change project name 2. Fat Jar

    public static void main(String[] args) {
        ConfigurationService configurationService = new ConfigurationService(new FileReader());
        configurationService.initDb();

        SqlJdbcSchoolService schoolService = new SqlJdbcSchoolService(configurationService.getConnectionService());
        schoolService.init();
        schoolService.run();
    }
}
