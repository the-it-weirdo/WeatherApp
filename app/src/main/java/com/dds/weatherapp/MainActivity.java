package com.dds.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dds.weatherapp.adapter.ForecastAdapter;
import com.dds.weatherapp.network.weatherapi.ForecastWeather;
import com.dds.weatherapp.network.NetworkHelper;
import com.dds.weatherapp.network.weatherapi.Weather;
import com.dds.weatherapp.network.weatherapi.WeatherApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    NetworkHelper networkHelper;
    WeatherApi apiHelper;

    View currentWeatherCard;
    TextView textTemperature;
    TextView textWeatherCondition;
    TextView textHumidity;
    TextView textLocation;
    TextView lastUpdated;
    ImageView weatherIcon;
    ImageView loadingCurrentIcon;
    ImageView loadingForecastIcon;
    Button refreshButton;
    Button refreshForecastButton;
    RecyclerView forecastRecycler;

    RequestQueue requestQueue;

    RecyclerView.LayoutManager layoutManager;

    RadioGroup tempUnitRadioGroup;

    Weather weather;
    ArrayList<ForecastWeather> forecastWeathers;
    ForecastAdapter adapter;

    boolean temperatureUnitInC = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkHelper = new NetworkHelper(this);

        if (!networkHelper.isNetworkConnected()) {
            setContentView(R.layout.no_internet);

        } else {
            setContentView(R.layout.activity_main);
            apiHelper = new WeatherApi();
            requestQueue = Volley.newRequestQueue(this);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (networkHelper.isNetworkConnected()) {
            setupUI();
        } else {
            refreshButton = findViewById(R.id.button_retry);

            refreshButton.setOnClickListener(v -> {
                if (networkHelper.isNetworkConnected()) {
                    apiHelper = new WeatherApi();
                    requestQueue = Volley.newRequestQueue(this);
                    setContentView(R.layout.activity_main);
                    setupUI();
                }
            });
        }
    }

    private void setupUI() {
        currentWeatherCard = findViewById(R.id.current_card);
        textTemperature = findViewById(R.id.text_temperature);
        textWeatherCondition = findViewById(R.id.text_weather_condition);
        textHumidity = findViewById(R.id.text_humidity);
        textLocation = findViewById(R.id.text_location);
        lastUpdated = findViewById(R.id.text_last_updated);
        weatherIcon = findViewById(R.id.image_weather_icon);
        tempUnitRadioGroup = findViewById(R.id.radio_group_unit);
        forecastRecycler = findViewById(R.id.recycler_forecast);
        refreshButton = findViewById(R.id.button_refresh);
        refreshForecastButton = findViewById(R.id.button_refresh_forecast);
        loadingCurrentIcon = findViewById(R.id.image_loading);
        loadingForecastIcon = findViewById(R.id.image_loading_forecast);

        forecastRecycler.setVisibility(View.INVISIBLE);
        currentWeatherCard.setVisibility(View.INVISIBLE);
        Glide.with(this)
                .load("https://upload.wikimedia.org/wikipedia/commons/b/b9/Youtube_loading_symbol_1_(wobbly).gif")
                .into(loadingCurrentIcon);
        Glide.with(this)
                .load("https://upload.wikimedia.org/wikipedia/commons/b/b9/Youtube_loading_symbol_1_(wobbly).gif")
                .into(loadingForecastIcon);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        tempUnitRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            temperatureUnitInC = checkedId == R.id.radio_celsius;
            if (weather == null) {
                getCurrentWeather();
            } else updateCurrentWeatherUI(weather);

            if (adapter != null) {
                adapter.setTempInC(temperatureUnitInC);
            } else getForecastWeather();
        });

        refreshButton.setOnClickListener(v -> {
            currentWeatherCard.setVisibility(View.INVISIBLE);
            loadingCurrentIcon.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load("https://upload.wikimedia.org/wikipedia/commons/b/b9/Youtube_loading_symbol_1_(wobbly).gif")
                    .into(loadingCurrentIcon);
            getCurrentWeather();
        });
        refreshForecastButton.setOnClickListener(v -> {
            getForecastWeather();
            loadingForecastIcon.setVisibility(View.VISIBLE);
            forecastRecycler.setVisibility(View.INVISIBLE);
        });

        getCurrentWeather();
        getForecastWeather();
    }


    @SuppressLint("SetTextI18n")
    void updateCurrentWeatherUI(Weather weather) {
        loadingCurrentIcon.setVisibility(View.INVISIBLE);
        currentWeatherCard.setVisibility(View.VISIBLE);

        String temperature = String.valueOf(temperatureUnitInC ? weather.getTemperatureInC() : weather.getTemperatureInF());
        textTemperature.setText(temperature + "\u00B0");
        textWeatherCondition.setText(weather.getWeatherCondition());
        textLocation.setText(weather.getLocationName() + ", " + weather.getRegion());
        DateFormat format = new SimpleDateFormat("hh:mm a MMM-dd-yyyy", Locale.ENGLISH);
        lastUpdated.setText(format.format(weather.getUpdatedAt()));
        Glide.with(this).load(weather.getWeatherIconUrl()).into(weatherIcon);
    }

    Pair<Double, Double> getLatLong() {
        return new Pair<>(37.208958, -93.292297);
    }

    void getCurrentWeather() {

        Log.d("MainActivity", "callApi");
        Pair<Double, Double> latLong = getLatLong();

        JsonObjectRequest currentWeatherRequest = networkHelper.makeGetRequest(apiHelper.buildURLFromLatLong(latLong.first, latLong.second, false, false, false), response -> {

            Log.d("MainActivity", "response");
            weather = apiHelper.parseCurrentWeatherFromJsonObject(response);
            updateCurrentWeatherUI(weather);
        }, Throwable::printStackTrace);

        requestQueue.add(currentWeatherRequest);
    }

    void getForecastWeather() {
        Log.d("MainActivity", "getForecastWeather");
        Pair<Double, Double> latLong = getLatLong();

        JsonObjectRequest forecastWeatherRequest = networkHelper.makeGetRequest(apiHelper.buildURLFromLatLong(latLong.first, latLong.second, true, false, false), response -> {

            Log.d("MainActivity", "response forecast");
            forecastWeathers = apiHelper.parseWeatherForecast(response);

            loadingForecastIcon.setVisibility(View.INVISIBLE);
            forecastRecycler.setVisibility(View.VISIBLE);

            forecastRecycler.setLayoutManager(layoutManager);
            adapter = new ForecastAdapter(forecastWeathers, temperatureUnitInC);
            forecastRecycler.setAdapter(adapter);

        }, Throwable::printStackTrace);

        requestQueue.add(forecastWeatherRequest);
    }
}