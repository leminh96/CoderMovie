package com.example.quocm.codermovie;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailActivity extends YouTubeBaseActivity {
    private ImageView ivCover;
    private ImageView ivPlayIcon;
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private RatingBar rbVote;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private MovieApi movieApi;
    private List<Trailer> trailers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ivCover = (ImageView) findViewById(R.id.ivCoverDetail);
        ivPlayIcon = (ImageView) findViewById(R.id.ivPlayIcon);
        tvTitle = (TextView) findViewById(R.id.tvTitleDetail);
        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        tvOverview = (TextView) findViewById(R.id.tvOverViewDetail);
        rbVote = (RatingBar) findViewById(R.id.ratingBar);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.ypvTrailer);
        Bundle extras = getIntent().getExtras();

        Retrofit retrofit = RetrofitUtil.create();
        movieApi = retrofit.create(MovieApi.class);

        String title = extras.getString("title");
        String backdropPath = extras.getString("backdrop_path");
        String releaseDate = extras.getString("release_date");
        String overview = extras.getString("overview");
        float avgVote = extras.getFloat("vote_average");
        long id = extras.getLong("id");

        movieApi.playTrailer(RetrofitUtil.BASE_URL + id + "/trailers").enqueue(new Callback<PlayTrailers>() {
            @Override
            public void onResponse(Call<PlayTrailers> call, Response<PlayTrailers> response) {
                trailers = response.body().getTrailers();
            }

            @Override
            public void onFailure(Call<PlayTrailers> call, Throwable t) {
                t.printStackTrace();
            }
        });

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(trailers.get(0).getSource());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        tvTitle.setText(title);
        tvReleaseDate.setText("Release date: " + releaseDate);
        tvOverview.setText(overview);
        rbVote.setRating(avgVote / 2);

        Glide.with(this.getApplicationContext())
                .load(backdropPath)
                .placeholder(R.mipmap.ic_launcher)
                .into(ivCover);

        ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivCover.setVisibility(View.INVISIBLE);
                ivPlayIcon.setVisibility(View.INVISIBLE);
                youTubePlayerView.initialize(BuildConfig.YOUTUBE_API_KEY, onInitializedListener);
            }
        });
    }
}
