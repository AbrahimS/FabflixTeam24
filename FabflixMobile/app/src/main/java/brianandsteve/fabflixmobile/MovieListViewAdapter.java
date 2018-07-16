package brianandsteve.fabflixmobile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieListViewAdapter extends ArrayAdapter<Movie> {
    private ArrayList<Movie> movieList;

    public MovieListViewAdapter(ArrayList<Movie> movieList, Context context) {
        super(context, R.layout.layout_movielist_row, movieList);
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.layout_movielist_row, parent, false);

        Movie movie = movieList.get(position);

        TextView movieTitleView = (TextView)view.findViewById(R.id.movieTitleView);
        TextView yearTextView = (TextView)view.findViewById(R.id.yearTextView);
        TextView directorTextView = (TextView)view.findViewById(R.id.directorTextView);
        TextView genresTextView = (TextView)view.findViewById(R.id.genresTextView);
        TextView starsTextView = (TextView)view.findViewById(R.id.starsTextView);
        TextView ratingTextView = (TextView)view.findViewById(R.id.ratingTextView);


        movieTitleView.setText("Title:\n" + movie.getTitle());
        yearTextView.setText("Year:\n" + movie.getYear());
        directorTextView.setText("Director:\n" + movie.getDirector());
        genresTextView.setText("Genres:\n" + movie.getGenres());
        starsTextView.setText("Stars:\n" + movie.getStars());
        ratingTextView.setText("Rating:\n"+movie.getRating());

        return view;
    }
}