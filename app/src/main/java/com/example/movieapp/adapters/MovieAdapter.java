package com.example.movieapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.MovieListActivity;
import com.example.movieapp.R;
import com.example.movieapp.models.MovieModel;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;
    public static final int DISPLAY_SEARCH = 2;

    public MovieAdapter(MovieListActivity movieListActivity) {
        this.onMovieListener = movieListActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == DISPLAY_SEARCH) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,
                    parent, false);
            return new MovieViewHolder(view, onMovieListener);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movie_item,
                    parent, false);
            return new MovieViewHolder(view, onMovieListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        ((MovieViewHolder) holder).title.setText(mMovies.get(position).getTitle());
//        ((MovieViewHolder) holder).release_date.setText(mMovies.get(position).getRelease_date());
//        ((MovieViewHolder) holder).language.setText(mMovies.get(position).getOriginal_language());

        ((MovieViewHolder) holder).ratingBar.setRating(mMovies.get(position).getVote_average() / 2);

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + mMovies.get(position).getPoster_path())
                .into(((MovieViewHolder) holder).imageView);

    }

    @Override
    public int getItemCount() {
        if (mMovies != null)
            return mMovies.size();
        return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    //  Getting the id of the movie clicked
    public MovieModel getSelectedMovie(int position) {
        if (mMovies != null)
            if (mMovies.size() > 0)
                return mMovies.get(position);

        return null;
    }
}
