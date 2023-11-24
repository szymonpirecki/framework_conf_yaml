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
    private HashMap<String, Object> propertiesMap = new HashMap<>();

    public EnvironmentModel() {
    }

    public EnvironmentModel(HashMap<String, Object> propertiesMap) {
        this.propertiesMap = propertiesMap;
    }

    public String getValueAsString(String key) {
        if (this.propertiesMap.containsKey(key))
            return this.propertiesMap.get(key).toString();
        log.error("Can't find {} in config file", key);
        return null;
    }
}
