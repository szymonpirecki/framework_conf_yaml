package testBase;

import configuration.handler.Browser;
import configuration.handler.BrowserHandler;
import configuration.handler.YamlReader;
import configuration.model.EnvironmentModel;
import configuration.model.YamlModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

@Slf4j
public class TestBase {

    private static EnvironmentModel testEnvironment;
    private static String loadedEnvironmentName;
    private static Browser loadedBrowser;
    private static YamlModel yamlModel;
    public WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        initializeTestEnvironment();
    }

    private static void initializeTestEnvironment() {
        yamlModel = YamlReader.getInstance().getYamlModel();
        loadedBrowser = yamlModel.getBrowser();
        loadedEnvironmentName = yamlModel.getEnvironment();
        testEnvironment = new EnvironmentModel(yamlModel.getSpecificTestData(loadedEnvironmentName));
    }

    @BeforeEach
    void setUp() {
        BrowserHandler browser = new BrowserHandler(testEnvironment.getValueAsString("appUrl"));
        driver = browser.getDriver(yamlModel.getBrowser());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        log.info("Driver closed properly");
    }

    public String getFromEnv(String key) {
        return testEnvironment.getValueAsString(key);
    }
}
