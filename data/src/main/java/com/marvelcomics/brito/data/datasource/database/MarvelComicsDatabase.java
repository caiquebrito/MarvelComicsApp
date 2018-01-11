package com.marvelcomics.brito.data.datasource.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.marvelcomics.brito.data.datasource.local.character.CharacterDao;
import com.marvelcomics.brito.data.datasource.local.character.CharacterRoom;

@Database(entities = {CharacterRoom.class}, version = 1, exportSchema = false)
public abstract class MarvelComicsDatabase extends RoomDatabase {
    abstract CharacterDao characterDao();
}