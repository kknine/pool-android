package com.pool.pool;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.pool.pool.model.Event;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private ArrayList<Event> mEventList;
    private EventListFragment.OnItemClickListner mListner;

    public EventAdapter(ArrayList<Event> eventList, EventListFragment.OnItemClickListner listener) {
        mEventList = eventList;
        mListner = listener;
    }


    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        if(position < mEventList.size())
            holder.bind(mEventList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mEventList == null)
            return 0;
        return mEventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        EventViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        void bind(final Event event) {
            TextView eventName_e = (TextView) itemView.findViewById(R.id.lie_name);
            TextView eventDatetime_e = (TextView) itemView.findViewById(R.id.lie_datetime);
            eventName_e.setText(event.getName());
            eventDatetime_e.setText(event.getDatetime());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListner.onItemClick(event);
                }
            });
        }
    }
}
