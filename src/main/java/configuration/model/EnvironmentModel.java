package configuration.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Getter
@Slf4j
public class EnvironmentModel {

    @JsonAnyGetter
    @JsonAnySetter
    private HashMap<String, Object> environmentPropertiesMap = new HashMap<>();

    public EnvironmentModel() {
    }

    public EnvironmentModel(HashMap<String, Object> propertiesMap) {
        this.environmentPropertiesMap = propertiesMap;
    }

    public String getValueAsString(String key) {
        if (!this.environmentPropertiesMap.containsKey(key)){
            log.error("Can't find {} in config file", key);
            return null;
        }
        return this.environmentPropertiesMap.get(key).toString();
    }
    public Integer getValueAsInt(String key) {
        if (!this.environmentPropertiesMap.containsKey(key)){
            log.error("Can't find {} in config file", key);
            return null;
        }
        return (Integer) this.environmentPropertiesMap.get(key);
    }
    public Object getValue(String key) {
        if (!this.environmentPropertiesMap.containsKey(key)){
            log.error("Can't find {} in config file", key);
            return null;
        }
        return this.environmentPropertiesMap.get(key);
    }
}
