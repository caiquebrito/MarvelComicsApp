package com.marvelcomics.brito.data.datasource.remote.response.model;

import com.google.gson.annotations.SerializedName;

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
}
