package com.datm.agota.findyourfriends;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAntal on 17.11.2016.
 */

public class FriendContainer {

    public static Friend[] getFriends() {
        List<Friend> friends = new ArrayList<>();

        Friend friend1 = new Friend(new LatLng(46.76, 23.54), "Bogar");
        Friend friend2 = new Friend(new LatLng(46.76, 23.59), "Bogyo");
        Friend friend3 = new Friend(new LatLng(46.79, 23.54), "Baboca");

        friends.add(friend1);
        friends.add(friend2);
        friends.add(friend3);

        Friend[] friendArray = friends.toArray(new Friend[0]);

        return friendArray;
    }

}