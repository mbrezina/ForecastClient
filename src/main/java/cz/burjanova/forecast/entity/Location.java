package cz.burjanova.forecast.entity;

import lombok.Data;

import java.util.List;

@Data
public class Location {

    private List<Weather> values;
    private String address;
    private String id;
    private String name;
    private String message;

    public Location(String message) {
        this.message = message;
    }

    public Location() {
    }
}
