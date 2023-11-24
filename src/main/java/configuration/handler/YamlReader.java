package configuration.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import configuration.model.YamlModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Getter
@Slf4j
public class YamlReader {

    private static final YamlReader INSTANCE = new YamlReader();

    private YamlModel yamlModel;

    private YamlReader() {
        loadData();
    }

    public static YamlReader getInstance() {
        return INSTANCE;
    }

    private void loadData() {
        String configFileName = "configFile.yaml";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(configFileName);
        if (resource == null) {
            log.error("Configuration file {} not found in classpath", configFileName);
            return;
        }
        File file = new File(resource.getFile());
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        try {
            this.yamlModel = om.readValue(file, YamlModel.class);
            log.info("Configuration successfully loaded from {}", file.getPath());
        } catch (IOException e) {
            log.error("Error during loading data from {}", file.getPath(), e);
        }
    }
}
