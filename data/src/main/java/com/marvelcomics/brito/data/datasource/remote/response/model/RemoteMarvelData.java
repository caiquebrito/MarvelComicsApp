package com.marvelcomics.brito.data.datasource.remote.response.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RemoteMarvelData<T> {
    @SerializedName("offset")
    private int offset;
    @SerializedName("limit")
    private int limit;
    @SerializedName("total")
    private int total;
    @SerializedName("count")
    private int count;
    @SerializedName("results")
    private List<T> results;

    public List<T> getResults() {
        return results;
    }
}
