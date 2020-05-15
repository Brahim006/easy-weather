package com.practice.easyweather.recyclerclasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.easyweather.R;

import java.util.ArrayList;

/**
 * Adaptador para la gestión de entrada y salidad de datos a la lista del RecyclerView.
 * Es a la vez listener para eventos generados al clickear sus componentes.
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder>
                            implements View.OnClickListener {

    ArrayList<Weather> dataSet;
    private View.OnClickListener listener;

    /**
     * Genera un adaptador que se encarga de crear todos los items de la lista con
     * los datos ingresados.
     * @param dataSet Un ArrayList de objetos Weather conteniendo información del
     *                clima de cada ciudad.
     */
    public WeatherAdapter(ArrayList<Weather> dataSet){
        super();
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.list_item_layout,parent,false);

        WeatherHolder holder = new WeatherHolder(view);

        return holder;

    } // fin onCreateViewHolder

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, int position) {

        Weather weather = dataSet.get(position);

        holder.textCityName.setText(weather.getCityName());
        holder.textTemperature.setText(String.valueOf(weather.getTemp()) + "°");
        holder.textMinTemp.setText(String.valueOf(weather.getMinTemp()) + "°");
        holder.textMaxTemp.setText(String.valueOf(weather.getMaxTemp()) + "°");
        holder.textTermicSensation.setText(String.valueOf(weather.getTermicSensation()) + "°");
        holder.textDescription.setText(weather.getWeatherDescription());
        holder.textPressure.setText(String.valueOf(weather.getPressure()) + "hPa");
        holder.textWind.setText(String.valueOf(weather.getWind()) + "m/s");

        holder.imageWeather.setImageResource(generateImageId(weather.getImageID()));

        // Se le asigna un ID a cada cardview basado en su posición dentro del RecyclerView,
        // pero s ele suma uno ya que el ID debe ser positivo y se quiere evitar conflictos con
        // el primer elemento de la lista
        holder.cardView.setId(position + 1);
        holder.cardView.setOnClickListener(this);

    } // fin onBindViewHolder

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /*
     * Retorna el código interno de la imagen correspondiente al ID devuelto por
     * el JSON del clima.
     */
    private int generateImageId(String id){

        switch (id){
            case "01d":
                return R.drawable.icon_01d;
            case "01n":
                return R.drawable.icon_01n;
            case "02d":
                return R.drawable.icon_02d;
            case "02n":
                return R.drawable.icon_02n;
            case "03d":
                return R.drawable.icon_03d;
            case "03n":
                return R.drawable.icon_03n;
            case "04d":
                return R.drawable.icon_04d;
            case "04n":
                return R.drawable.icon_04n;
            case "09d":
                return R.drawable.icon_09d;
            case "09n":
                return R.drawable.icon_09n;
            case "10d":
                return R.drawable.icon_10d;
            case "10n":
                return R.drawable.icon_10n;
            case "11d":
                return R.drawable.icon_11d;
            case "11n":
                return R.drawable.icon_11n;
            case "13d":
                return R.drawable.icon_13d;
            case "13n":
                return R.drawable.icon_13n;
            case "50d":
                return R.drawable.icon_50d;
            case "50n":
                return R.drawable.icon_50n;
            default:
                return -1;
        }

    } // fin generateImageId

    @Override
    public void onClick(View v) {
        if(listener != null) listener.onClick(v);
    }

    public void setOnClickListener(View.OnClickListener listener){
        // Recibe un listener externo
        this.listener = listener;
    }

    /*
     * Holder para la creación de cada Item
     */
    public class WeatherHolder extends RecyclerView.ViewHolder {

        TextView    textCityName, textTemperature, textMinTemp, textMaxTemp, textTermicSensation,
                    textDescription, textPressure, textWind;
        ImageView imageWeather;
        CardView cardView;

        public WeatherHolder(@NonNull final View itemView) {
            super(itemView);

            // Inicialización de recursos

            cardView = (CardView)itemView.findViewById(R.id.cardView);
            cardView.setRadius(20);

            textCityName = (TextView)itemView.findViewById(R.id.text_city_name);
            textTemperature = (TextView)itemView.findViewById(R.id.text_temp);
            textMinTemp = (TextView)itemView.findViewById(R.id.text_min_temp);
            textMaxTemp = (TextView)itemView.findViewById(R.id.text_max_temp);
            textTermicSensation = (TextView)itemView.findViewById(R.id.text_termic_sensation);
            textDescription = (TextView)itemView.findViewById(R.id.text_description);
            textPressure = (TextView)itemView.findViewById(R.id.text_pressure);
            textWind = (TextView)itemView.findViewById(R.id.text_wind);
            imageWeather = (ImageView)itemView.findViewById(R.id.imageView);

        } // fin WeatherHolder

    }

}
