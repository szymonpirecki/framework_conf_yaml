package configuration.handler;

import configuration.model.YamlModel;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BrowserHandler {
    private String browserName = "chrome";
    private boolean browserHeadless = false;
    private int browserImplicitTimeout = 1;
    private boolean maximizeWindow = false;


    public static void setBrowserProperties() {
        log.info("Setting browser properties...");
        YamlModel yamlModel = YamlReader.getInstance().getYamlModel();
        Map<String, Object> browserSettingsMap = yamlModel.getBrowserSettings();
        for (Map.Entry<String, Object> entry : browserSettingsMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                System.setProperty(key, (String) value);
            } else if (value instanceof Boolean) {
                System.setProperty(key, Boolean.toString((Boolean) value));
            } else if (value instanceof Number) {
                System.setProperty(key, value.toString());
            } else if (value != null) {
                System.setProperty(key, value.toString());
            }
            log.info("{}:{} set", key,value);
        }
        log.info("Browser properties set.");
    }

    public WebDriver initDriver() {
        adjustBrowserSettings();
        WebDriver driver = null;
        log.info("Initializing driver with browserName: {}", this.browserName);
        switch (this.browserName.toLowerCase()) {
            case "edge" -> driver = getEdgeDriver();
            case "ie" -> driver = getIeDriver();
            case "firefox" -> driver = getFirefoxDriver();
            default -> driver = getChromedriver();
        }
        if (this.maximizeWindow)
            driver.manage().window().maximize();
        setDriversImplicitTimeout(driver);
        log.info("Driver initialized - Chosen browser: {}", this.browserName);
        return driver;
    }


    private void adjustBrowserSettings() {
        log.debug("Adjusting browser settings...");
        browserImplicitTimeout = isSpecified("browserImplicitTimeout") ?
                Integer.parseInt(System.getProperty("browserImplicitTimeout")) : this.browserImplicitTimeout;

        browserName = isSpecified("browserName") ?
                System.getProperty("browserName") : this.browserName;

        browserHeadless = isSpecified("browserHeadless") ?
                Boolean.parseBoolean(System.getProperty("browserHeadless")) : this.browserHeadless;

        maximizeWindow = isSpecified("maximizeWindow") ?
                Boolean.parseBoolean(System.getProperty("maximizeWindow")) : this.maximizeWindow;
        log.debug("Browser settings adjusted.");
    }

    private boolean isSpecified(String setting) {
        return (System.getProperty(setting) != null);
    }

    private void setHeadlessOption(MutableCapabilities options) {
        log.debug("Setting headless option to {}", this.browserHeadless);
        if (this.browserHeadless) options.setCapability("headless", true);
    }

    private void setDriversImplicitTimeout(WebDriver driver) {
        log.debug("Setting driver's implicit timeout to {} seconds", this.browserImplicitTimeout);
        driver.manage().timeouts().implicitlyWait(this.browserImplicitTimeout, TimeUnit.SECONDS);
    }

    private WebDriver getFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        setHeadlessOption(options);
        return new FirefoxDriver(options);
    }


    private WebDriver getIeDriver() {
        WebDriverManager.iedriver().setup();
        InternetExplorerOptions options = new InternetExplorerOptions();
        setHeadlessOption(options);
        return new InternetExplorerDriver(options);
    }

    private WebDriver getEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        setHeadlessOption(options);
        return new EdgeDriver(options);
    }

    private WebDriver getChromedriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        setHeadlessOption(options);
        return new ChromeDriver(options);
    }
}