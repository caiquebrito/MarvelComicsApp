package com.marvelcomics.brito.presentation.viewmodel.comics;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.marvelcomics.brito.domain.comic.ComicsUseCase;
import com.marvelcomics.brito.entity.ComicEntity;
import com.marvelcomics.brito.presentation.ResourceModel;
import com.marvelcomics.brito.presentation.viewmodel.BaseViewModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ComicsViewModel extends BaseViewModel {

    private MutableLiveData<ResourceModel<List<ComicEntity>>> comics;

    private ComicsUseCase comicsUseCase;

    private CompositeDisposable disposables = new CompositeDisposable();

    public void setComicsUseCase(ComicsUseCase comicsUseCase) {
        this.comicsUseCase = comicsUseCase;
    }

    public LiveData<ResourceModel<List<ComicEntity>>> loadComics(int characterId) {
        if (comics == null) {
            comics = new MutableLiveData<>();
            comics.postValue(new ResourceModel<>(ResourceModel.State.LOADING, null, null));
            comicsUseCase.execute(
                    characterId,
                    schedulersProvider.computation(),
                    schedulersProvider.main(),
                    new ComicsObserver());
        }
        return comics;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    private class ComicsObserver implements Observer<List<ComicEntity>> {

        @Override
        public void onSubscribe(Disposable d) {
            disposables.add(d);
        }

        @Override
        public void onNext(List<ComicEntity> model) {
            comics.postValue(new ResourceModel<>(
                    ResourceModel.State.SUCCESS,
                    model,
                    null
            ));
        }

        @Override
        public void onError(Throwable e) {
            comics.postValue(new ResourceModel<>(
                    ResourceModel.State.ERROR,
                    null,
                    e.getMessage()
            ));
        }

        @Override
        public void onComplete() {
            // do nothing
        }
    }
}
