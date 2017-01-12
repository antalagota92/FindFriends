package com.datm.agota.findyourfriends;

import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import static android.R.attr.direction;
import static com.datm.agota.findyourfriends.FriendContainer.getFriends;
import static com.datm.agota.findyourfriends.R.id.map;

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
            LatLng markerToPut = getCoordinatesForAFriendName();
            mMap.addMarker(new MarkerOptions().position(markerToPut).title(name));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(markerToPut));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        }

        putAllPubsOnMap();
    }

    public LatLng getCoordinatesForAFriendName() {
        Friend[] myFriends = getFriends();
        LatLng coordinates = new LatLng(0, 0);
        for (int i = 0; i < myFriends.length; i++) {
            if (myFriends[i].getName().contentEquals(name)) {
                coordinates = myFriends[i].getLatLng();
            }
        }
        return coordinates;
    }

    public void putAllFriendsOnMap() {
        Friend[] myFriends = getFriends();

        for (int i = 0; i < myFriends.length; i++) {
            mMap.addMarker(new MarkerOptions().position(myFriends[i].getLatLng()).title(myFriends[i].getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myFriends[i].getLatLng()));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        }
    }

    public void putAllPubsOnMap() {
        Pub[] myPubs = PubContainer.getPubs();

        for (int i = 0; i < myPubs.length; i++) {
            mMap.addMarker(new MarkerOptions().position(myPubs[i].getLatLng()).title(myPubs[i].getName()).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPubs[i].getLatLng()));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        }
    }
}
