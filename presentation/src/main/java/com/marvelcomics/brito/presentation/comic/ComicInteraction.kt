package com.marvelcomics.brito.presentation.comic

sealed class ComicInteraction {
    class LoadComicsById(val id: Int) : ComicInteraction()
}