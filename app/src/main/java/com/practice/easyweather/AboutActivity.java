package com.practice.easyweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.net.URI;

public class AboutActivity extends AppCompatActivity {

    // Links a las redes
    public static final String
            LINKEDIN_URL = "https://www.linkedin.com/in/eduardo-brahim-alí-938a87168",
            GITHUB_URL = "https://github.com/Brahim006";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void onClickLinkedInText(View v){

        // Delega la tarea al sistema de cómo quiere abrir el enlace
        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(LINKEDIN_URL));
        startActivity(browser);

    }

    public void onClickGitHubText(View v){

        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URL));
        startActivity(browser);

    }

}
