package com.marvelcomics.brito.entity

data class CharacterEntity(
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnailEntity: ThumbnailEntity?
)
