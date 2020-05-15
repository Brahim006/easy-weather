package com.practice.easyweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Segunda actividad que se inicia con la intención de poroporcionar una interfaz
 * al usuario para que ingrese una nueva ciudad a la lista.
 */
public class CityAddActivity extends AppCompatActivity {

    private EditText editCityName;
    private Button buttonCityName;
    public static final String CITY_NAME_KEY = "city_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_add);

        editCityName = findViewById(R.id.editCityName);
        buttonCityName = findViewById(R.id.buttonCityAdd);

    } // fin onCreate

    /*
     * Lógica del botón para añadir ciudades.
     */
    public void onClickButtonCityAdd(View view){

        String cityName = editCityName.getText().toString();

        // Intent para el envío del nombre de la ciudad
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(MainActivity.NEW_CITY_NAME,cityName);

        if(!cityName.equals("")){ // Verifica un nombre válido

            setResult(RESULT_OK,intent);
            finish();

        } else {
            setResult(RESULT_CANCELED,intent);
            finish();
        }

    } // fin onClickButtonCityAdd

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Simplemente se encarga de persistir el texto ingresado al EditText
        // Para evitar que se borre al recrear la actividad
        outState.putString(CITY_NAME_KEY,editCityName.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editCityName.setText(savedInstanceState.getString(CITY_NAME_KEY));
    }
}
