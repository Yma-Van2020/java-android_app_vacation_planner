package com.example.d308vacationplanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity(tableName = "excursions")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private String excursionName;
    private double price;
    private int vacationID;
    private String excursionDate;

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public Excursion(int excursionID, String excursionName, double price, int vacationID, String excursionDate) {
        this.excursionID = excursionID;
        this.excursionName = excursionName;
        this.price = price;
        this.vacationID = vacationID;
        this.excursionDate = excursionDate;
    }

    public String getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }
}
