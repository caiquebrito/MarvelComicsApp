package com.marvelcomics.brito.presentation.viewmodel.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.marvelcomics.brito.domain.character.CharactersUseCase;
import com.marvelcomics.brito.entity.CharacterEntity;
import com.marvelcomics.brito.presentation.ResourceModel;
import com.marvelcomics.brito.presentation.viewmodel.BaseViewModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class HomeViewModel extends BaseViewModel {

    private MutableLiveData<ResourceModel<List<CharacterEntity>>> characters;

    private CharactersUseCase charactersUseCase;

    private CompositeDisposable disposables = new CompositeDisposable();

    public void setCharactersUseCase(CharactersUseCase charactersUseCase) {
        this.charactersUseCase = charactersUseCase;
    }

    public LiveData<ResourceModel<List<CharacterEntity>>> loadCharacters(String name) {
        characters = new MutableLiveData<>();
        characters.postValue(new ResourceModel<>(ResourceModel.State.LOADING, null, null));
        charactersUseCase.execute(
                name,
                schedulersProvider.computation(),
                schedulersProvider.main(),
                new CharactersObserver());
        return characters;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    private class CharactersObserver implements Observer<List<CharacterEntity>> {

        @Override
        public void onSubscribe(Disposable d) {
            disposables.add(d);
        }

        @Override
        public void onNext(List<CharacterEntity> model) {
            characters.postValue(new ResourceModel<>(
                    ResourceModel.State.SUCCESS,
                    model,
                    null
            ));
        }

        @Override
        public void onError(Throwable e) {
            characters.postValue(new ResourceModel<>(
                    ResourceModel.State.ERROR,
                    null,
                    e.getMessage()
            ));
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            // do nothing
        }
    }
}
