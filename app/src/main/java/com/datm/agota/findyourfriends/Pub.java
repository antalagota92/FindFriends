package com.datm.agota.findyourfriends;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Agota on 1/12/2017.
 */

public class Pub {

    private LatLng latLng;
    private String name;

    public Pub(final LatLng latLng, final String name) {
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
