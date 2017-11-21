package com.marvelcomics.brito.entity;

public class CharacterEntity {

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
}