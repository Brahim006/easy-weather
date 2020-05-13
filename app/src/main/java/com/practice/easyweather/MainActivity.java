package com.practice.easyweather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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

    public static final int CITY_NAME_RESULT = 1;
    public static final String NEW_CITY_NAME = "new_city_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de recursos
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        fab = (FloatingActionButton)findViewById(R.id.fab);
        // Acceso a datos
        db = new CityAdapter(getApplicationContext());
        cityNames = db.getAllCityNames();
        // Recursos del RecyclerView
        dataSet = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(dataSet);
        recyclerView.setAdapter(weatherAdapter);

        new WeaterColsultingTask().execute(cityNames);

    } // fin onCreate

    /*
     * Inicia la segunda actividad con la intención de obtener una ciudad
     * ingresada por el usuario.
     */
    public void onClickFloatingActionButton(View view){

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
                String newCityName = data.getStringExtra(NEW_CITY_NAME);

                db.insertCityName(newCityName);
                ArrayList<String> coll = new ArrayList<>();
                coll.add(newCityName);

                new WeaterColsultingTask().execute(coll);
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
                break;
        }
        return true;
    }

}
