package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.stream.Collectors;

public class VacationDetails extends AppCompatActivity {
    Repository repository;
    int vacationID;
    int numExcursions;
    Vacation currentVacation;
    EditText editTitle;
    EditText editPrice;
    EditText editHotel;
    EditText editVacationStart;
    EditText editVacationEnd;
    String title;
    String hotel;
    double price;
    String vacationStart;
    String vacationEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);

        editTitle = findViewById(R.id.vacationname);
        editHotel = findViewById(R.id.vacationhotel);
        editPrice = findViewById(R.id.vacationprice);
        editVacationStart = findViewById(R.id.vacationstart);
        editVacationEnd = findViewById(R.id.vacationend);

        title = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price", 0);
        hotel = getIntent().getStringExtra("hotel");
        vacationStart = getIntent().getStringExtra("start");
        vacationEnd = getIntent().getStringExtra("end");

        editTitle.setText(title);
        editHotel.setText(hotel);
        editPrice.setText(Double.toString(price));
        editVacationStart.setText(vacationStart);
        editVacationEnd.setText(vacationEnd);

        vacationID = getIntent().getIntExtra("id", -1);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);

        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);

        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Filter excursions by vacationID
        List<Excursion> excursionsForVacation = repository.getAllExcursions().stream()
                .filter(excursion -> excursion.getVacationID() == vacationID)
                .collect(Collectors.toList());
        excursionAdapter.setExcursions(excursionsForVacation);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home){
            this.finish();
            return true;}

        if(item.getItemId()== R.id.vacationsave){
            Vacation vacation;
            if (vacationID == -1) {
                if (repository.getAllVacations().isEmpty()) vacationID = 1;
                else vacationID = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationID() + 1;

                vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), editVacationStart.getText().toString(), editVacationEnd.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
                repository.insert(vacation);
                Toast.makeText(this, "Vacation inserted successfully", Toast.LENGTH_SHORT).show();
                Log.d("VacationDetails", "Vacation inserted: " + vacationID);
                this.finish();
            } else {
                try {
                    vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), editVacationStart.getText().toString(), editVacationEnd.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
                    repository.update(vacation);
                    Toast.makeText(this, "Vacation updated successfully", Toast.LENGTH_SHORT).show();
                    Log.d("VacationDetails", "Vacation updated: " + vacationID);
                    this.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error updating vacation", Toast.LENGTH_SHORT).show();
                    Log.e("VacationDetails", "Error updating vacation", e);
                }
            }
            return true;
        }

        
        if(item.getItemId()== R.id.vacationdelete) {
            for (Vacation vacation : repository.getAllVacations()) {
                vacationID = getIntent().getIntExtra("id", -1);

                if (vacation.getVacationID() == vacationID) {
                    currentVacation = vacation;
                }

                numExcursions = 0;
                for (Excursion excursion : repository.getAllExcursions()) {
                    if (excursion.getVacationID() == vacationID) ++numExcursions;
                }

                if (numExcursions == 0) {
                    repository.delete(currentVacation);
                    Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(VacationDetails.this, "Can't delete a vacation with excursions", Toast.LENGTH_LONG).show();
                }
                return true;

            }
        }
        return super.onOptionsItemSelected(item);
    }

}