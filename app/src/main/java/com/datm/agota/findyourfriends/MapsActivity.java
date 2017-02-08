package com.datm.agota.findyourfriends;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.datm.agota.findyourfriends.containers.FriendContainer;
import com.datm.agota.findyourfriends.containers.PubContainer;
import com.datm.agota.findyourfriends.domain.Friend;
import com.datm.agota.findyourfriends.domain.Pub;
import com.datm.agota.findyourfriends.util.directions.DirectionResult;
import com.datm.agota.findyourfriends.util.directions.DirectionTask;
import com.datm.agota.findyourfriends.util.directions.GoogleMapsDirection;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.List;

import static com.datm.agota.findyourfriends.R.id.map;
import static com.datm.agota.findyourfriends.containers.FriendContainer.getFriends;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap googleMap;
    private String name;
    private Location myLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.name = extras.getString("name");
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeMap();
    }

    private void initializeMap() {

        if (googleMap != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.clear();
        }

        if (googleMap != null) {

            if (name.equalsIgnoreCase("all")) {
                putAllFriendsOnMap();
            } else {
                LatLng markerToPut = getCoordinatesForAFriendName(name);
                Pub closestPubToFriend = getClosestPubToLatLong(markerToPut);
                this.googleMap.addMarker(new MarkerOptions().position(markerToPut).title(name).snippet("Closest pub is " + closestPubToFriend.getName()));
                drawDirections(closestPubToFriend.getLatLng(), closestPubToFriend.getName());
            }

            putAllPubsOnMap();
        }
    }

    private void drawDirections(final LatLng toPos, final String toName) {
        LatLng myPos = new LatLng(46.756963, 23.596411);

        if (this.myLocation != null) {
            myPos = new LatLng(this.myLocation.getLatitude(), this.myLocation.getLongitude());
        }

        final DirectionTask asyncTask = new DirectionTask(this, myPos, toPos, GoogleMapsDirection.MODE_WALKING, toName);
        asyncTask.execute(new HashMap<String, String>());
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

        final Pub closestPubToAllFriends = getClosestPubToAllFriends();
        for (Friend friend : myFriends) {
            final String friendName = friend.getName();
            googleMap.addMarker(new MarkerOptions().position(friend.getLatLng()).title(friendName)
                    .snippet("Closest pub to all friends is " + closestPubToAllFriends.getName()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(friend.getLatLng()));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        }
        drawDirections(closestPubToAllFriends.getLatLng(), closestPubToAllFriends.getName());
    }

    public void putAllPubsOnMap() {
        List<Pub> myPubs = PubContainer.getPubs();

        for (Pub pub : myPubs) {
            googleMap.addMarker(new MarkerOptions().position(pub.getLatLng()).title(pub.getName()).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
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

    public void handleGetDirectionsResult(final DirectionResult directionResult) {

        final PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.GREEN);
        // move camera to zoom on map
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < directionResult.getDirectionPoints().size(); i++) {
            rectLine.add(directionResult.getDirectionPoints().get(i));
            builder.include(directionResult.getDirectionPoints().get(i));
        }

        googleMap.addMarker(new MarkerOptions().position(directionResult.getDirectionPoints().get(0)).title("Me").icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        googleMap.addPolyline(rectLine);
        // move camera to zoom on map
        LatLngBounds bounds = builder.build();
        // bound of points and offset from edges in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200, 200, 20);
        googleMap.moveCamera(cu);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(directionResult.getDirectionPoints().get(0),
                13));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        initializeMap();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.myLocation = location;
    }
}
