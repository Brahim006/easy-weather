package com.practice.easyweather.services;

import android.os.AsyncTask;

import com.practice.easyweather.recyclerclasses.Weather;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class WeaterColsultingTask extends AsyncTask<ArrayList<String>,Integer,ArrayList<Weather>> {

    private static final String OPEN_WEATHER_KEY = "f1ab3298ed7153aac248f65ed2d00ccd";

    @Override
    protected ArrayList<Weather> doInBackground(ArrayList<String>... cityNames) {

        InputStream is = null;
        ArrayList<Weather> ret = new ArrayList<>();

        try {

            for(int i = 0; i < cityNames.length; i++){
                // Obtengo el lenguaje del dispositivo
                String lang = Locale.getDefault().getLanguage();
                // Obtención del JSON desde el servicio de OpenWeather
                String url = "api.openweathermap.org/data/2.5/weather?q=" +
                        cityNames[0].get(i) + "&appid=" + OPEN_WEATHER_KEY +
                        "&units=metric&lang=" + lang;
                is = new URL(url).openStream();

                if(is != null){

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
                    String jsonFile = "", line = "";

                    while((line = buffer.readLine()) != null){ jsonFile += line; }
                    is.close();

                    // Extracción de datos desde el archivo JSON
                    Weather weather = new Weather();

                    weather.setCityName(cityNames[0].get(i));
                    JSONObject jsonObject = new JSONObject(jsonFile);
                    JSONObject main = jsonObject.getJSONObject("main");

                    weather.setTemp((int) main.getDouble("temp"));
                    weather.setTermicSensation((int)main.getDouble("feels_like"));
                    weather.setMinTemp((int)main.getDouble("temp_min"));
                    weather.setMaxTemp((int)main.getDouble("temp_max"));
                    weather.setPressure(main.getInt("pressure"));

                    JSONObject jsonWeather = jsonObject.getJSONObject("weather");
                    weather.setImageID(jsonWeather.getString("icon"));
                    weather.setWeatherDescription(jsonWeather.getString("description"));

                    JSONObject windObject = jsonObject.getJSONObject("wind");
                    weather.setWind(windObject.getDouble("speed"));

                    ret.add(weather);

                } else
                    throw new Exception();

            } // fin for

        } catch (Exception e) {

        } finally {
            return ret;
        }

    } // fin doInBackground

}
