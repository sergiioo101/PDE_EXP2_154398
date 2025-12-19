package com.example.pde_exp2_154398.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.pde_exp2_154398.data.AppDatabase;
import com.example.pde_exp2_154398.data.Dog;
import com.example.pde_exp2_154398.data.DogDao;

import java.util.List;

public class DogRepository {
    private DogDao dogDao;
    private LiveData<List<Dog>> allDogs;
    
    public DogRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        dogDao = database.dogDao();
        allDogs = dogDao.getAllDogs();
    }
    
    public LiveData<List<Dog>> getAllDogs() {
        return allDogs;
    }
    
    public LiveData<Dog> getDogById(int dogId) {
        return dogDao.getDogById(dogId);
    }
}

