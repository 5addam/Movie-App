package com.example.movieapp.request;

import com.example.movieapp.utils.Credentials;
import com.example.movieapp.utils.MovieApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static MovieApi movieApi = retrofit.create(MovieApi.class);

    public MovieApi getMovieApi(){
        return movieApi;
    }
}