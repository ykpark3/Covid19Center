package org.androidtown.covid19center.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Clinic.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    //public abstract ClinicDao clinicDao();

    private static AppDatabase database;

    private static final String DATABASE_NAME = "clinic";

    public synchronized static AppDatabase getInstance(Context context) {
        // Check condition
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    // Create Dao
    public abstract ClinicDao clinicDao();
}