package configuration.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Getter
@Slf4j
public class BrowserModel {
    @JsonAnyGetter
    @JsonAnySetter
    private HashMap<String, Object> browserSettingsMap = new HashMap<>();

    public BrowserModel(HashMap<String, Object> browserSettingsMap) {
        this.browserSettingsMap = browserSettingsMap;
    }

    public String getValueAsString(String key) {
        if (!this.browserSettingsMap.containsKey(key)){
            log.error("Can't find {} in config file", key);
            return null;
        }
        return this.browserSettingsMap.get(key).toString();
    }
    public Boolean getValueAsBoolean(String key) {
        if (!this.browserSettingsMap.containsKey(key)){
            log.error("Can't find {} in config file", key);
            return null;
        }
        return (Boolean) this.browserSettingsMap.get(key);
    }
    public Integer getValueAsInt(String key) {
        if (!this.browserSettingsMap.containsKey(key)){
            log.error("Can't find {} in config file", key);
            return null;
        }
        return Integer.parseInt(String.valueOf(this.browserSettingsMap.get(key)));
    }
}