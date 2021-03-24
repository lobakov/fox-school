package ua.com.foxminded.sqljdbcschool;

import ua.com.foxminded.sqljdbcschool.repository.CourseRepository;
import ua.com.foxminded.sqljdbcschool.repository.GroupRepository;
import ua.com.foxminded.sqljdbcschool.repository.StudentRepository;
import ua.com.foxminded.sqljdbcschool.service.ConfigurationService;
import ua.com.foxminded.sqljdbcschool.service.ConnectionService;
import ua.com.foxminded.sqljdbcschool.service.SqlJdbcSchoolService;
import ua.com.foxminded.sqljdbcschool.service.UIService;
import ua.com.foxminded.sqljdbcschool.utility.FileReader;
import ua.com.foxminded.sqljdbcschool.view.MenuProvider;

public class SqlJdbcSchoolApplication {
    //TODO: 1. Change project name 2. Fat Jar

    public static void main(String[] args) throws InterruptedException {
        ConfigurationService configurationService = new ConfigurationService(new FileReader());

        ConnectionService connectionService = configurationService.getConnectionService();

        SqlJdbcSchoolService schoolService = new SqlJdbcSchoolService(new CourseRepository(connectionService),
                                new GroupRepository(connectionService), new StudentRepository(connectionService));

        UIService uiService = new UIService(schoolService, new MenuProvider());
        uiService.run();
    }
}
