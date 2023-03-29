package com.dds.weatherapp.network.weatherapi;


import java.util.Date;

public class Weather {
    public Date getLocalTime() {
        return localTime;
    }

    public void setLocalTime(Date localTime) {
        this.localTime = localTime;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    private String locationName;
    private String region;
    private String country;
    private double latitude;
    private double longitude;

    private Date localTime;
    private Date updatedAt;

    private double temperatureInC;
    private double temperatureInF;
    private double humidity;

    private boolean isDay;

    private String weatherCondition;

    private String weatherIconUrl;

    private int weatherCode;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getTemperatureInC() {
        return temperatureInC;
    }

    public void setTemperatureInC(double temperatureInC) {
        this.temperatureInC = temperatureInC;
    }

    public double getTemperatureInF() {
        return temperatureInF;
    }

    public void setTemperatureInF(double temperatureInF) {
        this.temperatureInF = temperatureInF;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public boolean isDay() {
        return isDay;
    }

    public void setDay(boolean day) {
        isDay = day;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(String weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

}
