package com.marvelcomics.brito.data.datasource.local.character;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(CharacterRoom characterRoom);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCharacter(CharacterRoom characterRoom);

    @Delete
    void deleteCharacter(CharacterRoom characterRoom);

    @Query("select * from character where id = 1")
    CharacterRoom getcharacterById();
}
