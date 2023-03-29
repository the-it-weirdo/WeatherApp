package com.dds.weatherapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dds.weatherapp.R;
import com.dds.weatherapp.network.weatherapi.ForecastWeather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private final ArrayList<ForecastWeather> forecastWeathers;

    private boolean isTempInC;

    public ForecastAdapter(ArrayList<ForecastWeather> data, boolean tempInC) {
        forecastWeathers = data;
        isTempInC = tempInC;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTempInC(boolean tempInC) {
        isTempInC = tempInC;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_forecast_card, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ForecastWeather weather = forecastWeathers.get(position);

        DateFormat dayOfWeekFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
        holder.getTextDayOfWeek().setText(dayOfWeekFormat.format(weather.getForecastDate()));
        DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        holder.getTextForecastDate().setText(format.format(weather.getForecastDate()));
        holder.getTextWeatherCondition().setText(weather.getWeatherCondition());
        if (isTempInC) {
            holder.getTextMaxTemp().setText("Max: " + weather.getMaxTempInC() + "\u00B0");
            holder.getTextAvgTemp().setText("Avg: " + weather.getAvgTempInC() + "\u00B0");
            holder.getTextMinTemp().setText("Min: " + weather.getMinTempInC() + "\u00B0");
        } else {
            holder.getTextMaxTemp().setText("Max: " + weather.getMaxTempInF() + "\u00B0");
            holder.getTextAvgTemp().setText("Avg: " + weather.getAvgTempInF() + "\u00B0");
            holder.getTextMinTemp().setText("Min: " + weather.getMinTempInF() + "\u00B0");
        }
        Glide.with(holder.getParent()).load(weather.getWeatherConditionUrl()).into(holder.getImageWeatherIcon());
    }

    @Override
    public int getItemCount() {
        return forecastWeathers.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textDayOfWeek;
        private final TextView textForecastDate;
        private final TextView textWeatherCondition;
        private final TextView textMaxTemp;
        private final TextView textAvgTemp;
        private final TextView textMinTemp;
        private final ImageView imageWeatherIcon;
        private final View parent;

        public ViewHolder(View view) {
            super(view);
            parent = view;
            textDayOfWeek = view.findViewById(R.id.text_day_of_week);
            textForecastDate = view.findViewById(R.id.text_date);
            textWeatherCondition = view.findViewById(R.id.text_weather_condition_forecast);
            textMaxTemp = view.findViewById(R.id.text_max_temp);
            textAvgTemp = view.findViewById(R.id.text_avg_temp);
            textMinTemp = view.findViewById(R.id.text_min_temp);
            imageWeatherIcon = view.findViewById(R.id.image_weather_icon_forecast);
        }

        public TextView getTextDayOfWeek() {
            return textDayOfWeek;
        }

        public TextView getTextForecastDate() {
            return textForecastDate;
        }

        public TextView getTextWeatherCondition() {
            return textWeatherCondition;
        }

        public TextView getTextMaxTemp() {
            return textMaxTemp;
        }

        public TextView getTextAvgTemp() {
            return textAvgTemp;
        }

        public TextView getTextMinTemp() {
            return textMinTemp;
        }

        public ImageView getImageWeatherIcon() {
            return imageWeatherIcon;
        }

        public View getParent() {
            return parent;
        }
    }

}
