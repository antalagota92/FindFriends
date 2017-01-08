package com.datm.agota.findyourfriends;

/**
 * Created by AAntal on 17.11.2016.
 */
        import com.google.android.gms.maps.model.LatLng;

public class Friend {

    private LatLng latLng;
    private String name;

    public Friend(final LatLng latLng, final String name) {
        this.latLng = latLng;
        this.name = name;
    }

    public LatLng getLatLng() {

        return latLng;
    }

    public String getName() {

        return name;
    }
}