package com.example.pde_exp2_154398.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase; // Import SupportSQLiteDatabase

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Dog.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DogDao dogDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "dogs_database")
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(SupportSQLiteDatabase db) { // Use the imported class
                                    super.onCreate(db);
                                    databaseWriteExecutor.execute(() -> {
                                        List<Dog> initialDogs = Arrays.asList(
                                                new Dog("Brutus",
                                                        "https://images.dog.ceo/breeds/pitbull/dog-3981033_1280.jpg",
                                                        "Pitbull",
                                                        "Atletismo y fuerza; Inteligencia; Compañerismo"),
                                                new Dog("Goliat",
                                                        "https://images.dog.ceo/breeds/dane-great/n02109047_5618.jpg",
                                                        "Gran Danés",
                                                        "Obediencia; Compañerismo; Habilidades sociales"),
                                                new Dog("Ryuu",
                                                        "https://images.dog.ceo/breeds/akita/Japaneseakita.jpg",
                                                        "Akita",
                                                        "Protección y vigilancia; Resistencia y fuerza; Inteligencia"),
                                                new Dog("Coco",
                                                        "https://images.dog.ceo/breeds/chihuahua/n02085620_9654.jpg",
                                                        "Chihuahua",
                                                        "Deber de vigilancia; Compañerismo; Adaptabilidad"),
                                                new Dog("Luna",
                                                        "https://images.dog.ceo/breeds/labrador/n02099712_8719.jpg",
                                                        "Labrador",
                                                        "Trabajo de olfato; Obediencia y agilidad; Caza y trabajo de campo"),
                                                new Dog("Zeus",
                                                        "https://images.dog.ceo/breeds/rottweiler/n02106550_10048.jpg",
                                                        "Rottweiler",
                                                        "Guardia y protección; Fuerza y potencia; Inteligencia")
                                        );
                                        INSTANCE.dogDao().insertAll(initialDogs);
                                    });
                                }
                            })
                            .build(); // Correct position for .build()
                }
            }
        }
        return INSTANCE;
    }
}
