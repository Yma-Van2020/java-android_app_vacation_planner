package com.example.d308vacationplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.d308vacationplanner.dao.ExcursionDAO;
import com.example.d308vacationplanner.dao.VacationDAO;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 4, exportSchema = false)
public abstract class TripDatabaseBuilder extends RoomDatabase {

    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();

    private static volatile TripDatabaseBuilder INSTANCE;

    public static TripDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TripDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TripDatabaseBuilder.class, "MyTripDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
