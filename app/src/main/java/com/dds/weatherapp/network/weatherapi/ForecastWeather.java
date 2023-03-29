package com.dds.weatherapp.network.weatherapi;

import java.util.Date;

public class ForecastWeather {

    private Date forecastDate;

    private double maxTempInC;
    private double maxTempInF;
    private double minTempInC;
    private double minTempInF;
    private double avgTempInC;
    private double avgTempInF;


    private String weatherCondition;
    private String weatherConditionUrl;
    private int weatherConditionCode;

    public double getMaxTempInC() {
        return maxTempInC;
    }

    public void setMaxTempInC(double maxTempInC) {
        this.maxTempInC = maxTempInC;
    }

    public double getMaxTempInF() {
        return maxTempInF;
    }

    public void setMaxTempInF(double maxTempInF) {
        this.maxTempInF = maxTempInF;
    }

    public double getMinTempInC() {
        return minTempInC;
    }

    public void setMinTempInC(double minTempInC) {
        this.minTempInC = minTempInC;
    }

    public double getMinTempInF() {
        return minTempInF;
    }

    public void setMinTempInF(double minTempInF) {
        this.minTempInF = minTempInF;
    }

    public double getAvgTempInC() {
        return avgTempInC;
    }

    public void setAvgTempInC(double avgTempInC) {
        this.avgTempInC = avgTempInC;
    }

    public double getAvgTempInF() {
        return avgTempInF;
    }

    public void setAvgTempInF(double avgTempInF) {
        this.avgTempInF = avgTempInF;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getWeatherConditionUrl() {
        return weatherConditionUrl;
    }

    public void setWeatherConditionUrl(String weatherConditionUrl) {
        this.weatherConditionUrl = weatherConditionUrl;
    }

    public int getWeatherConditionCode() {
        return weatherConditionCode;
    }

    public void setWeatherConditionCode(int weatherConditionCode) {
        this.weatherConditionCode = weatherConditionCode;
    }

    public Date getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(Date forecastDate) {
        this.forecastDate = forecastDate;
    }
}
