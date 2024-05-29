package com.example.d308vacationplanner.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    private EditText excursionNameEditText;
    private EditText excursionPriceEditText;
    private EditText excursionVacationIdEditText;
    private EditText excursionDateEditText;
    private Button saveButton;
    private Button deleteButton;
    private Button notifyButton;
    private Repository repository;
    private Excursion currentExcursion;
    private int excursionID;

    String myFormat = "MM/dd/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

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
        notifyButton = findViewById(R.id.notify_button);

        saveButton.setOnClickListener(v -> saveOrUpdateExcursion());
        notifyButton.setOnClickListener(v -> notifyExcursion());
        deleteButton.setOnClickListener(v -> deleteExcursion());

        // Retrieve excursion data from intent
        String excursionName = getIntent().getStringExtra("name");
        double excursionPrice = getIntent().getDoubleExtra("price", 0.0);
        int excursionVacationId = getIntent().getIntExtra("vacationID", 0);
        String excursionDate = getIntent().getStringExtra("excursionDate");
        excursionID = getIntent().getIntExtra("id", -1);

        // Populate EditText fields with excursion data
        excursionNameEditText.setText(excursionName);
        excursionPriceEditText.setText(String.valueOf(excursionPrice));
        excursionVacationIdEditText.setText(String.valueOf(excursionVacationId));
        excursionDateEditText.setText(excursionDate);

        currentExcursion = new Excursion(excursionID, excursionName, excursionPrice, excursionVacationId, excursionDate);
    }

    private void saveOrUpdateExcursion() {
        String name = excursionNameEditText.getText().toString();
        double price = Double.parseDouble(excursionPriceEditText.getText().toString());
        int vacationID = Integer.parseInt(excursionVacationIdEditText.getText().toString());
        String date = excursionDateEditText.getText().toString();

        // Retrieve the start and end dates of the associated vacation
        String vacationStartDate = repository.getVacationStartDate(vacationID);
        String vacationEndDate = repository.getVacationEndDate(vacationID);

        try {
            // Parse the excursion date
            Date excursionDate = sdf.parse(date);

            // Check if the excursion date is within the vacation range
            if (excursionDate.before(sdf.parse(vacationStartDate)) || excursionDate.after(sdf.parse(vacationEndDate))) {
                Toast.makeText(this, "Excursion date must be within the associated vacation's range", Toast.LENGTH_SHORT).show();
                return; // Exit method if excursion date is not within the vacation range
            }

            if (excursionID != -1) {
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
        } catch (ParseException e) {
            e.printStackTrace();
            excursionDateEditText.setError("Invalid date format (MM/DD/YY)");
        }
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

    private void notifyExcursion() {
        String excursionDateStr = excursionDateEditText.getText().toString();

        // Validate date format
        if (!isValidDate(excursionDateStr)) {
            excursionDateEditText.setError("Invalid date format (MM/DD/YY)");
            return;
        }

        try {
            // Parse the excursion date
            Date excursionDate = sdf.parse(excursionDateStr);

            // Get calendar instance and set it to the excursion date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(excursionDate);

            // Set notification for the excursion date
            setNotificationForDate(calendar, currentExcursion.getExcursionName() + " is today!");
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error parsing excursion date", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidDate(String date) {
        // Define the date format
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        sdf.setLenient(false); // Strict validation

        try {
            // Parse the date string
            Date parsedDate = sdf.parse(date);
            return parsedDate != null;
        } catch (ParseException e) {
            // Date parsing failed
            return false;
        }
    }

    private void setNotificationForDate(Calendar targetCalendar, String notificationMessage) {
        // Create an intent for the broadcast receiver
        Intent intent = new Intent(this, MyReceiver.class);
        intent.putExtra("key", notificationMessage);

        // Create a pending intent to be triggered
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Get the alarm manager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set the notification
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCalendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(this, "Notification set for: " + targetCalendar.getTime().toString(), Toast.LENGTH_SHORT).show();
    }
}
