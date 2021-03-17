package com.example.movieapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    //This class is used for ViewModel
    private MovieRepository movieRepository;


    //Constructor
    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }

    //3 - Calling method in viewModel
    public void searchMovieApi(String query, int pageNumber){
        movieRepository.searchMovieApi(query,pageNumber);
    }

    public void searchNextPage(){
        movieRepository.searchNextPage();
    }

    public LiveData<List<MovieModel>> getPopularMovies(){
        return movieRepository.getPopularMovies();
    }

    public void searchPopularMovieApi(int pageNumber){
        movieRepository.searchPopularMovie(pageNumber);
    }
}
