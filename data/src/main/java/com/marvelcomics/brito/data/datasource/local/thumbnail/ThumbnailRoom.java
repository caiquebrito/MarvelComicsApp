package com.marvelcomics.brito.data.datasource.local.thumbnail;

public class ThumbnailRoom {

    private long thumbnailId;
    private String path;
    private String extension;

    public long getThumbnailId() {
        return thumbnailId;
    }

    public void setThumbnailId(long thumbnailId) {
        this.thumbnailId = thumbnailId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
