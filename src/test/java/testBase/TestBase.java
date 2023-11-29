package testBase;

import configuration.handler.BrowserHandler;
import configuration.handler.EnvironmentHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

@Slf4j
public class TestBase {

    public WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        initializeTestEnvironment();
    }

    private static void initializeTestEnvironment() {
        EnvironmentHandler.setEnvironmentProperties();
        BrowserHandler.setBrowserProperties();
    }

    @BeforeEach
    void setUp() {
        BrowserHandler browserHandler = new BrowserHandler();
        if (this.driver == null)
            driver = browserHandler.initDriver();
        driver.get(System.getProperty("appUrl"));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        log.info("Driver closed properly");
    }
}