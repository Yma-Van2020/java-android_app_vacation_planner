package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VacationDetails extends AppCompatActivity {
    Repository repository;
    int vacationID;
    int numExcursions;
    Vacation currentVacation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);

        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);

        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        excursionAdapter.setExcursions(repository.getAllExcursions());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home){
            this.finish();
            return true;}
       
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