package com.example.d308vacationplanner.database;

import android.app.Application;

import com.example.d308vacationplanner.dao.ExcursionDAO;
import com.example.d308vacationplanner.dao.VacationDAO;
import com.example.d308vacationplanner.database.TripDatabaseBuilder;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private ExcursionDAO mExcursionDAO;
    private VacationDAO mVacationDAO;
    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        TripDatabaseBuilder db=TripDatabaseBuilder.getDatabase(application);
        mExcursionDAO=db.excursionDAO();
        mVacationDAO=db.vacationDAO();
    }

    // Method to get start date of the vacation by ID
    public String getVacationStartDate(int vacationID) {
        final String[] startDate = new String[1]; // Array to hold the start date

        // Execute the query in a background thread
        databaseExecutor.execute(() -> {
            startDate[0] = mVacationDAO.getStartDateById(vacationID); // Assuming getStartDateById exists in your VacationDAO
        });

        // Wait for the query to finish
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return startDate[0]; // Return the start date
    }

    // Method to get end date of the vacation by ID
    public String getVacationEndDate(int vacationID) {
        final String[] endDate = new String[1]; // Array to hold the end date

        // Execute the query in a background thread
        databaseExecutor.execute(() -> {
            endDate[0] = mVacationDAO.getEndDateById(vacationID); // Assuming getEndDateById exists in your VacationDAO
        });

        // Wait for the query to finish
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return endDate[0]; // Return the end date
    }

    // vacations
    public List<Vacation>getAllVacations(){
        databaseExecutor.execute(()->{
            mAllVacations=mVacationDAO.getAllVacations();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllVacations;
    }
    public void insert(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.insert(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.update(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.delete(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // excursions --------------------------------------------------------------------
    public List<Excursion>getAllExcursions(){
        databaseExecutor.execute(()->{
            mAllExcursions=mExcursionDAO.getAllExcursions();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }
    public void insert(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.insert(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.update(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.delete(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
