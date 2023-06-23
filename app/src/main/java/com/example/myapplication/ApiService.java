package com.example.myapplication;

import com.example.myapplication.model.Baju;
import com.example.myapplication.model.Celana;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("bajus")
    Call<List<Baju>> getBajus();

    @POST("bajus")
    Call<Baju> createBaju(@Body Baju baju);

    @PUT("bajus/{id}")
    Call<Baju> updateBaju(@Path("id") String id, @Body Baju baju);

    @DELETE("bajus/{id}")
    Call<Void> deleteBaju(@Path("id") String id);

    @GET("celanas")
    Call<List<Celana>> getCelanas();

    @POST("celanas")
    Call<Celana> createCelana(@Body Celana celana);

    @PUT("celanas/{id}")
    Call<Celana> updateCelana(@Path("id") String id, @Body Celana celana);

    @DELETE("celanas/{id}")
    Call<Void> deleteCelana(@Path("id") String id);



}
