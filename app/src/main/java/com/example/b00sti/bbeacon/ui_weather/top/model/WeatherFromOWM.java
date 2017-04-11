
package com.example.b00sti.bbeacon.ui_weather.top.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherFromOWM {

    public Coord coord;
    public List<Weather> weather = null;
    public String base;
    public Main main;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int id;
    public String name;
    public int cod;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
