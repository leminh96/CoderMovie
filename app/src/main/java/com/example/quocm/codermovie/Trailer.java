package com.example.quocm.codermovie;

import com.google.gson.annotations.SerializedName;

public class Trailer {

    @SerializedName("name")
    private String name;

    @SerializedName("source")
    private String source;

    @SerializedName("type")
    private String type;

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }
}
