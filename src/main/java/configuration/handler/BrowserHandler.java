package configuration.handler;

import configuration.enums.BrowserSetting;
import configuration.model.BrowserModel;
import configuration.model.EnvironmentModel;
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

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BrowserHandler {
    private String browserName = "chrome";
    private boolean browserHeadless = false;
    private int browserImplicitTimeout = 1;
    private boolean maximizeWindow = false;

    private final BrowserModel browserSettings;
    private final EnvironmentModel environmentProperties;


    public BrowserHandler(BrowserModel browserModelObj, EnvironmentModel environmentModelObj) {
        this.browserSettings = browserModelObj;
        this.environmentProperties = environmentModelObj;
    }

    public WebDriver initDriver() {
        adjustBrowserSettings();
        WebDriver driver = null;
        switch (this.browserName.toLowerCase()) {
            case "chrome" -> driver = getChromedriver();
            case "edge" -> driver = getEdgeDriver();
            case "ie" -> driver = getIeDriver();
            case "firefox" -> driver = getFirefoxDriver();
            default -> driver = getChromedriver();
        }
        if (this.maximizeWindow)
            driver.manage().window().maximize();
        log.info("Driver initialized - Chosen browser: {}", this.browserName);
        return driver;
    }

    private void adjustBrowserSettings() {
        HashMap<String, Object> browserSettingsMap = browserSettings.getBrowserSettingsMap();
        HashMap<String, Object> environmentPropertiesMap = environmentProperties.getEnvironmentPropertiesMap();

        browserImplicitTimeout = isSpecified(environmentPropertiesMap, BrowserSetting.BROWSER_IMPLICIT_TIMEOUT.getSettingKey()) ?
                environmentProperties.getValueAsInt(BrowserSetting.BROWSER_IMPLICIT_TIMEOUT.getSettingKey()) : browserImplicitTimeout;

        browserName = isSpecified(browserSettingsMap, BrowserSetting.BROWSER_NAME.getSettingKey()) ?
                browserSettings.getValueAsString(BrowserSetting.BROWSER_NAME.getSettingKey()) : browserName;

        browserHeadless = isSpecified(browserSettingsMap, BrowserSetting.BROWSER_HEADLESS.getSettingKey()) ?
                browserSettings.getValueAsBoolean(BrowserSetting.BROWSER_HEADLESS.getSettingKey()) : browserHeadless;

        maximizeWindow = isSpecified(browserSettingsMap, BrowserSetting.MAXIMIZE_WINDOW.getSettingKey()) ?
                browserSettings.getValueAsBoolean(BrowserSetting.MAXIMIZE_WINDOW.getSettingKey()) : maximizeWindow;
    }

    private boolean isSpecified(HashMap<String, Object> settingsMap, String setting) {
        if (settingsMap.containsKey(setting))
            return settingsMap.get(setting) != null;
        return false;
    }


    private void configureDriverOptions(MutableCapabilities options) {
        if (this.browserHeadless) {
            options.setCapability("headless", true);
        }
    }


    private void setDriversImplicitTimeout(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(this.browserImplicitTimeout, TimeUnit.SECONDS);
    }

    private WebDriver getFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        configureDriverOptions(options);
        FirefoxDriver driver = new FirefoxDriver(options);
        setDriversImplicitTimeout(driver);
        return driver;
    }


    private WebDriver getIeDriver() {
        WebDriverManager.iedriver().setup();
        InternetExplorerOptions options = new InternetExplorerOptions();
        configureDriverOptions(options);
        InternetExplorerDriver driver = new InternetExplorerDriver(options);
        setDriversImplicitTimeout(driver);
        return driver;
    }

    private WebDriver getEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        configureDriverOptions(options);
        EdgeDriver driver = new EdgeDriver(options);
        setDriversImplicitTimeout(driver);
        return driver;
    }

    private WebDriver getChromedriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        configureDriverOptions(options);
        ChromeDriver driver = new ChromeDriver(options);
        setDriversImplicitTimeout(driver);
        return driver;
    }
}