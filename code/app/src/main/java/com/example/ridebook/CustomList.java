package com.example.ridebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * This class formats and provides the custom list display for each ride object.
 */
public class CustomList extends ArrayAdapter<Ride> {

    private ArrayList<Ride> listOfRides;
    private Context context;

    public CustomList(Context context, ArrayList<Ride> listOfRides ) {
        super(context,0,listOfRides);
        this.listOfRides = listOfRides;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content, parent, false);
        }

        Ride ride = listOfRides.get(position);

        TextView date = view.findViewById(R.id.ride_date);
        TextView time = view.findViewById(R.id.ride_time);
        TextView distance = view.findViewById(R.id.ride_distance);

        date.setText(DateUtils.formatDate(ride.getDateTime()));
        time.setText(DateUtils.formatTime(ride.getDateTime()));
        distance.setText(String.valueOf(ride.getDistance()));

        return view;
    }
}
