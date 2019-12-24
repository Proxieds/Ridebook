package com.example.ridebook;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

/**
 * This intent is used to add a new ride to the ride list, it takes in certain parameters and upon clicking the add ride, will create a new Ride object
 * and return it to the main activity, otherwise, it will catch any exceptions and notify the user.
 */
public class AddRide extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride);

        Button dateButton = findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new com.example.ridebook.DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Button timeButton = findViewById(R.id.timeButton);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new com.example.ridebook.TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Button addRide = findViewById(R.id.addRideBtn);

        //Upon clicking the add ride button, there will be an intent to another activity to fill out the required information.
        addRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText distance = findViewById(R.id.distance);
                EditText cadence = findViewById(R.id.cadenceInRevolutions);
                EditText speed = findViewById(R.id.averageSpeed);
                EditText comment = findViewById(R.id.commentBox);

                Intent intent = new Intent();

                TextView dateView = findViewById(R.id.startDate);
                TextView timeView = findViewById(R.id.startTime);

                String date = dateView.getText().toString();
                String time = timeView.getText().toString();

                try {
                    intent.putExtra("newRide",
                        new Ride(
                            DateUtils.parse(date + " " + time),
                            Double.parseDouble(distance.getText().toString()),
                            Double.parseDouble(speed.getText().toString()),
                            Integer.parseInt(cadence.getText().toString()),
                            comment.getText().toString()
                        )
                    );
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (ParseException e) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Invalid info",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    /**
     * Formats and sets the new time string.
     * @param view
     * @param hourOfDay
     * @param minute
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView startTime = findViewById(R.id.startTime);
        String newHour = String.format(Locale.US,"%02d", hourOfDay);
        String newMinute = String.format(Locale.US, "%02d", minute);
        startTime.setText(String.format("%s:%s", newHour, newMinute));
    }

    /**
     * Formats and sets the new date string.
     * @param view
     * @param year
     * @param month
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateUtils.formatDate(c.getTime());
        TextView textView = findViewById(R.id.startDate);
        textView.setText(currentDateString);
    }

}
