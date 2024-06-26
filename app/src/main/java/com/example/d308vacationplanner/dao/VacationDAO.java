package com.example.d308vacationplanner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308vacationplanner.entities.Vacation;

import java.util.Date;
import java.util.List;

@Dao
public interface VacationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM vacations ORDER BY vacationID ASC")
    List<Vacation> getAllVacations();

    @Query("SELECT COUNT(*) FROM excursions WHERE vacationID = :vacationID")
    int getExcursionCountForVacation(int vacationID);

    @Query("SELECT startDate FROM vacations WHERE vacationID = :vacationID")
    String getStartDateById(int vacationID);

    @Query("SELECT endDate FROM vacations WHERE vacationID = :vacationID")
    String getEndDateById(int vacationID);
}
