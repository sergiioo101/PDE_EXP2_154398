package com.example.pde_exp2_154398.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dogs")
public class Dog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String name;
    private String imageUrl;
    private String breed;
    private String characteristics;
    
    public Dog() {
    }
    
    public Dog(String name, String imageUrl, String breed, String characteristics) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.breed = breed;
        this.characteristics = characteristics;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getBreed() {
        return breed;
    }
    
    public void setBreed(String breed) {
        this.breed = breed;
    }
    
    public String getCharacteristics() {
        return characteristics;
    }
    
    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }
}

