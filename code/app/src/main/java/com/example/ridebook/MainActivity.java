package com.example.ridebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for handling the functions that are used to add/edit/delete rides, also displays the list of rides, and total distance.
 */
public class MainActivity extends AppCompatActivity {
    ListView rideList;
    ArrayAdapter<com.example.ridebook.Ride> rideAdapter;
    ArrayList<com.example.ridebook.Ride> rideDataList;
    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rideList = findViewById(R.id.rideList);
        button = findViewById(R.id.addRideButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddRide();
            }
        });

        rideDataList = new ArrayList<>();
        rideAdapter = new CustomList(this, rideDataList);

        rideList.setAdapter(rideAdapter);
        rideList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int currentSelected = position;
                Ride ride = rideDataList.get(currentSelected);
                Intent intent2 = new Intent(getApplicationContext(), UpdateOrDeleteRide.class);
                intent2.putExtra("RIDE", ride);
                intent2.putExtra("POSITION", currentSelected);
                startActivityForResult(intent2, 2);
            }
        });

        updateDistance();

    }

    /**
     * Get the total distance.
     * @param listOfRides
     * @return
     */
    public double sumDistance(List<Ride> listOfRides) {
        double sum = 0;
        for (int i = 0; i < listOfRides.size(); i++) {
            sum += listOfRides.get(i).getDistance();
        }
        return sum;
    }

    /**
     * Update the total distance when any changes are made or rides are added/deleted.
     */
    public void updateDistance() {
        TextView totalDistance = findViewById(R.id.totalDistance);
        totalDistance.setText(String.valueOf(sumDistance(rideDataList)));
    }

    /**
     * Opens add ride intent.
     */
    public void openAddRide() {
        Intent intent = new Intent(this, com.example.ridebook.AddRide.class);
        startActivityForResult(intent, 1);
    }

    /**
     * First request code is for adding rides, will take the returned string ride, cast it and add it to the ride list.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Serializable strNewRide = data.getSerializableExtra("newRide");
                rideAdapter.add((Ride) strNewRide);
                updateDistance();
            }
        }

        //For the update/edit intent page.
        if (requestCode == 2) {
            int DELETED_RIDE = 2;

            //Result code will be 2 when delete button is selected from inside the update/edit intent, remove the ride indexed from the returned position.
            if (resultCode == DELETED_RIDE) {
                Serializable strPosition = data.getSerializableExtra("position");
                rideAdapter.remove(rideAdapter.getItem((int) strPosition));
                updateDistance();
            }

            //Result code will be RESULT_OK when user selects save ride info, it will take the updated ride and replace the ride indexed from returned position.
            //Will also notify the adapter of the changes.
            else if (resultCode == RESULT_OK) {
                Serializable strUpdatedRide = data.getSerializableExtra("updatedRide");
                Serializable strPosition = data.getSerializableExtra("position");
                rideDataList.set((int) strPosition, (Ride) strUpdatedRide);
                rideAdapter.notifyDataSetChanged();
                updateDistance();
            }

        }
    }
}