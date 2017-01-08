package com.datm.agota.findyourfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.datm.agota.findyourfriends.R.layout.activity_find_location;

public class FindLocation extends AppCompatActivity {

    final Friend[] myFriends = FriendContainer.getFriends();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_find_location);

        Button refreshLocationButton = (Button) findViewById(R.id.refreshLocationButton);

        setButtonsForMyFriends();

        refreshLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(FindLocation.this, MapsActivity.class);
                i.putExtra("name", "Bogyo");
                startActivity(i);
            }
        });

    }

    protected void setButtonsForMyFriends() {
        LinearLayout layout = (LinearLayout)findViewById(R.id.activity_first_app);
        layout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < myFriends.length; i++) {
            Button btn = new Button(this);
            btn.setText(myFriends[i].getName());
            btn.setHeight(150);
            btn.setWidth(150);
            layout.addView(btn);
        }
    }

}