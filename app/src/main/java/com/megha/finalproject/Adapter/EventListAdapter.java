package com.megha.finalproject.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.megha.finalproject.Entities.Activities;
import com.megha.finalproject.MainActivity;
import com.megha.finalproject.R;
import com.megha.finalproject.Service.Bachhpan;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private Context mContext;
    MainActivity mainActivity;

    private List<Activities> activities;
    String likest;

    public EventListAdapter(Context mContext, List<Activities> activities, MainActivity mainActivity) {
        this.mContext = mContext;
        this.activities = activities;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_events_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int like = activities.get(position).getLikes();
        likest = Integer.toString(like);

        holder.likes.setText(likest);

        if (!activities.get(position).getImage().equals("")) {
            Picasso.get().load(activities.get(position).getImage()).into(holder.eventimage);
        }
        holder.event_name.setText(activities.get(position).getName());


        holder.fav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MainActivity.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Bachhpan bachhpan = retrofit.create(Bachhpan.class);

                Call<Boolean> allActivities = bachhpan.IncreaseLike(String.valueOf(activities.get(position).getActivity_id()));
                allActivities.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful()) {
                            Log.e("AllActivites", "Success");
                            mainActivity.CallAPitoGetEventList();
                            holder.fav_icon.setImageResource(R.drawable.fav_black);
                            //Toast.makeText(mContext, "Liked", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        holder.fav_icon.setImageResource(R.drawable.fav_border);
                        Log.e("AllActivites", "Error" + t.getMessage());
                    }


                });

            }
        });
    }


    @Override
    public int getItemCount() {
        return activities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView eventimage, fav_icon;
        public TextView likes, event_name, event_desc;

        public ViewHolder(View itemView) {
            super(itemView);
            likes = itemView.findViewById(R.id.likes);
            eventimage = itemView.findViewById(R.id.activity_image);
            event_name = itemView.findViewById(R.id.event_name);
            fav_icon = itemView.findViewById(R.id.fav_icon);

        }
    }
}
