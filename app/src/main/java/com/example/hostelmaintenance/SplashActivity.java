package com.example.hostelmaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(){
          public void run(){
              try {
                    sleep(4000);
              }catch (Exception e){
              }
              finally {
                  Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                  startActivity(i);
              }
          }
        };
        thread.start();
    }
}