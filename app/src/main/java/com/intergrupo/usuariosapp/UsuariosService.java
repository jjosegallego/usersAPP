package com.intergrupo.usuariosapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface UsuariosService {
    @Headers("Content-Type: application/json")
    @GET("sch/prospects.json/")
    Call<List<Lista>> getLista(
            @Header("token") String token

    );
}
