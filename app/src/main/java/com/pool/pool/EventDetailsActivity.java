package com.pool.pool;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pool.pool.model.User;
import com.pool.pool.server.Server;
import com.pool.pool.server.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EventDetailsActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FriendAdapter mAdapter;
    Server mServer;
    int eventId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ede_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }
        eventId = getIntent().getIntExtra(Constants.EXTRA_EVENT_ID,0);
        String eventName = getIntent().getStringExtra(Constants.EXTRA_EVENT_NAME);
        TextView eventName_tv = (TextView) findViewById(R.id.ede_name);
        eventName_tv.setText(eventName);
        int eventOwner = getIntent().getIntExtra(Constants.EXTRA_EVENT_OWNER,0);
        if(eventOwner == Auth.getUser(this).getId()) {
            Button inviteFriends_b = (Button) findViewById(R.id.ede_invite);
            inviteFriends_b.setEnabled(true);
            inviteFriends_b.setVisibility(View.VISIBLE);
        } else {
            Button inviteFriends_b = (Button) findViewById(R.id.ede_invite);
            inviteFriends_b.setEnabled(false);
            inviteFriends_b.setVisibility(View.GONE);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.ede_recycler);
        mServer = new Server(this);

    }

    protected void onResume() {
        super.onResume();
        mServer.getInvitedGuests(eventId, new Utils.Callback<ArrayList<User>, String>() {
            @Override
            public void onSuccess(ArrayList<User> guests) {
                if(mAdapter==null) {
                    mAdapter = new FriendAdapter(guests);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager((EventDetailsActivity.this)));
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(EventDetailsActivity.this, DividerItemDecoration.VERTICAL));
                    mRecyclerView.setAdapter(mAdapter);
                    TextView guestsNumber_tv = (TextView) findViewById(R.id.ede_guests_nuber);
                    guestsNumber_tv.setText(Integer.toString(guests.size()));
                } else {
                    mAdapter.swap(guests);
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFail(String obj) {

            }
        });

    }

    public void inviteFriends(View v) {
        Intent i = new Intent(this,InviteFriendsActivity.class);
        i.putExtra(Constants.EXTRA_EVENT_ID,eventId);
        startActivity(i);
    }

    public void showDirections(View v) {
        Intent i = new Intent(this,MapsActivity.class);
        i.putExtra(Constants.EXTRA_EVENT_ID,eventId);
        startActivity(i);
    }



}
