package configuration.model;

import configuration.handler.Browser;
import lombok.Data;

import java.util.HashMap;

@Data
public class YamlModel {

    private Browser browser;
    private String environment;
    private HashMap<String, EnvironmentModel> environments;

    public HashMap<String, Object> getSpecificTestData(String environmentName) {
        return environments.get(environmentName).getPropertiesMap();
    }
}
