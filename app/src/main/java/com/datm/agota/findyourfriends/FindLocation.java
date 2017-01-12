package com.datm.agota.findyourfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static android.R.attr.name;
import static com.datm.agota.findyourfriends.R.id.refreshLocationButton;
import static com.datm.agota.findyourfriends.R.layout.activity_find_location;

public class FindLocation extends AppCompatActivity {

    final Friend[] myFriends = FriendContainer.getFriends();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_find_location);

        setButtonsForMyFriends();
        setAllButtonForAllFriends();
    }

    protected void setButtonsForMyFriends() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_first_app);
        layout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < myFriends.length; i++) {
            Button btn = new Button(this);

            final String name = myFriends[i].getName();

            btn.setText(name);
            btn.setHeight(150);
            btn.setWidth(150);

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(FindLocation.this, MapsActivity.class);
                    i.putExtra("name", name);
                    startActivity(i);
                }
            });

            layout.addView(btn);
        }
    }

    protected void setAllButtonForAllFriends() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_first_app);
        layout.setOrientation(LinearLayout.VERTICAL);
            Button btn = new Button(this);

            btn.setText("All");
            btn.setHeight(150);
            btn.setWidth(150);

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(FindLocation.this, MapsActivity.class);
                    i.putExtra("name", "all");
                    startActivity(i);
                }
            });

            layout.addView(btn);
    }

}