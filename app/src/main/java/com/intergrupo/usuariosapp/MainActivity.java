package com.intergrupo.usuariosapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String baseURL="http://directotesting.igapps.co/";

    private TextView resultado;
    private EditText email;
    private EditText password;
    private Button logbtn;
    private String emailTxt,passwordtxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        logbtn=findViewById(R.id.logbtn);

        resultado=findViewById(R.id.texto);

        cargarPreferencias();



        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.onEditorAction(EditorInfo.IME_ACTION_DONE);
                password.onEditorAction(EditorInfo.IME_ACTION_DONE);
                emailTxt=String.valueOf(email.getText());
                passwordtxt=String.valueOf(password.getText());

                if(TextUtils.isEmpty(emailTxt) || TextUtils.isEmpty(passwordtxt)){
                    alerta("Por favor no deje campos sin llenar");
                }
                else{
                    Toast.makeText(getApplicationContext(), "Ingresando...", Toast.LENGTH_LONG).show();
                    new android.os.Handler().postDelayed(new Runnable() {
                        public void run() {
                           conexion(emailTxt,passwordtxt);
                        }

                        },2000);
                }


            }
        });

        /*call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(!response.isSuccessful()){
                    resultado.setText("code: "+response.code());
                    return;
                }
              resultado.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                resultado.setText(t.getMessage());
            }
        });
        /*call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<List<Login>> call, Response<List<Login>> response) {
                if(!response.isSuccessful()){
                    resultado.setText("code: "+response.code());
                    return;
                }
                listaLogin=response.body();

                for(Login login: listaLogin ){
                    String contenido="";
                    contenido += " authToken "+login.getAuthToken();
                    resultado.append(contenido);
                }
            }

            @Override
            public void onFailure(Call<List<Login>> call, Throwable t) {
                resultado.setText(t.getMessage());
            }
        });*/

    }

    private void alerta (String mensaje){
        new AlertDialog.Builder(this)
                .setTitle("Alerta") // definimos el titulo
                .setMessage(mensaje)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Error de usuario", "Campos vacios");
            }
        }).show();
    }

    private void cargarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("Credenciales",Context.MODE_PRIVATE);

        String usuario = preferences.getString("email","nan");
        String contra = preferences.getString("password","nan");
        String token = preferences.getString("token", "nan");

        if(!token.equals("nan")){
            Intent intent = new Intent(getApplicationContext(), listaUsuarios.class);
            intent.putExtra("Token", token);
            startActivity(intent);
            finish();
        }

    }


    private void guardarPreferencias(String pemail, String ppassword, String token){
        SharedPreferences preferences = getSharedPreferences("Credenciales",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("email",pemail);
        editor.putString("password",ppassword);
        editor.putString("token",token);
        editor.commit();

    }




    private void conexion(String email, String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService loginService = retrofit.create(LoginService.class);
        loginService.getLogin(email,password).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(!response.isSuccessful()){
                    alerta("Revise los datos ingresados. (Error: "+response.code()+")");
                    resultado.setText("code: "+response.code());
                    return;
                }

                guardarPreferencias(emailTxt,passwordtxt,response.body().getAuthToken());
                Intent intent = new Intent(getApplicationContext(), listaUsuarios.class);
                intent.putExtra("Token", response.body().getAuthToken());
                resultado.setText("ok=> "+response.body().getAuthToken());
                startActivity(intent);
                finish();
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                alerta("Revise su conexion a internet. (Error: "+t.getMessage()+")");
                resultado.setText("Error "+t.getMessage());
            }
        });

    }

}
