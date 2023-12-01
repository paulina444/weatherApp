package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class City {
    @JsonProperty("name")
    private String name;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    public String getName() {
        return name;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }

}
