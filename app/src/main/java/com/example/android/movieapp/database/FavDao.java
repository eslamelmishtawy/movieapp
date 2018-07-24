package com.example.android.movieapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavDao {

    @Query("SELECT * FROM favs")
    LiveData<List<FavEntry>> loadAllFavs();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTask(FavEntry favEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(FavEntry favEntry);

    @Query("SELECT id = :userId FROM favs WHERE id = :userId")
    String selectUserById(String userId);


    @Query("DELETE FROM favs WHERE id = :userId")
    void deleteByUserId(String userId);

}
