package com.marvelcomics.brito.presentation.presenter.comics;

import com.marvelcomics.brito.domain.comic.ComicsUseCase;
import com.marvelcomics.brito.entity.ComicEntity;
import com.marvelcomics.brito.presentation.presenter.BasePresenter;
import com.marvelcomics.brito.presentation.presenter.PresenterObserver;

import java.util.List;

public class ComicsPresenter extends BasePresenter implements ComicsContract.Presenter {

    private ComicsUseCase comicsUseCase;
    private ComicsContract.View view;

    public void setComicsUseCase(ComicsUseCase comicsUseCase) {
        this.comicsUseCase = comicsUseCase;
    }

    @Override
    public void loadComics(int characterId) {
        comicsUseCase.execute(characterId, new PresenterObserver<List<ComicEntity>>() {
            @Override
            public void onNextObserver(List<ComicEntity> comicEntities) {
                if (!comicEntities.isEmpty()) {
                    view.showComics(comicEntities);
                } else {
                    onErrorObserver(new Exception("Comics is empty."));
                }
            }

            @Override
            public void onErrorObserver(Throwable throwable) {
                view.showError(throwable.getMessage());
            }
        });
    }

    public void setView(ComicsContract.View view) {
        this.view = view;
    }
}
