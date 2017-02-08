package com.datm.agota.findyourfriends;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.datm.agota.findyourfriends.containers.FriendContainer;
import com.datm.agota.findyourfriends.containers.PubContainer;
import com.datm.agota.findyourfriends.domain.Friend;
import com.datm.agota.findyourfriends.domain.Pub;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static com.datm.agota.findyourfriends.R.id.map;
import static com.datm.agota.findyourfriends.containers.FriendContainer.getFriends;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.name = extras.getString("name");
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (name.equalsIgnoreCase("all")) {
            putAllFriendsOnMap();
        } else {
            LatLng markerToPut = getCoordinatesForAFriendName(name);
            mMap.addMarker(new MarkerOptions().position(markerToPut).title(name).snippet("Closest pub is " + getClosestPubToLatLong(markerToPut).getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(markerToPut));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        }

        putAllPubsOnMap();
    }


    public LatLng getCoordinatesForAFriendName(final String theName) {
        List<Friend> myFriends = getFriends();
        LatLng coordinates = new LatLng(0, 0);
        for (Friend friend : myFriends) {
            if (friend.getName().contentEquals(theName)) {
                coordinates = friend.getLatLng();
            }
        }
        return coordinates;
    }

    public void putAllFriendsOnMap() {
        List<Friend> myFriends = getFriends();

        for (Friend friend : myFriends) {
            final String friendName = friend.getName();
            mMap.addMarker(new MarkerOptions().position(friend.getLatLng()).title(friendName)
                    .snippet("Closest pub to all friends is " + getClosestPubToAllFriends().getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(friend.getLatLng()));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        }
    }

    public void putAllPubsOnMap() {
        List<Pub> myPubs = PubContainer.getPubs();

        for (Pub pub : myPubs) {
            mMap.addMarker(new MarkerOptions().position(pub.getLatLng()).title(pub.getName()).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pub.getLatLng()));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        }
    }

    private Pub getClosestPubToLatLong(LatLng latLng) {
        final Location friendLocation = getLocation(latLng, name);
        final List<Pub> allPubs = PubContainer.getPubs();
        Pub closestPub = null;
        float minDistance = Float.MAX_VALUE;
        for (Pub pub : allPubs) {
            Location pubLocation = getLocation(pub.getLatLng(), pub.getName());
            float distance = friendLocation.distanceTo(pubLocation);
            if (distance < minDistance) {
                closestPub = pub;
                minDistance = distance;
            }
        }
        return closestPub;
    }

    private Pub getClosestPubToAllFriends() {
        final List<Friend> allFriends = FriendContainer.getFriends();
        final List<Pub> allPubs = PubContainer.getPubs();
        Pub closestPub = null;
        float minTotalDistance = Float.MAX_VALUE;
        for (Pub pub : allPubs) {
            float totalDistanceToPub = 0;
            for (Friend friend : allFriends) {
                final Location friendLocation = getLocation(friend.getLatLng(), friend.getName());
                Location pubLocation = getLocation(pub.getLatLng(), pub.getName());
                totalDistanceToPub += friendLocation.distanceTo(pubLocation);
            }
            if (totalDistanceToPub < minTotalDistance) {
                closestPub = pub;
                minTotalDistance = totalDistanceToPub;
            }
        }
        return closestPub;
    }

    private Location getLocation(LatLng latLng, String name) {
        final Location location = new Location(name);
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        return location;
    }
}
