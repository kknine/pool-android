package com.pool.pool;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pool.pool.model.Event;
import com.pool.pool.server.Server;
import com.pool.pool.server.Utils;

import java.util.ArrayList;

public class EventListFragment extends Fragment {


   RecyclerView mRecyclerView;
   View mContainer;
   EventAdapter mAdapter;
   Server mServer;

    public EventListFragment() {
        // Required empty public constructor
    }


    public static EventListFragment newInstance() {
        return new EventListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContainer = inflater.inflate(R.layout.fragment_event_list, container, false);;
        mRecyclerView = (RecyclerView) mContainer.findViewById(R.id.eli_recycler);
        FloatingActionButton fab = (FloatingActionButton) mContainer.findViewById(R.id.eli_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),CreateEventActivity.class);
                startActivity(i);
            }
        });
        mServer = new Server(getActivity());
        mServer.getAvailableEvents(new Utils.Callback<ArrayList<Event>, String>() {
            @Override
            public void onSuccess(ArrayList<Event> list) {
                mAdapter = new EventAdapter(list, new OnItemClickListner() {
                    @Override
                    public void onItemClick(Event event) {
                        Intent i = new Intent(getActivity(),EventDetailsActivity.class);
                        i.putExtra(Constants.EXTRA_EVENT_ID,event.getId());
                        i.putExtra(Constants.EXTRA_EVENT_NAME,event.getName());
                        i.putExtra(Constants.EXTRA_EVENT_OWNER,event.getOwnerId());
                        startActivity(i);
                    }
                });
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                mRecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onFail(String obj) {
                Toast.makeText(getActivity(),"Could not load events",Toast.LENGTH_SHORT).show();
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

    public interface OnItemClickListner {
        void onItemClick(Event event);
    }


//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
