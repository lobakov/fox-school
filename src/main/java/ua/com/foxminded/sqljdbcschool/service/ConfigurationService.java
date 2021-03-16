package ua.com.foxminded.sqljdbcschool.service;

import java.io.IOException;

import ua.com.foxminded.sqljdbcschool.config.ConnectionConfig;
import ua.com.foxminded.sqljdbcschool.utility.FileReader;

public class ConfigurationService {

    private static final String DB_FILE = "db/migration/v1_init.sql";

    private FileReader reader;
    private ConnectionConfig config;
    private ConnectionService connectionService;

    public ConfigurationService(FileReader reader) {
        this.reader = reader;
        this.config = new ConnectionConfig(reader);
        this.connectionService = new ConnectionService(config);
    }

    public ConnectionService getConnectionService() {
        return this.connectionService;
    }

    public void initDb() {
        TablesCreationService tablesCreationService = new TablesCreationService(connectionService);

        try {
            Class.forName(config.getDbDriver());
            tablesCreationService.createTablesFromFile(reader.read(DB_FILE));
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR: Could not find driver " + config.getDbDriver());
            ex.printStackTrace();
        } catch (IOException e) {
            System.out.println("ERROR: Could not read migration script " + DB_FILE);
            e.printStackTrace();
        }
    }
}
