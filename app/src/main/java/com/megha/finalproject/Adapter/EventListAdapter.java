package com.megha.finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.megha.finalproject.Entities.Activities;
import com.megha.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class EventListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Activities> activities;

    public EventListAdapter(Context mContext, List<Activities> activities) {
        this.mContext = mContext;
        this.activities = activities;
    }

    @Override
    public int getCount() {
        return activities.size();
    }

    @Override
    public Object getItem(int position) {
        return activities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {

        View view = contentView;

        if(view == null){
            LayoutInflater vi;
            vi =LayoutInflater.from(mContext);
            view =vi.inflate(R.layout.activity_events_list,null);
        }

        TextView event_name = view.findViewById(R.id.event_name);
        event_name.setText(activities.get(position).getName());

        return view;
    }
}
