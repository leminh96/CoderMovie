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
    public static final int TYPE_POPULAR = 1;
    public static final int TYPE_NORMAL = 0;

    List<Movie> movies;

    public MovieAdapter(@NonNull Context context, List<Movie> movies) {
        super(context, -1);
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return movies.get(position).getType(movies.get(position).getVoteAverage());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        FiveStarViewHolder fiveStarViewHolder;
        int type = getItemViewType(position);

        if (convertView == null) {
            if (type == TYPE_NORMAL) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_movie, parent, false);
                viewHolder = new ViewHolder(convertView);
                fiveStarViewHolder = null;
                convertView.setTag(viewHolder);
            }
            else {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.popular_item_movie, parent, false);
                fiveStarViewHolder = new FiveStarViewHolder(convertView);
                viewHolder = null;
                convertView.setTag(fiveStarViewHolder);
            }
        }
        else {
            if (type == TYPE_NORMAL) {
                viewHolder = (ViewHolder) convertView.getTag();
                fiveStarViewHolder = null;
            }
            else {
                fiveStarViewHolder = (FiveStarViewHolder) convertView.getTag();
                viewHolder = null;
            }
        }
        Movie movie = movies.get(position);
        if (type == TYPE_NORMAL) {

            viewHolder.tvTitle.setText(movie.getTitle());
            viewHolder.tvOverView.setText(movie.getOverview());

            Configuration config = getContext().getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Glide.with(getContext())
                        .load(movie.getBackdropPath())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(viewHolder.ivCover);
            } else {
                Glide.with(getContext())
                        .load(movie.getPosterPath())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(viewHolder.ivCover);
            }
        }
        else {
            Glide.with(getContext())
                    .load(movie.getPosterPath())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(fiveStarViewHolder.ivCover);
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

    private class FiveStarViewHolder {
        final ImageView ivCover;

        FiveStarViewHolder(View convert) {
            ivCover = (ImageView) convert.findViewById(R.id.ivCoverPopular);
        }
    }
}
