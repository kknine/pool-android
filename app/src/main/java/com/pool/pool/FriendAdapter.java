package com.pool.pool;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pool.pool.model.User;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    ArrayList<User> mFriendList;

    public FriendAdapter(ArrayList<User> friendList) {
        mFriendList = friendList;
    }

    @Override
    public FriendAdapter.FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_friend, parent, false);
        return new FriendAdapter.FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendAdapter.FriendViewHolder holder, int position) {
        if(position < mFriendList.size())
            holder.bind(mFriendList.get(position));

    }

    @Override
    public int getItemCount() {
        if(mFriendList == null)
            return 0;
        return mFriendList.size();
    }

    public void swap(ArrayList<User> list) {
        mFriendList = list;
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {
        View itemView;

        public FriendViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        void bind(User friend) {
            TextView name_e = itemView.findViewById(R.id.lif_name);
            name_e.setText(friend.getName());
        }
    }
}
