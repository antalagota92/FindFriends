package com.datm.agota.findyourfriends;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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


        // Add a marker in Sydney and move the camera
        LatLng markerToPut = getCoordinatesForAFriendName();
        mMap.addMarker(new MarkerOptions().position(markerToPut).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(markerToPut));
    }

    public LatLng getCoordinatesForAFriendName() {
        Friend[] myFriends = FriendContainer.getFriends();
        LatLng coordinates = new LatLng(0, 0);
        for (int i = 0; i < myFriends.length; i++) {
            if (myFriends[i].getName().contentEquals(name)) {
                coordinates = myFriends[i].getLatLng();
            }
        }
        return coordinates;
    }
}
