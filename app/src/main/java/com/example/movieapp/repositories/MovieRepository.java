package com.example.movieapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    //This class is acting as repositories

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;

    public static MovieRepository getInstance() {
        if (instance == null)
            instance = new MovieRepository();

        return instance;
    }

    private MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieApiClient.getMovies();
    }

    //2 - Calling the method in repository
    public void searchMovieApi(String query, int pageNumber) {
        mQuery = query;
        mPageNumber = pageNumber;

        movieApiClient.searchMoviesApi(query, pageNumber);
    }

    public void searchNextPage() {
        searchMovieApi(mQuery, mPageNumber + 1);
    }


    public LiveData<List<MovieModel>> getPopularMovies() {
        return movieApiClient.getPopularMovies();
    }

    public void searchPopularMovie(int pageNumber) {
        mPageNumber = pageNumber;

        movieApiClient.searchPopularMoviesApi(pageNumber);
    }
}
