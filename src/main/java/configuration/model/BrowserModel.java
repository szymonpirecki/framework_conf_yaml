package configuration.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class BrowserModel {
    @JsonAnyGetter
    @JsonAnySetter
    private HashMap<String, Object> browserSettingsMap = new HashMap<>();

}