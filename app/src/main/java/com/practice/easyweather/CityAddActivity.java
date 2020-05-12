package com.practice.easyweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CityAddActivity extends AppCompatActivity {

    EditText editCityName;
    Button buttonCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_add);

        editCityName = findViewById(R.id.editCityName);
        buttonCityName = findViewById(R.id.buttonCityAdd);

    } // fin onCreate

    public void onClickButtonCityAdd(View view){

    }

}
