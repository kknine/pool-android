package com.pool.pool;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.pool.pool.model.User;

import java.util.ArrayList;

public class FriendInviteAdapter extends RecyclerView.Adapter<FriendInviteAdapter.FriendInviteViewHolder> {

    ArrayList<User> mFriendList;
    InviteFriendsActivity.OnItemClickListener mListener;

    public FriendInviteAdapter(ArrayList<User> friendList, InviteFriendsActivity.OnItemClickListener listener) {
        mFriendList = friendList;
        mListener = listener;
    }

    @Override
    public FriendInviteAdapter.FriendInviteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_friend_invite, parent, false);
        return new FriendInviteAdapter.FriendInviteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendInviteAdapter.FriendInviteViewHolder holder, int position) {
        if(position < mFriendList.size())
            holder.bind(mFriendList.get(position));

    }

    @Override
    public int getItemCount() {
        if(mFriendList == null)
            return 0;
        return mFriendList.size();
    }

    class FriendInviteViewHolder extends RecyclerView.ViewHolder {
        View itemView;

        public FriendInviteViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        void bind(final User friend) {
            final CheckBox check_cb = itemView.findViewById(R.id.lifi_check);
            check_cb.setText(friend.getName());
            check_cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean state = check_cb.isChecked();
                    mListener.onItemClick(friend,state);
                }
            });
        }
    }
}
