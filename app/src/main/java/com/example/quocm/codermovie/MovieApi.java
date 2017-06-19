package com.example.quocm.codermovie;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApi {
    @GET("now_playing")
    Call<NowPlaying> nowPlaying();
}
