package com.example.pde_exp2_154398.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DogDao {
    @Query("SELECT * FROM dogs ORDER BY name ASC")
    LiveData<List<Dog>> getAllDogs();
    
    @Query("SELECT * FROM dogs WHERE id = :dogId")
    LiveData<Dog> getDogById(int dogId);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Dog> dogs);
    
    @Query("SELECT COUNT(*) FROM dogs")
    int getDogCount();
}

