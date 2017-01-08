package com.datm.agota.findyourfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FindLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location);

        final TextView locationField = (TextView) findViewById(R.id.locationField);
        Button refreshLocationButton = (Button) findViewById(R.id.refreshLocationButton);

        refreshLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(FindLocation.this, MapsActivity.class);
                i.putExtra("name", "Bogyo");
                startActivity(i);            }
        });

    }
}