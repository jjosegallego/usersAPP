/*Interfaz encargada de recibir los dos argumentos (password y email) y hacer la peticion GET
* al servidor externo (API SERVER)*/


package com.intergrupo.usuariosapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface LoginService {

    @GET("application/login")
    Call<Login> getLogin(
         @Query("email") String usuario,
         @Query("password") String password
    );


}
