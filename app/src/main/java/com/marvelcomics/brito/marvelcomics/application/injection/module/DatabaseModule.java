package com.marvelcomics.brito.marvelcomics.application.injection.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.marvelcomics.brito.data.datasource.database.MarvelComicsDatabase;
import com.marvelcomics.brito.data.datasource.local.character.CharacterDao;
import com.marvelcomics.brito.data.datasource.local.character.CharacterDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Singleton
    @Provides
    MarvelComicsDatabase providesDatabase(Context context) {
        return Room.databaseBuilder(
                context,
                MarvelComicsDatabase.class,
                "marvel.db")
                .build();
    }

    @Singleton
    @Provides
    CharacterDao providesCharacterDao(MarvelComicsDatabase marvelComicsDatabase) {
        return marvelComicsDatabase.getCharacterDao();
    }

    @Singleton
    @Provides
    CharacterDataSource providesCharacterDataSource(CharacterDao characterDao) {
        return new CharacterDataSource(characterDao);
    }
}
