package com.example.d308vacationplanner.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;
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
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarEnd = Calendar.getInstance();


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

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }
        };


        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelEnd();
        }
    };
        editVacationStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editVacationStart.getText().toString();
                if(info.equals(""))info = "05/01/24";
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editVacationEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editVacationEnd.getText().toString();
                if(info.equals(""))info = "05/03/24";
                try{
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

    private boolean validateVacationDetails() {
        // Validate start date
        if (!isValidDate(editVacationStart.getText().toString())) {
            editVacationStart.setError("Invalid date format (MM/DD/YY)");
            return false;
        }

        // Validate end date
        if (!isValidDate(editVacationEnd.getText().toString())) {
            editVacationEnd.setError("Invalid date format (MM/DD/YY)");
            return false;
        }

        // Check if end date is after start date
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        sdf.setLenient(false); // Strict validation
        try {
            Date startDate = sdf.parse(editVacationStart.getText().toString());
            Date endDate = sdf.parse(editVacationEnd.getText().toString());
            if (endDate.before(startDate)) {
                // End date is before start date
                editVacationEnd.setError("End date must be after start date");
                return false;
            }
        } catch (ParseException e) {
            // Date parsing failed
            e.printStackTrace();
            return false;
        }

        // All validation passed
        return true;
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editVacationStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editVacationEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }

    private void setNotificationForDate(String dateString, String notificationMessage) {
        String myFormat = "MM/dd/yy"; // The date format you need
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);

        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            Calendar targetCalendar = Calendar.getInstance();
            targetCalendar.setTime(date);
            targetCalendar.set(Calendar.HOUR_OF_DAY, 8);
            targetCalendar.set(Calendar.MINUTE, 0);
            targetCalendar.set(Calendar.SECOND, 0);

            // Check if the target date is today's date
            if (targetCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR) &&
                    targetCalendar.get(Calendar.DAY_OF_YEAR) == todayCalendar.get(Calendar.DAY_OF_YEAR)) {
                // Set notification only if it's today's date
                Long trigger = targetCalendar.getTimeInMillis();
                Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
                intent.putExtra("key", notificationMessage);
                PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                Toast.makeText(this, "Notification set for: " + targetCalendar.getTime().toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("VacationDetails", "Failed to parse date.");
        }
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
            // Validate vacation details before saving
            if (!validateVacationDetails()) {
                // Validation failed, return without saving
                return true;
            }

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

        if (item.getItemId() == R.id.vacationdelete) {
            // Check if the vacation ID is valid
            if (vacationID != -1) {
                // Find the current vacation
                for (Vacation vacation : repository.getAllVacations()) {
                    if (vacation.getVacationID() == vacationID) {
                        currentVacation = vacation;
                        break; // Break the loop once the vacation is found
                    }
                }

                if (currentVacation != null) {
                    // Check if there are any excursions associated with the vacation
                    numExcursions = repository.getAllExcursions().stream()
                            .filter(excursion -> excursion.getVacationID() == vacationID)
                            .collect(Collectors.toList())
                            .size();

                    if (numExcursions == 0) {
                        // If there are no excursions, delete the vacation
                        try {
                            repository.delete(currentVacation);
                            Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
                            finish(); // Finish the activity after successful deletion
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("VacationDetails", "Error deleting vacation", e);
                            Toast.makeText(VacationDetails.this, "Error deleting vacation", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // If there are excursions associated with the vacation, display a message
                        Toast.makeText(VacationDetails.this, "Can't delete a vacation with excursions", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // If the current vacation is not found, display an error message
                    Toast.makeText(VacationDetails.this, "Error: Vacation not found", Toast.LENGTH_LONG).show();
                }
            } else {
                // If the vacation ID is invalid, display an error message
                Toast.makeText(VacationDetails.this, "Error: Invalid vacation ID", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        if (item.getItemId() == R.id.vacationnotify) {
            setNotificationForDate(editVacationStart.getText().toString(), title + " is starting today!");
            setNotificationForDate(editVacationEnd.getText().toString(), title + " is ending today!");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}