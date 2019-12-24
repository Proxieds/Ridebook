package com.example.ridebook;

import java.io.Serializable;
import java.util.Date;

/**
 * This class contains the required parameters to create a Ride Object and their respective constructors to initialize.
 */
public class Ride implements Serializable {

    //Combined Date and time to datetime, which will be formatted with DateUtils to the respective forms when the app runs.
    private Date dateTime;
    private double distance;
    private double speed;
    private int cadence;
    private String comment;

    public Ride(Date dateTime, double distance, double speed, int cadence, String comment) {
        this.dateTime = dateTime;

        if (distance < 0) {
            throw new IllegalArgumentException("Distance cannot be negative");
        }
        this.distance = distance;
        if (speed < 0) {
            throw new IllegalArgumentException("Speed cannot be negative");
        }
        this.speed = speed;
        if (cadence < 0) {
            throw new IllegalArgumentException("Cadence cannot be negative");
        }
        this.cadence = cadence;
        if (comment.length() > 20) {
            throw new IllegalArgumentException("Comment is limited to 20 characters");
        }
        this.comment = comment;
    }

    //Getters and Setters for Ride

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getCadence() {
        return cadence;
    }

    public void setCadence(int cadence) {
        this.cadence = cadence;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
