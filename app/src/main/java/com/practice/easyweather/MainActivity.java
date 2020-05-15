package com.practice.easyweather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.practice.easyweather.sql.*;
import com.practice.easyweather.recyclerclasses.*;
import com.practice.easyweather.services.WeaterColsultingTask;

import java.util.ArrayList;

/**
 * Actividad principal la cual se encarga de mostrar los datos correspondientes
 * a cada ciudad a través de un recyclerView.
 */
public class MainActivity extends AppCompatActivity {

    public static ArrayList<Weather> dataSet;
    public static WeatherAdapter weatherAdapter;

    public static CityAdapter db;
    public ArrayList<String> cityNames;

    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    public static final int CITY_NAME_RESULT = 1,
                            SOUNDS_MAX_STREAMS = 1;
    public static final String NEW_CITY_NAME = "new_city_name",
                               PARCELABLE_ARRAY_KEY = "dataset";

    // Generación de sonidos
    public static SoundPool soundPool;
    public static int pressedButtonSound, failSound, cardViewSound;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización del SoundPool y sus sonidos
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder().setMaxStreams(SOUNDS_MAX_STREAMS)
                                           .setAudioAttributes(audioAttributes)
                                           .build();
        pressedButtonSound = soundPool.load(getApplicationContext(),R.raw.press_button_sound,1);
        failSound = soundPool.load(getApplicationContext(),R.raw.fail_button_sound,1);
        cardViewSound = soundPool.load(getApplicationContext(),R.raw.cardview_sound,1);

        // Inicialización de recursos
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        fab = (FloatingActionButton)findViewById(R.id.fab);
        // Acceso a datos
        db = new CityAdapter(getApplicationContext());
        cityNames = db.getAllCityNames();
        // Recursos del RecyclerView

        if(savedInstanceState == null){
            dataSet = new ArrayList<>();
        } else {
            // Si hay una instancia guardada (se re-creó la actividad), recupera el dataSet
            dataSet = savedInstanceState.getParcelableArrayList(PARCELABLE_ARRAY_KEY);
        }

        weatherAdapter = new WeatherAdapter(dataSet);

        weatherAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());

                soundPool.play(cardViewSound,1,1,1,0,1);

                final int cardId = v.getId();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getItemId() == R.id.delete_city_item){
                            soundPool.play(pressedButtonSound,1,1,1,0,1);

                            String cityName = dataSet.get(cardId - 1).getCityName();
                            // Borrado de la base de datos
                            db.deleteCityName(cityName);
                            // Borrado del dataSet
                            dataSet.remove(cardId - 1);
                            weatherAdapter.notifyDataSetChanged();

                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        }); // fin setOnClickListener

        recyclerView.setAdapter(weatherAdapter);

        // Ejecuta la búsqueda de datos desde internet sólo si es la primera vez que se inicia la
        // actividad en esta instancia, caso omiso sólo se cargan los datos ya almacenados en el
        // dataSet
        if(savedInstanceState == null){
            new WeaterColsultingTask().execute(cityNames);
        }

    } // fin onCreate

    /*
     * Inicia la segunda actividad con la intención de obtener una ciudad
     * ingresada por el usuario.
     */
    public void onClickFloatingActionButton(View view){

        soundPool.play(pressedButtonSound,1,1,1,0,1);
        Intent intent = new Intent(this,CityAddActivity.class);
        startActivityForResult(intent,CITY_NAME_RESULT);

    }

    /*
     * Al recibir el nombre de la nueva ciudad, la inserta en la base de datos y realiza la
     * consulta para ingresarla en la lista.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == CITY_NAME_RESULT){

            if(resultCode == RESULT_OK){

                soundPool.play(pressedButtonSound,1,1,1,0,1);

                String newCityName = data.getStringExtra(NEW_CITY_NAME);

                db.insertCityName(newCityName);
                ArrayList<String> coll = new ArrayList<>();
                coll.add(newCityName);

                new WeaterColsultingTask().execute(coll);
            } else if(requestCode == RESULT_CANCELED){

                soundPool.play(failSound,1,1,1,0,1);

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    } // fin onActivityResult

    // Configuración de menúes

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_about:
                soundPool.play(pressedButtonSound,1,1,1,0,1);
                // Inicia la actividad de "Acerca de"
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    // Métodos de persistencia de información de la instancia iniciada

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        // Se encarga de guardar la colección con los objetos guardados de manera
        // que al re-crear la actividad no sea necesario realizar la consolta
        // al servicio de OpenWeather
        outState.putParcelableArrayList(PARCELABLE_ARRAY_KEY,dataSet);

    } // fin onSaveInstanceState

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        // Restaura el dataSet. Este método es llamado inmediatamente después de
        // onStart, por lo que la restauración no depende del método onCreate
        if(dataSet == null){
            dataSet = savedInstanceState.getParcelableArrayList(PARCELABLE_ARRAY_KEY);
        }

    } // fin onRestoreInstanceState
}
