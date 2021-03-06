package com.example.movieapp.response;

import com.example.movieapp.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//This class is for getting multiple movies (movie list) - popular movies
public class MovieSearchResponse {

    @SerializedName("total_results")
    @Expose()
    private  int total_count;

    @SerializedName("results")
    @Expose()
    private List<MovieModel> movieModelList;


    public  int getTotal_count(){return total_count;}
    public List<MovieModel> getMovieModelList(){return movieModelList;}

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "total_count=" + total_count +
                ", movieModelList=" + movieModelList +
                '}';
    }
}
