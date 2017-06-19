package com.example.quocm.codermovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MovieApi {
    @GET("now_playing")
    Call<NowPlaying> nowPlaying();

    @GET
    Call<PlayTrailers> playTrailer(@Url String url);
}
