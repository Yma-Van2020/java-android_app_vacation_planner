package com.example.d308vacationplanner.UI;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308vacationplanner.R;

public class ExcursionDetails extends AppCompatActivity {

    private EditText excursionNameEditText;
    private EditText excursionPriceEditText;
    private EditText excursionVacationIdEditText;
    private EditText excursionDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        // Initialize EditText fields
        excursionNameEditText = findViewById(R.id.excursion_name);
        excursionPriceEditText = findViewById(R.id.excursion_price);
        excursionVacationIdEditText = findViewById(R.id.excursion_vacation_id);
        excursionDateEditText = findViewById(R.id.excursion_date);

        // Retrieve excursion data from intent
        String excursionName = getIntent().getStringExtra("name");
        double excursionPrice = getIntent().getDoubleExtra("price", 0.0);
        int excursionVacationId = getIntent().getIntExtra("vacationID", 0);
        String excursionDate = getIntent().getStringExtra("excursionDate");

        // Populate EditText fields with excursion data
        excursionNameEditText.setText(excursionName);
        excursionPriceEditText.setText(String.valueOf(excursionPrice));
        excursionVacationIdEditText.setText(String.valueOf(excursionVacationId));
        excursionDateEditText.setText(excursionDate);
    }
}