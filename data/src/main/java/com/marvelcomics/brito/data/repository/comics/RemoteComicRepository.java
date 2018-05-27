package com.marvelcomics.brito.data.repository.comics;

import com.marvelcomics.brito.data.datasource.remote.MarvelWebService;
import com.marvelcomics.brito.data.datasource.remote.response.ComicResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;

import io.reactivex.Observable;

public class RemoteComicRepository implements ComicRepository {

    private MarvelWebService marvelWebService;

    public RemoteComicRepository(MarvelWebService marvelWebService) {
        this.marvelWebService = marvelWebService;
    }

    @Override
    public Observable<RemoteMarvelContainer<ComicResponse>> comics(int characterId) {
        return marvelWebService.comics(characterId).toObservable();
    }
}
