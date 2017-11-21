package com.marvelcomics.brito.data.datasource.remote.response.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RemoteMarvelContainer<T> {

    @SerializedName("code")
    private int code;
    @SerializedName("status")
    private String status;
    @SerializedName("copyright")
    private String copyright;
    @SerializedName("attributionText")
    private String attributionText;
    @SerializedName("attributionHTML")
    private String attributionHTML;
    @SerializedName("etag")
    private String etag;
    @SerializedName("data")
    private RemoteMarvelData<T> remoteMarvelData;

    public RemoteMarvelData<T> getRemoteMarvelData() {
        return remoteMarvelData;
    }

    public class RemoteMarvelData<E> {

        @SerializedName("offset")
        private int offset;
        @SerializedName("limit")
        private int limit;
        @SerializedName("total")
        private int total;
        @SerializedName("count")
        private int count;
        @SerializedName("results")
        private List<E> results;

        public List<E> getResults() {
            return results;
        }
    }
}
