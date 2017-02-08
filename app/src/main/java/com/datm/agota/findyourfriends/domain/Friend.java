package com.datm.agota.findyourfriends.domain;

/**
 * Created by AAntal on 17.11.2016.
 */

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Friend implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Friend friend = (Friend) o;

        if (latLng != null ? !latLng.equals(friend.latLng) : friend.latLng != null) return false;
        return name != null ? name.equals(friend.name) : friend.name == null;

    }

    @Override
    public int hashCode() {
        int result = latLng != null ? latLng.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}