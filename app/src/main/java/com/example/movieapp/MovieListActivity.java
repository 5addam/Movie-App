package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.request.Service;
import com.example.movieapp.response.MovieSearchResponse;
import com.example.movieapp.utils.Credentials;
import com.example.movieapp.utils.MovieApi;
import com.example.movieapp.viewmodels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;
    //ViewModel
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn_click);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

//        btn.setOnClickListener(this);
    }

    //Observing any data changes
    private void ObserverChanges(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observing any data change

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_click) {
//            GetRetrofitMovieListResponse();
            GetRetrofitMovieResponse();
        }

    }

    private void GetRetrofitMovieListResponse() {
        MovieApi movieApi = Service.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi
                .searchMovie(
                        Credentials.API_KEY,
                        "Action",
                        "1"
                );

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    Log.v("Tag", response.body().toString());

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovieModelList());

                    for (MovieModel movie : movies) {
                        Log.v("Tag",movie.getTitle());
                    }
                }
                else {
                    assert response.errorBody() != null;
                    Log.v("Tag","Error "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

    // Search with ID

    private void GetRetrofitMovieResponse(){
        MovieApi movieApi = Service.getMovieApi();
        Call<MovieModel> responseCall =  movieApi
                .getMovie(
                        527774,
                        Credentials.API_KEY
                );

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code() == 200) {
                    Log.v("Tag", response.body().toString());

                    MovieModel movie = response.body();

                    Log.v("Tag","Response:\n"+ "Title: "+movie.getTitle()+"\n"+movie.getOverview());

                }
                else {
                    assert response.errorBody() != null;
                    Log.v("Tag","Error "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }
}