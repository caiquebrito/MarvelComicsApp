package com.marvelcomics.brito.presentation.viewmodel.comics;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.marvelcomics.brito.domain.comic.ComicsUseCase;
import com.marvelcomics.brito.entity.ComicEntity;
import com.marvelcomics.brito.presentation.ResourceModel;
import com.marvelcomics.brito.presentation.viewmodel.BaseObserver;
import com.marvelcomics.brito.presentation.viewmodel.BaseViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ComicsViewModel extends BaseViewModel {

    private MutableLiveData<ResourceModel<List<ComicEntity>>> comics;

    private ComicsUseCase comicsUseCase;

    public void setComicsUseCase(ComicsUseCase comicsUseCase) {
        this.comicsUseCase = comicsUseCase;
    }

    public LiveData<ResourceModel<List<ComicEntity>>> loadComics(int characterId) {
        if (comics == null) {
            comics = new MutableLiveData<>();
            comics.postValue(new ResourceModel<>(ResourceModel.State.LOADING, null, null));
            comicsUseCase.execute(characterId, new BaseObserver<>(comics));
        }
        return comics;
    }
}
