package com.marvelcomics.brito.presentation.presenter.comics;

import com.marvelcomics.brito.entity.ComicEntity;

import java.util.List;

public interface ComicsContract {

    interface View {
        void showError(String message);
        void showComics(List<ComicEntity> comics);
    }

    interface Presenter {
        void loadComics(int characterId);
    }
}
