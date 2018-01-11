package com.marvelcomics.brito.data.datasource.local.character;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.marvelcomics.brito.data.datasource.local.thumbnail.ThumbnailRoom;

@Entity(tableName = "character")
public class CharacterRoom {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String description;
    @Embedded
    private ThumbnailRoom thumbnailRoom;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public ThumbnailRoom getThumbnailRoom() {
        return thumbnailRoom;
    }

    public void setThumbnailRoom(ThumbnailRoom thumbnailRoom) {
        this.thumbnailRoom = thumbnailRoom;
    }
}
