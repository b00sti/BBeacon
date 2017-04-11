
package com.example.b00sti.bbeacon.ui_weather.top.model;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public double temp;
    public double pressure;
    public int humidity;
    public double tempMin;
    public double tempMax;
    public double seaLevel;
    public double grndLevel;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}