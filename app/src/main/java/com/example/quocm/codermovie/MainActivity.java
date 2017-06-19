package com.example.quocm.codermovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private ListView lvMovies;
    private SwipeRefreshLayout swipeContainer;
    MovieApi movieApi;
    List<Movie> mMovies = new ArrayList<>();
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieAdapter = new MovieAdapter(this, mMovies);
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        lvMovies.setAdapter(movieAdapter);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        Retrofit retrofit = RetrofitUtil.create();
        movieApi = retrofit.create(MovieApi.class);
        fetchData();
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent mIntent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("backdrop_path", mMovies.get(position).getBackdropPath());
                mBundle.putString("release_date", mMovies.get(position).getReleaseDate());
                mBundle.putString("title", mMovies.get(position).getTitle());
                mBundle.putString("overview", mMovies.get(position).getOverview());
                mBundle.putFloat("vote_average", mMovies.get(position).getVoteAverage());
                mBundle.putLong("id", mMovies.get(position).getId());
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
    }

    private void fetchData() {
        movieApi.nowPlaying().enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                List<Movie> movies = response.body().getMovies();
                mMovies.clear();
                mMovies.addAll(movies);
                movieAdapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
                t.printStackTrace();
                swipeContainer.setRefreshing(false);
            }
        });
    }

}
