package com.practice.easyweather.recyclerclasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase que encapsula la información necesaria sobre el clima de una ciudad dada.
 * Esta clase implementa la interfaz Parcelable, ya que los objetos btenidos a
 * partir de ella necesitan "construirse y deconstruirse" al ser pasados entre
 * instancias de la clase MainActvity una vez que esta se reinicie, evitando tener
 * que realizar las consultas correspondientes a OpenWeather más veces de las
 * necesarias.
 */
public class Weather implements Parcelable {

    private String cityName, weatherDescription, imageID;
    private int temp, minTemp, maxTemp, termicSensation, pressure;
    private double wind;

    /**
     * Genera un objeto Weather con todos sus atributos inicializados con valores por defecto.
     */
    public Weather(){}

    /**
     * Construye un objeto Weather a partir de información provista por un objeto
     * Parcel. Este constructor está pensado para ser usado automáticamente por
     * el bundle savedInstanceState utilizado por la actividad principal al recrear
     * los objetos del dataSet en caso de que se reinicie la actividad.
     * @param parcel
     */
    public Weather(Parcel parcel) {

        cityName = parcel.readString();
        weatherDescription = parcel.readString();
        imageID = parcel.readString();
        temp = parcel.readInt();
        minTemp = parcel.readInt();
        maxTemp = parcel.readInt();
        termicSensation = parcel.readInt();
        pressure = parcel.readInt();
        wind = parcel.readDouble();

    }

    // Setters y getters

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getTermicSensation() {
        return termicSensation;
    }

    public void setTermicSensation(int termicSensation) {
        this.termicSensation = termicSensation;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    // Métodos parseables

    @Override
    public int describeContents() {
        // Describe la instancia del objeto a través de su hashCode
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        // Inserta todos los atributos en el objeto Parcel para su posterior recuperación
        dest.writeString(cityName);
        dest.writeString(weatherDescription);
        dest.writeString(imageID);
        dest.writeInt(temp);
        dest.writeInt(minTemp);
        dest.writeInt(maxTemp);
        dest.writeInt(termicSensation);
        dest.writeInt(pressure);
        dest.writeDouble(wind);

    } // fin writeToParcel

    /*
     * Objeto Creator que se encarga de recrear los objetos Weater a
     * partir de un Parcel.
     */
    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

}
