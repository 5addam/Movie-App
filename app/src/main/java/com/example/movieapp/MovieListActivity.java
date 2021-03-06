package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.movieapp.adapters.MovieAdapter;
import com.example.movieapp.adapters.OnMovieListener;
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

public class MovieListActivity extends AppCompatActivity implements View.OnClickListener, OnMovieListener {


    RecyclerView recyclerView;
    MovieAdapter adapter;
//    Button btn;
    //ViewModel
    private MovieListViewModel movieListViewModel;

    boolean isPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        btn = findViewById(R.id.btn_click);

        //SearchView
        setupSearchView();

        recyclerView = findViewById(R.id.recycler_view);


        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        initRecyclerView();

        //Calling the observers
        ObserverChanges();

        setupSearchView();

//        searchMovieApi("fast",1);

//        btn.setOnClickListener(this);

        observePopularMovies();

        // Getting the popular movies
        movieListViewModel.searchPopularMovieApi(1);

    }

    private void observePopularMovies() {
        movieListViewModel.getPopularMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observing any data change
                if(movieModels != null){
                    for(MovieModel movie: movieModels){
                        //getting the data
                        Log.v("Tag","onChanged "+movie.getTitle());
                    }

                    adapter.setmMovies(movieModels);

                }

            }
        });
    }

    //Observing any data changes
    private void ObserverChanges(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observing any data change
                if(movieModels != null){
                    for(MovieModel movie: movieModels){
                        //getting the data
                        Log.v("Tag","onChanged "+movie.getTitle());
                    }

                    adapter.setmMovies(movieModels);

                }

            }
        });
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.btn_click) {
//            GetRetrofitMovieListResponse();
//            GetRetrofitMovieResponse();
//            searchMovieApi("Fast",2);
//        }

    }

    //4 - Calling method in Main activity
    private void searchMovieApi(String query, int pageNumber){
        movieListViewModel.searchMovieApi(query,pageNumber);
    }
//
//    private void GetRetrofitMovieListResponse() {
//        MovieApi movieApi = Service.getMovieApi();
//
//        Call<MovieSearchResponse> responseCall = movieApi
//                .searchMovie(
//                        Credentials.API_KEY,
//                        "Action",
//                        1
//                );
//
//        responseCall.enqueue(new Callback<MovieSearchResponse>() {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//                if (response.code() == 200) {
//                    Log.v("Tag", response.body().toString());
//
//                    List<MovieModel> movies = new ArrayList<>(response.body().getMovieModelList());
//
//                    for (MovieModel movie : movies) {
//                        Log.v("Tag",movie.getTitle());
//                    }
//                }
//                else {
//                    assert response.errorBody() != null;
//                    Log.v("Tag","Error "+response.errorBody().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    // Search with ID
//    private void GetRetrofitMovieResponse(){
//        MovieApi movieApi = Service.getMovieApi();
//        Call<MovieModel> responseCall =  movieApi
//                .getMovie(
//                        527774,
//                        Credentials.API_KEY
//                );
//
//        responseCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                if (response.code() == 200) {
//                    Log.v("Tag", response.body().toString());
//
//                    MovieModel movie = response.body();
//
//                    Log.v("Tag","Response:\n"+ "Title: "+movie.getTitle()+"\n"+movie.getOverview());
//
//                }
//                else {
//                    assert response.errorBody() != null;
//                    Log.v("Tag","Error "+response.errorBody().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//
//            }
//        });
//    }

    // 5 - initializing recyclerview & adding data to it
    private void initRecyclerView(){
        //Live data can't be passed via the constructor
        adapter = new MovieAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        //RecyclerView Pagination
        // Loading next page of api response

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollHorizontally(1)){
                    // Here we need to display the next results on the next page of api
                    movieListViewModel.searchNextPage();
                }
            }
        });
    }


    @Override
    public void onMovieClick(int position) {
        Toast.makeText(this,"Click the Item no.: "+position,Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MovieDetail.class);
        intent.putExtra("movie",adapter.getSelectedMovie(position));
        startActivity(intent);

    }

    @Override
    public void onCategoryClick(String category) {

    }


    //Getting data from searchView & query the api to get the results (Movies)
    private void setupSearchView() {
        final  SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(
                        query,
                        1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular = false;
            }
        });

    }
}