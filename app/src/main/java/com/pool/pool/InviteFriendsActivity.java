package com.pool.pool;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pool.pool.model.User;
import com.pool.pool.server.Server;
import com.pool.pool.server.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class InviteFriendsActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FriendInviteAdapter mAdapter;
    Server mServer;
    HashMap<Integer,Boolean> map;
    int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        map = new HashMap<>();
        eventId = getIntent().getIntExtra(Constants.EXTRA_EVENT_ID,1);
        mServer = new Server(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.ifr_recycler);
        mServer.getUninvitedFriends(eventId, new Utils.Callback<ArrayList<User>, String>() {
            @Override
            public void onSuccess(ArrayList<User> list) {
                mAdapter = new FriendInviteAdapter(list, new OnItemClickListener() {
                    @Override
                    public void onItemClick(User friend, boolean state) {
                        map.put(friend.getId(),state);
                    }
                });
                mRecyclerView.setLayoutManager(new LinearLayoutManager((InviteFriendsActivity.this)));
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(InviteFriendsActivity.this, DividerItemDecoration.VERTICAL));
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFail(String obj) {

            }
        });

    }

    public void accept(View v) {
        Set<Integer> set = map.keySet();
        for(int i : set) {
            if(map.get(i).booleanValue()) {
                mServer.inviteFriend(eventId, i, new Utils.Callback<String, String>() {
                    @Override
                    public void onSuccess(String obj) {

                    }

                    @Override
                    public void onFail(String obj) {

                    }
                });
            }
        }
        finish();
    }

    public interface OnItemClickListener {
        void onItemClick(User friend, boolean state);
    }
}
