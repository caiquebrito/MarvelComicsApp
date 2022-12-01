package com.marvelcomics.brito.entity

data class ThumbnailEntity(
    val path: String? = null,
    val extension: String? = null
) {
    fun getFullUrlThumbnailWithAspect(aspect: String): String {
        return if (aspect.isEmpty()) {
            path.toString() + "." + extension
        } else {
            path.toString() + "/" + aspect + "." + extension
        }
    }
}
