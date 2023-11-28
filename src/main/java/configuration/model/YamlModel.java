package configuration.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class YamlModel {
    private String environment;
    private HashMap<String, Object> browserSettings;
    private HashMap<String, EnvironmentModel> environments;

    public HashMap<String, Object> getSpecificTestData(String environmentName) {
        return environments.get(environmentName).getEnvironmentPropertiesMap();
    }
}
