
package com.example.b00sti.bbeacon.ui_weather.top;

import java.util.HashMap;
import java.util.Map;

public class Sys {

    public double message;
    public String country;
    public int sunrise;
    public int sunset;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
