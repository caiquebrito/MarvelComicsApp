package com.marvelcomics.brito.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class CharacterEntity implements Parcelable {

    private int id;
    private String name;
    private String description;
    private ThumbnailEntity thumbnailEntity;

    public CharacterEntity(int id, String name, String description, ThumbnailEntity thumbnailEntity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnailEntity = thumbnailEntity;
    }

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

    public ThumbnailEntity getThumbnailEntity() {
        return thumbnailEntity;
    }

    public void setThumbnailEntity(ThumbnailEntity thumbnailEntity) {
        this.thumbnailEntity = thumbnailEntity;
    }

    public String getFullUrlThumbnailWithAspect(String aspect) {
        ThumbnailEntity thumb = this.getThumbnailEntity();
        if (aspect.isEmpty()) {
            return thumb.getPath() + "." + thumb.getExtension();
        } else {
            return thumb.getPath() + "/" + aspect + "." + thumb.getExtension();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeParcelable(this.thumbnailEntity, flags);
    }

    protected CharacterEntity(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.thumbnailEntity = in.readParcelable(ThumbnailEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<CharacterEntity> CREATOR = new Parcelable.Creator<CharacterEntity>() {
        @Override
        public CharacterEntity createFromParcel(Parcel source) {
            return new CharacterEntity(source);
        }

        @Override
        public CharacterEntity[] newArray(int size) {
            return new CharacterEntity[size];
        }
    };
}