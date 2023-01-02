package com.example.resturantsnearme;

import com.example.resturantsnearme.model.Restaurants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Data {
    @GET("search")
    Call<Restaurants> getData(@Query("location") String loc, @Header("Authorization") String authHeader);
}
