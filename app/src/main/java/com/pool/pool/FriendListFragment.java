package com.pool.pool;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pool.pool.model.Event;
import com.pool.pool.model.User;
import com.pool.pool.server.Server;
import com.pool.pool.server.Utils;

import java.util.ArrayList;

public class FriendListFragment extends Fragment {


    RecyclerView mRecyclerView;
    View mContainer;
    FriendAdapter mAdapter;
    Server mServer;

    public FriendListFragment() {
        // Required empty public constructor
    }


    public static FriendListFragment newInstance() {
        return new FriendListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContainer = inflater.inflate(R.layout.fragment_friend_list, container, false);;
        mRecyclerView = (RecyclerView) mContainer.findViewById(R.id.fli_recycler);
        mServer = new Server(getActivity());
        mServer.getFriends(new Utils.Callback<ArrayList<User>, String>() {
            @Override
            public void onSuccess(ArrayList<User> list) {
                mAdapter = new FriendAdapter(list);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                mRecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onFail(String obj) {
                Toast.makeText(getActivity(),"Could not load your friends",Toast.LENGTH_SHORT).show();
            }
        });
        // Inflate the layout for this fragment
        return mContainer;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
