package com.intergrupo.usuariosapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class Splash extends AppCompatActivity {

    public static final int segundos=4;
    public static final int milisegundos=segundos*1000;
    private ProgressBar progreso;
    public static final int delay=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        progreso=(ProgressBar)findViewById(R.id.barra);
        empezaranimacion();


    }
    public void empezaranimacion(){
        new CountDownTimer(milisegundos,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                progreso.setProgress(establecer(millisUntilFinished));
                progreso.setMax(maximo());
            }
            @Override
            public void onFinish() {
                Intent nuevo=new Intent(Splash.this,MainActivity.class);
                startActivity(nuevo);
                finish();
            }
        }.start();
    }
    public int establecer(long mili){
        return (int)(milisegundos-mili)/1000;
    }

    public int maximo(){
        return segundos-delay;
    }
}

