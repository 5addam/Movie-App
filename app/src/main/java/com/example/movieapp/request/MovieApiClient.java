package com.example.movieapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.response.MovieSearchResponse;
import com.example.movieapp.threads.AppExecutors;
import com.example.movieapp.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //This is LiveData
    private MutableLiveData<List<MovieModel>> mutableLiveData;

    private static MovieApiClient instance;

    //making global runnable request
    private RetrieveMoviesRunnable moviesRunnable;

    public static MovieApiClient getInstance() {
        if (instance == null)
            instance = new MovieApiClient();
        return instance;
    }

    private MovieApiClient() {
        mutableLiveData = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return mutableLiveData;
    }

    //1 - This method will be used to call through the classes
    public void searchMoviesApi(String query, int pageNumber) {

        if(moviesRunnable != null)
            moviesRunnable = null;

        moviesRunnable = new RetrieveMoviesRunnable(query,pageNumber);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(moviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                ///canceling the retrofit call
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);

    }


    //Retrieving data from RestApi by runnable class
    //We have 2 types of Queries
    private class RetrieveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            // getting the response object
            try {
                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest)
                    return;
                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovieModelList());
                    if(pageNumber == 1){
                        //sending data to live data
                        //PostData: used for background thread
                        //setValue: not for background thrad
                        mutableLiveData.postValue(list);
                    }
                    else {
                        List<MovieModel> currentMovies = mutableLiveData.getValue();
                        currentMovies.addAll(list);
                        mutableLiveData.postValue(currentMovies);
                    }
                }
                else {
                    String error = response.errorBody().string();
                    Log.v("Tag","Error: "+error);
                    mutableLiveData.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mutableLiveData.postValue(null);
            }
            if (cancelRequest)
                return;

            // Search Method/query
        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            return Service.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Canceling Search Req");
            cancelRequest = true;
        }
    }
}
