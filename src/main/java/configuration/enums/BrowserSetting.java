package configuration.enums;

import lombok.Getter;

@Getter
public enum BrowserSetting {
    BROWSER_NAME("browserName"),
    BROWSER_HEADLESS("browserHeadless"),
    BROWSER_IMPLICIT_TIMEOUT("browserImplicitTimeout"),
    MAXIMIZE_WINDOW("maximizeWindow");

    private final String settingKey;

    BrowserSetting(String settingKey) {
        this.settingKey = settingKey;
    }
}