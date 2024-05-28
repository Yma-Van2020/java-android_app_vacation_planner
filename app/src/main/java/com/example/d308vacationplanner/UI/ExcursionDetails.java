package com.example.d308vacationplanner.UI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;

public class ExcursionDetails extends AppCompatActivity {

    private EditText excursionNameEditText;
    private EditText excursionPriceEditText;
    private EditText excursionVacationIdEditText;
    private EditText excursionDateEditText;
    private Button saveButton;
    private Button deleteButton;

    private Repository repository;
    private Excursion currentExcursion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        repository = new Repository(getApplication());

        excursionNameEditText = findViewById(R.id.excursion_name);
        excursionPriceEditText = findViewById(R.id.excursion_price);
        excursionVacationIdEditText = findViewById(R.id.excursion_vacation_id);
        excursionDateEditText = findViewById(R.id.excursion_date);
        saveButton = findViewById(R.id.save_button);
        deleteButton = findViewById(R.id.delete_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrUpdateExcursion();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteExcursion();
            }
        });

        if (getIntent().hasExtra("excursionID")) {
            // Retrieve excursion data from intent
            String excursionName = getIntent().getStringExtra("name");
            double excursionPrice = getIntent().getDoubleExtra("price", 0.0);
            int excursionVacationId = getIntent().getIntExtra("vacationID", 0);
            String excursionDate = getIntent().getStringExtra("excursionDate");
            int excursionID = getIntent().getIntExtra("id", 0);

            // Populate EditText fields with excursion data
            excursionNameEditText.setText(excursionName);
            excursionPriceEditText.setText(String.valueOf(excursionPrice));
            excursionVacationIdEditText.setText(String.valueOf(excursionVacationId));
            excursionDateEditText.setText(excursionDate);

            currentExcursion = new Excursion(excursionID, excursionName, excursionPrice, excursionVacationId, excursionDate);
        }
    }

    private void saveOrUpdateExcursion() {
        String name = excursionNameEditText.getText().toString();
        double price = Double.parseDouble(excursionPriceEditText.getText().toString());
        int vacationID = Integer.parseInt(excursionVacationIdEditText.getText().toString());
        String date = excursionDateEditText.getText().toString();

        if (currentExcursion != null) {
            // Update existing excursion
            currentExcursion.setExcursionName(name);
            currentExcursion.setPrice(price);
            currentExcursion.setVacationID(vacationID);
            currentExcursion.setExcursionDate(date);

            repository.update(currentExcursion);
            Toast.makeText(this, "Excursion updated", Toast.LENGTH_SHORT).show();
        } else {
            // Add new excursion
            Excursion excursion = new Excursion(0, name, price, vacationID, date);
            repository.insert(excursion);
            Toast.makeText(this, "Excursion added", Toast.LENGTH_SHORT).show();
        }

        finish(); // Finish the activity after saving or updating
    }

    private void deleteExcursion() {
        if (currentExcursion != null) {
            repository.delete(currentExcursion);
            Toast.makeText(this, "Excursion deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No excursion to delete", Toast.LENGTH_SHORT).show();
        }

        finish(); // Finish the activity after deleting
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Finish the activity if back button is pressed
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
