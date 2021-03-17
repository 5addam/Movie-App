package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.models.MovieModel;

public class MovieDetail extends AppCompatActivity {

    //Widgets
    ImageView imageViewDetail;
    TextView titleDetail, descriptionDetail;
    RatingBar ratingBarDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        imageViewDetail = findViewById(R.id.imageView_detail);
        titleDetail = findViewById(R.id.textView_detail);
        descriptionDetail = findViewById(R.id.textView_description);

        ratingBarDetail = findViewById(R.id.ratingBar_detail);

        getDataFromIntent();
    }

    private void getDataFromIntent() {
        if(getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
//            Log.v("Tag","Incoming intent "+movieModel.getTitle());

            titleDetail.setText(movieModel.getTitle());
            descriptionDetail.setText(movieModel.getOverview());
            ratingBarDetail.setRating(movieModel.getVote_average()/2);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500"+movieModel.getPoster_path())
                    .into(imageViewDetail);
        }

    }
}