package com.example.quocm.codermovie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlayTrailers {
    @SerializedName("youtube")
    private List<Trailer> trailers;

    public List<Trailer> getTrailers() {
        return trailers;
    }
}
