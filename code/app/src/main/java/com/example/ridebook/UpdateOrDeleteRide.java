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

import static android.view.View.OnClickListener;

/**
 * This class functions similarly to the add ride class, however, there is a delete button and saving now edits the current ride info.
 */
public class UpdateOrDeleteRide extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_or_delete_ride);

        //Get the ride object passed from the main activity.
        Ride ride = (Ride) getIntent().getSerializableExtra("RIDE");

        //Format the current info in the text boxes with getters from the ride object.
        TextView dateView = findViewById(R.id.startDate);
        TextView timeView = findViewById(R.id.startTime);
        EditText distance = findViewById(R.id.distance);
        EditText cadence = findViewById(R.id.cadenceInRevolutions);
        EditText speed = findViewById(R.id.averageSpeed);
        EditText comment = findViewById(R.id.commentBox);

        timeView.setText(DateUtils.formatTime(ride.getDateTime()));
        dateView.setText(DateUtils.formatDate(ride.getDateTime()));
        distance.setText(String.valueOf(ride.getDistance()));
        cadence.setText(String.valueOf(ride.getCadence()));
        speed.setText(String.valueOf(ride.getSpeed()));
        comment.setText(ride.getComment());


        Button dateButton = findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new com.example.ridebook.DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Button timeButton = findViewById(R.id.timeButton);
        timeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new com.example.ridebook.TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Button saveInfo = findViewById(R.id.addRideBtn);

        //When save info is selected, it will create a new ride with the new parameters and return it along with the position, which will modify the current
        //selected ride object to be assigned to the new object created here and indexed with the current position.
        saveInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText distance = findViewById(R.id.distance);
                EditText cadence = findViewById(R.id.cadenceInRevolutions);
                EditText speed = findViewById(R.id.averageSpeed);
                EditText comment = findViewById(R.id.commentBox);
                Intent intent = new Intent();
                int position = (int)getIntent().getSerializableExtra("POSITION");

                TextView dateView = findViewById(R.id.startDate);
                TextView timeView = findViewById(R.id.startTime);

                String date = dateView.getText().toString();
                String time = timeView.getText().toString();

                try {
                    intent.putExtra("updatedRide",
                            new Ride(
                                    DateUtils.parse(date + " " + time),
                                    Double.parseDouble(distance.getText().toString()),
                                    Double.parseDouble(speed.getText().toString()),
                                    Integer.parseInt(cadence.getText().toString()),
                                    comment.getText().toString()
                            )
                    );
                    intent.putExtra("position",position);
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

        Button deleteInfo = findViewById(R.id.deleteButton);
        // When the delete button is selected, it will return the position of the Ride in the list to be removed in the main activity.
        deleteInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)getIntent().getSerializableExtra("POSITION");
                Intent intent = new Intent();
                intent.putExtra("position",position);
                setResult(2,intent);
                finish();
            }
        });
    }


    /**
     * When user presses the back button on android layout bar while in the save/edit page.
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }

    /**
     * Formats time and sets the text view to the new time string.
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView startTime = findViewById(R.id.startTime);
        String newHour = String.format(Locale.US,"%02d", hourOfDay);
        String newMinute = String.format(Locale.US, "%02d", minute);
        startTime.setText(String.format("%s:%s", newHour, newMinute));
    }
    /**
     *Formats date and sets the text view to the new date string.
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
