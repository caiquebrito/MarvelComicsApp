package com.marvelcomics.brito.data.datasource.remote.response;

import com.google.gson.annotations.SerializedName;

public class ComicResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("thumbnail")
    private ThumbnailResponse thumbnailResponse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ThumbnailResponse getThumbnailResponse() {
        return thumbnailResponse;
    }

    public void setThumbnailResponse(ThumbnailResponse thumbnailResponse) {
        this.thumbnailResponse = thumbnailResponse;
    }
}
