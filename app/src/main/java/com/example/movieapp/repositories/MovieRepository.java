package com.example.movieapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.models.MovieModel;

import java.util.List;

public class MovieRepository {
    //This class is acting as repositories

    //This is LiveData
    private MutableLiveData<List<MovieModel>> mutableLiveData;

    private static MovieRepository instance;

    public static MovieRepository getInstance(){
        if(instance == null)
            instance = new MovieRepository();

        return instance;
    }

    private MovieRepository(){
        mutableLiveData = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mutableLiveData;
    }
}
