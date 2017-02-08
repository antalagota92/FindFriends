package com.datm.agota.findyourfriends.containers;

import com.datm.agota.findyourfriends.domain.Friend;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAntal on 17.11.2016.
 */

public class FriendContainer {

    public static List<Friend> getFriends() {
        List<Friend> friends = new ArrayList<>();

        Friend friend1 = new Friend(new LatLng(46.76, 23.54), "William");
        Friend friend2 = new Friend(new LatLng(46.76, 23.59), "Kate");
        Friend friend3 = new Friend(new LatLng(46.79, 23.54), "John");

        friends.add(friend1);
        friends.add(friend2);
        friends.add(friend3);

        return friends;
    }

}