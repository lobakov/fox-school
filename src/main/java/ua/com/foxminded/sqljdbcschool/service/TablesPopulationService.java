package ua.com.foxminded.sqljdbcschool.service;

public class TablesPopulationService {

    private TestDataGenerationService generationService;

    public TablesPopulationService(TestDataGenerationService service) {
        this.generationService = service;
    }

    public void populateTables();
}
