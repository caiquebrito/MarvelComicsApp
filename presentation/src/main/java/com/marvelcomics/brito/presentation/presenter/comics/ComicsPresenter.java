package com.marvelcomics.brito.presentation.presenter.comics;

import com.marvelcomics.brito.domain.comic.ComicsUseCase;
import com.marvelcomics.brito.entity.ComicEntity;
import com.marvelcomics.brito.infrastructure.di.ResourceProvider;
import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;
import com.marvelcomics.brito.presentation.R;
import com.marvelcomics.brito.presentation.presenter.BasePresenter;
import com.marvelcomics.brito.presentation.presenter.PresenterObserver;

import java.util.List;

public class ComicsPresenter extends BasePresenter<ComicsContract.View> implements ComicsContract.Presenter {

    private ComicsUseCase comicsUseCase;
    private ResourceProvider resourceProvider;

    public ComicsPresenter(ComicsContract.View view,
                           SchedulersProvider schedulersProvider,
                           ComicsUseCase comicsUseCase,
                           ResourceProvider resourceProvider) {
        super(view, schedulersProvider);
        this.comicsUseCase = comicsUseCase;
        this.resourceProvider = resourceProvider;
    }

    @Override
    public void loadComics(int characterId) {
        comicsUseCase.execute(characterId, new PresenterObserver<List<ComicEntity>>() {
            @Override
            public void onNextObserver(List<ComicEntity> comicEntities) {
                if (!comicEntities.isEmpty()) {
                    view.showComics(comicEntities);
                } else {
                    onErrorObserver(new Exception(resourceProvider.getString(R.string.comics_not_found)));
                }
            }

            @Override
            public void onErrorObserver(Throwable throwable) {
                view.showError(throwable.getMessage());
            }
        });
    }
}
