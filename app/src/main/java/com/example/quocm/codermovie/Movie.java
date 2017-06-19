package com.example.quocm.codermovie;


import com.google.gson.annotations.SerializedName;

public class Movie {
    private static final String POST_FIX = "https://image.tmdb.org/t/p/w500";

    @SerializedName("id")
    private long id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("vote_average")
    private float voteAverage;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return POST_FIX + posterPath;
    }

    public String getBackdropPath() {
        return POST_FIX + backdropPath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public int getType(float avg) {
        if (avg >= 7){
            return 1;
        }
        else return 0;
    }
}
