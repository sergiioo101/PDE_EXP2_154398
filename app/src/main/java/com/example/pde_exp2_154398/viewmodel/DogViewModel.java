package com.example.pde_exp2_154398.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pde_exp2_154398.data.Dog;
import com.example.pde_exp2_154398.repository.DogRepository;

import java.util.List;

public class DogViewModel extends AndroidViewModel {
    private DogRepository repository;
    private LiveData<List<Dog>> allDogs;
    
    public DogViewModel(Application application) {
        super(application);
        repository = new DogRepository(application);
        allDogs = repository.getAllDogs();
    }
    
    public LiveData<List<Dog>> getAllDogs() {
        return allDogs;
    }
    
    public LiveData<Dog> getDogById(int dogId) {
        return repository.getDogById(dogId);
    }
}

