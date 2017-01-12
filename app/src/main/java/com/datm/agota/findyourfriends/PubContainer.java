package com.datm.agota.findyourfriends;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agota on 1/12/2017.
 */

public class PubContainer {
    public static Pub[] getPubs() {
        List<Pub> pubs = new ArrayList<>();

        Pub pub1 = new Pub(new LatLng(46.78, 23.54), "Klausen");
        Pub pub2 = new Pub(new LatLng(46.76, 23.52), "Irish");
        Pub pub3 = new Pub(new LatLng(46.73, 23.56), "Bulgakov");

        pubs.add(pub1);
        pubs.add(pub2);
        pubs.add(pub3);

        Pub[] pubArray = pubs.toArray(new Pub[0]);

        return pubArray;
    }
}
