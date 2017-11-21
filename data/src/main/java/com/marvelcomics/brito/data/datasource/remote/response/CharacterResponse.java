package com.marvelcomics.brito.data.datasource.remote.response;

import com.google.gson.annotations.SerializedName;

public class CharacterResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("modified")
    private String modified;
    @SerializedName("thumbnail")
    private ThumbnailResponse thumbnailResponse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public ThumbnailResponse getThumbnailResponse() {
        return thumbnailResponse;
    }

    public void setThumbnailResponse(ThumbnailResponse thumbnailResponse) {
        this.thumbnailResponse = thumbnailResponse;
    }
}
