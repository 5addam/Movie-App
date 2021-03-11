package com.example.movieapp.response;


import com.example.movieapp.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// This class is for single movie request
public class MovieResponse {
    // 1 Finding the movie object

    @SerializedName("results")
    @Expose
    private MovieModel movieModel;

    public MovieModel getMovieModel(){return movieModel;}


    @Override
    public String toString() {
        return "MovieResponse{" +
                "movieModel=" + movieModel +
                '}';
    }
}
