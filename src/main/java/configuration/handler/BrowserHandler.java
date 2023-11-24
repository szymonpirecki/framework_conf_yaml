package configuration.handler;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

@Slf4j
public class BrowserHandler {

    private String appUrl;

    public BrowserHandler(String appUrl) {
        this.appUrl = appUrl;
    }

    public WebDriver getDriver(Browser browser) {
        WebDriver driver = null;
        switch (browser) {
            case CHROME -> {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            }
            case EDGE -> {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            }
            case IE -> {
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;
            }
        }
        assert driver != null;
        driver.manage().window().maximize();
        log.info("Driver initialized");
        log.debug("Chosen browser: {}", browser);
        driver.get(appUrl);
        log.info("Going to website: {}", appUrl);
        return driver;
    }
}
