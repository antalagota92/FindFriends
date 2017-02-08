package com.datm.agota.findyourfriends.domain;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Agota on 1/12/2017.
 */

public class Pub implements Serializable{

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pub pub = (Pub) o;

        if (latLng != null ? !latLng.equals(pub.latLng) : pub.latLng != null) return false;
        return name != null ? name.equals(pub.name) : pub.name == null;

    }

    @Override
    public int hashCode() {
        int result = latLng != null ? latLng.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
