package com.practice.easyweather;

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

        if(!cityName.equals("")){ // Verifica un nombre válido
            // Intent para el envío del nombre de la ciudad
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(MainActivity.NEW_CITY_NAME,cityName);

            setResult(RESULT_OK,intent);
            finish();

        } else {
            Toast.makeText(this,getResources().getString(R.string.wrong_city_name),
                    Toast.LENGTH_LONG);
        }

    } // fin onClickButtonCityAdd

}
