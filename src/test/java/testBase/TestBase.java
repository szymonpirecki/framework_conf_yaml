package testBase;

import configuration.handler.BrowserHandler;
import configuration.handler.YamlReader;
import configuration.model.BrowserModel;
import configuration.model.EnvironmentModel;
import configuration.model.YamlModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

@Slf4j
public class TestBase {

    private static EnvironmentModel environmentProperties;
    private static BrowserModel browserSettings;
    public WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        initializeTestEnvironment();
    }

    private static void initializeTestEnvironment() {
        YamlModel yamlModel = YamlReader.getInstance().getYamlModel();
        String loadedEnvironmentName = yamlModel.getEnvironment();
        environmentProperties = new EnvironmentModel(yamlModel.getSpecificTestData(loadedEnvironmentName));
        browserSettings = new BrowserModel(yamlModel.getBrowserSettings());
    }

    @BeforeEach
    void setUp() {
        BrowserHandler browserHandler = new BrowserHandler(browserSettings, environmentProperties);
        if (this.driver == null)
            driver = browserHandler.initDriver();
        driver.get(environmentProperties.getValueAsString("appUrl"));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        log.info("Driver closed properly");
    }

    public Object getFromEnv(String key) {
        return environmentProperties.getValue(key);
    }
}