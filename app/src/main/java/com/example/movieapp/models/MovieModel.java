package com.example.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {
    //Model class for our movies

    private int movie_id;
    private String title;
    private String poster_path;
    private String release_date;
    private float vote_average;
    private String overview;
    private String original_language;


    //Constructor
    public MovieModel(int movie_id, String title, String poster_path, String release_date,
                      float vote_average, String overview, String original_language) {
        this.movie_id = movie_id;
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.overview = overview;
        this.original_language = original_language;
    }

    protected MovieModel(Parcel in) {
        movie_id = in.readInt();
        title = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        vote_average = in.readFloat();
        overview = in.readString();
        original_language = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    //Getters
    public int getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginal_language() {
        return original_language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movie_id);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeFloat(vote_average);
        dest.writeString(overview);
        dest.writeString(original_language);
    }
}
