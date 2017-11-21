package com.marvelcomics.brito.marvelcomics.application.injection.module;

import com.marvelcomics.brito.data.datasource.remote.MarvelWebService;
import com.marvelcomics.brito.data.repository.character.CharacterRepository;
import com.marvelcomics.brito.data.repository.character.RemoteCharacterRepository;
import com.marvelcomics.brito.data.repository.comics.ComicRepository;
import com.marvelcomics.brito.data.repository.comics.RemoteComicRepository;
import com.marvelcomics.brito.data.repository.series.RemoteSeriesRepository;
import com.marvelcomics.brito.data.repository.series.SeriesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    static CharacterRepository providesRemoteCharacterRepository(MarvelWebService marvelWebService) {
        return new RemoteCharacterRepository(marvelWebService);
    }

    @Provides
    @Singleton
    static ComicRepository providesRemoteComicRepository(MarvelWebService marvelWebService) {
        return new RemoteComicRepository(marvelWebService);
    }

    @Provides
    @Singleton
    static SeriesRepository providesRemoteSeriesRepository(MarvelWebService marvelWebService) {
        return new RemoteSeriesRepository(marvelWebService);
    }
}
