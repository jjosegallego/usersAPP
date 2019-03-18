package com.intergrupo.usuariosapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listaUsuarios extends AppCompatActivity {

    String dato1;


    RecyclerView recycler;

    private final String baseURL="http://directotesting.igapps.co";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        recycler= findViewById(R.id.listaplay);
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        Bundle extras = getIntent().getExtras();
        dato1=extras.getString("Token");

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UsuariosService usuarios = retrofit.create(UsuariosService.class);
        Call<List<Lista>> call = usuarios.getLista(dato1);

        call.enqueue(new Callback<List<Lista>>() {
            @Override
            public void onResponse(Call<List<Lista>> call, Response<List<Lista>> response) {

                if(!response.isSuccessful()){

                    return;
                }

                List<Lista> usuariosLista = response.body();

                AdaptadorUsuarios adapter = new AdaptadorUsuarios(usuariosLista);
                recycler.setAdapter(adapter);



            }

            @Override
            public void onFailure(Call<List<Lista>> call, Throwable t) {

            }


        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cerrar) {
            SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
            preferences.edit().clear().commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        if (id == R.id.acerca) {

        }

        return super.onOptionsItemSelected(item);
    }



}
