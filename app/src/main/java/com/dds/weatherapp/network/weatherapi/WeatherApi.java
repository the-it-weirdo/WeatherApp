package com.dds.weatherapp.network.weatherapi;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class WeatherApi {

    private final static String BASE_URL = "https://api.weatherapi.com/v1/";


    private String isForecast(boolean isForecast) {
        String url = isForecast ? BASE_URL + "forecast.json?&days=7" : BASE_URL + "current.json?";
        return url + "&key=5b3986175da64d94ba9151516232803";
    }

    public String buildURLFromLatLong(Double latitude, Double longitude, boolean isForecast, boolean aqi, boolean alerts) {
        String aqiS = aqi ? "yes" : "no";
        String alertStr = alerts ? "yes" : "no";
        return this.isForecast(isForecast) + "&q=" + latitude.toString() + "," +
                longitude.toString() + "&aqi=" + aqiS + "&alerts=" + alertStr;
    }

    public Weather parseCurrentWeatherFromJsonObject(JSONObject jsonObject) {
        Weather weather = new Weather();
        try {

            JSONObject location = jsonObject.getJSONObject("location");
            weather.setLocationName(location.getString("name"));
            weather.setRegion(location.getString("region"));
            weather.setCountry(location.getString("country"));
            weather.setLatitude(location.getDouble("lat"));
            weather.setLongitude(location.getDouble("lon"));

            String localDateTime = location.getString("localtime");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
            weather.setLocalTime(format.parse(localDateTime));

            JSONObject current = jsonObject.getJSONObject("current");
            String lastUpdated = current.getString("last_updated");
            weather.setUpdatedAt(format.parse(lastUpdated));
            weather.setTemperatureInC(current.getDouble("temp_c"));
            weather.setTemperatureInF(current.getDouble("temp_f"));
            weather.setDay(current.getInt("is_day") == 1);
            JSONObject condition = current.getJSONObject("condition");
            weather.setWeatherCondition(condition.getString("text"));
            weather.setWeatherIconUrl("https://" + condition.getString("icon"));
            weather.setWeatherCode(condition.getInt("code"));

            weather.setHumidity(jsonObject.getDouble("humidity"));


        } catch (JSONException e) {
            Log.e("WeatherApi-parseCurrentWeatherFromJsonObject", "JSON parse exception: " + e);
        } catch (ParseException e) {
            Log.e("WeatherApi-parseCurrentWeatherFromJsonObject", "Date parse exception" + e);
        }

        return weather;
    }

    public ArrayList<ForecastWeather> parseWeatherForecast(JSONObject response) {
        ArrayList<ForecastWeather> forecasts = new ArrayList<>();

        try {
            JSONObject forecast = response.getJSONObject("forecast");
            JSONArray forecastsArr = forecast.getJSONArray("forecastday");

            for (int i = 0; i < forecastsArr.length(); i++) {
                JSONObject obj = forecastsArr.getJSONObject(i);
                ForecastWeather forecastWeather = new ForecastWeather();

                String localDateTime = obj.getString("date");
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                forecastWeather.setForecastDate(format.parse(localDateTime));

                JSONObject day = obj.getJSONObject("day");

                forecastWeather.setMaxTempInC(day.getDouble("maxtemp_c"));
                forecastWeather.setMaxTempInF(day.getDouble("maxtemp_f"));
                forecastWeather.setMinTempInC(day.getDouble("mintemp_c"));
                forecastWeather.setMinTempInF(day.getDouble("mintemp_f"));
                forecastWeather.setAvgTempInC(day.getDouble("avgtemp_c"));
                forecastWeather.setAvgTempInF(day.getDouble("avgtemp_f"));

                JSONObject condition = day.getJSONObject("condition");

                forecastWeather.setWeatherCondition(condition.getString("text"));
                forecastWeather.setWeatherConditionUrl("https://" + condition.getString("icon"));
                forecastWeather.setWeatherConditionCode(condition.getInt("code"));

                forecasts.add(forecastWeather);
            }

        } catch (JSONException e) {
            Log.e("WeatherApi-parseWeatherForecast", "JSON parse exception: " + e);
        } catch (ParseException e) {
            Log.e("WeatherApi-parseWeatherForecast", "Date parse exception" + e);
        }
        return forecasts;
    }

}
