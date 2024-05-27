package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VacationList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(VacationList.this, VacationDetails.class);
            startActivity(intent);
        });

        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshVacations(recyclerView);
    }

    private void refreshVacations(RecyclerView recyclerView) {
        List<Vacation> allVacations = repository.getAllVacations();
        Log.d("VacationList", "All Vacations: " + allVacations.size());
        for (Vacation vacation : allVacations) {
            Log.d("VacationList", "Vacation: " + vacation.getVacationName());
        }
        VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        vacationAdapter.setVacations(allVacations);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.mysample) {
            repository = new Repository(getApplication());

            Vacation vacation = new Vacation(0, "Bermuda", "Four season hotel", "04/06/24", "04/26/24", 1000.0);
            repository.insert(vacation);
            Vacation vacation2 = new Vacation(0, "London", "Hilton hotel", "06/16/24", "06/26/24", 2000.0);
            repository.insert(vacation2);

            Excursion excursion = new Excursion(0, "high tea", 50, 1);
            repository.insert(excursion);

            refreshVacations(findViewById(R.id.recyclerview));
            return true;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshVacations(findViewById(R.id.recyclerview));
    }
}
