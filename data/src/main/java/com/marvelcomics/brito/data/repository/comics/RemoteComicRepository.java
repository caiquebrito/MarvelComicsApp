package com.marvelcomics.brito.data.repository.comics;

import com.marvelcomics.brito.data.datasource.remote.MarvelWebService;
import com.marvelcomics.brito.data.mapper.ComicEntityMapper;
import com.marvelcomics.brito.entity.ComicEntity;

import java.util.List;

import io.reactivex.Observable;

public class RemoteComicRepository implements ComicRepository {

    private MarvelWebService marvelWebService;

    public RemoteComicRepository(MarvelWebService marvelWebService) {
        this.marvelWebService = marvelWebService;
    }

    @Override
    public Observable<List<ComicEntity>> comics(int characterId) {
        return marvelWebService.comics(characterId)
                .map(ComicEntityMapper::transform)
                .toObservable();
    }
}
