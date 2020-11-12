package cz.burjanova.forecast.entity;

import lombok.Data;

@Data
public class GraphTemperature {
    
    private Float temperature;
    private String day;

    public GraphTemperature(Float temperature, String day) {
        this.temperature = temperature;
        this.day = day;
    }
}
