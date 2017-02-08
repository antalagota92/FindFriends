package com.datm.agota.findyourfriends.util.directions;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Agota on 2/8/2017.
 */
public class DirectionResult {

    private ArrayList<LatLng> directionPoints;
    private String duration;
    private String toPubName;

    public DirectionResult(ArrayList<LatLng> directionPoints, String duration, String toPubName) {
        this.directionPoints = directionPoints;
        this.duration = duration;
        this.toPubName = toPubName;
    }

    public ArrayList<LatLng> getDirectionPoints() {
        return directionPoints;
    }

    public String getDuration() {
        return duration;
    }

    public String getToPubName() {
        return toPubName;
    }

    @Override
    public String toString() {
        return "DirectionResult{" +
                "directionPoints=" + directionPoints +
                ", duration='" + duration + '\'' +
                ", toPubName='" + toPubName + '\'' +
                '}';
    }
}
