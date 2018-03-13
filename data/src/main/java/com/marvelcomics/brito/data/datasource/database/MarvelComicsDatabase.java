package com.marvelcomics.brito.data.datasource.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.marvelcomics.brito.data.datasource.local.character.CharacterDao;
import com.marvelcomics.brito.data.datasource.local.character.CharacterRoom;

@Database(entities = {CharacterRoom.class}, version = MarvelComicsDatabase.VERSION, exportSchema = false)
public abstract class MarvelComicsDatabase extends RoomDatabase {
    static final int VERSION = 1;
    public abstract CharacterDao getCharacterDao();
}