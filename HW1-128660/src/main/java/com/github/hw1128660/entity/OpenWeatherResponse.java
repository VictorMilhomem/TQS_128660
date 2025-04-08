package com.github.hw1128660.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.tools.javac.Main;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherResponse {

    @JsonProperty("list")
    private List<ForecastEntry> list;

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ForecastEntry {
        @JsonProperty("dt_txt")
        private String dateTime;

        @JsonProperty("main")
        private Main main;

        @JsonProperty("weather")
        private List<Weather> weather;
    }

    @Getter @Setter
    public static class Main {
        private double temp;
    }

    @Getter @Setter
    public static class Weather {
        private String description;
    }

    public List<ForecastEntry> getList() {
        return list;
    }

    public void setList(List<ForecastEntry> list) {
        this.list = list;
    }
}
