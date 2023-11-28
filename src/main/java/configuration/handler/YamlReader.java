package configuration.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import configuration.model.YamlModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

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

    public void loadData() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("configFile.yaml")).getFile());
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            this.yamlModel = mapper.readValue(file, YamlModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
