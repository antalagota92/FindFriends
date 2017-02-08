package com.datm.agota.findyourfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.datm.agota.findyourfriends.containers.FriendContainer;
import com.datm.agota.findyourfriends.domain.Friend;

import java.util.List;

import static com.datm.agota.findyourfriends.R.layout.activity_find_location;

public class FindLocation extends AppCompatActivity {

    final List<Friend> myFriends = FriendContainer.getFriends();

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

        for (final Friend friend : myFriends) {
            Button btn = new Button(this);

            final String name = friend.getName();

            btn.setText(name);
            btn.setHeight(150);
            btn.setWidth(150);

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(FindLocation.this, MapsActivity.class);
                    i.putExtra("name", friend.getName());
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

        btn.setText("Everyone");
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