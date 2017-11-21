package com.marvelcomics.brito.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ThumbnailEntity implements Parcelable {

    private String path;
    private String extension;

    public ThumbnailEntity(String path, String extension) {
        this.path = path;
        this.extension = extension;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.extension);
    }

    protected ThumbnailEntity(Parcel in) {
        this.path = in.readString();
        this.extension = in.readString();
    }

    public static final Parcelable.Creator<ThumbnailEntity> CREATOR = new Parcelable.Creator<ThumbnailEntity>() {
        @Override
        public ThumbnailEntity createFromParcel(Parcel source) {
            return new ThumbnailEntity(source);
        }

        @Override
        public ThumbnailEntity[] newArray(int size) {
            return new ThumbnailEntity[size];
        }
    };
}
