package com.example.quocm.codermovie;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    List<Movie> movies;

    public MovieAdapter(@NonNull Context context, List<Movie> movies) {
        super(context, -1);
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Movie movie = movies.get(position);
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverView.setText(movie.getOverview());

        Configuration config = getContext().getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Glide.with(getContext())
                    .load(movie.getBackdropPath())
                    .placeholder(R.drawable.loading_spinner)
                    .into(viewHolder.ivCover);
        }
        else {
            Glide.with(getContext())
                    .load(movie.getPosterPath())
                    .placeholder(R.drawable.loading_spinner)
                    .into(viewHolder.ivCover);
        }

        return convertView;
    }

    private class ViewHolder {
        final ImageView ivCover;
        final TextView tvTitle;
        final TextView tvOverView;

        ViewHolder(View convert) {
            ivCover = (ImageView) convert.findViewById(R.id.ivCover);
            tvTitle = (TextView) convert.findViewById(R.id.tvTitle);
            tvOverView = (TextView) convert.findViewById(R.id.tvOverView);
        }
    }
}
