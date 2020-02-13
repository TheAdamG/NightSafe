package com.cards.nightsafe;



import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class findAdaptor extends androidx.recyclerview.widget.RecyclerView.Adapter {

    private ArrayList<GroupFind> groupMembers;

    public findAdaptor(ArrayList<GroupFind> groupMembers ) {
        this.groupMembers = groupMembers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_table, parent, false);
        return new ViewHolder(v);
    }

    private static String getLastSeen(String time) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("hh:mm");
        Date seenTime = sdf.parse(time);
        Calendar cal = Calendar.getInstance();
        Date currentTime = sdf.parse(sdf.format(cal.getTime()));
        return String.valueOf(Math.abs(seenTime.getTime() - currentTime.getTime())/1000/60);
    }

    // Sets up the linear layout with each button to have the current position as well as
    // sets the image to the user's correct battery percentage

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        GroupFind group = groupMembers.get(position);
        vh.findName.setText(group.getName());
        Log.v("lastSeenTime", group.getLastSeenTime());

        try{
            String s = "Last seen: " +  getLastSeen(group.getLastSeenTime()) + " mins ago";
        vh.findLastSeen.setText(s); }
        catch (ParseException e) {
        }

        if (group.getBatteryPercentage() < 25) {
            vh.batteryImage.setImageResource(R.drawable.zero);
        }
        else if (group.getBatteryPercentage() < 50) {
            vh.batteryImage.setImageResource(R.drawable.fifty);
        }
        else if (group.getBatteryPercentage() < 75) {
            vh.batteryImage.setImageResource(R.drawable.seventy);
        } else {
            vh.batteryImage.setImageResource(R.drawable.hundred);
        }

        LatLng latLng = new LatLng(group.getLatitude(), group.getLongitude());
        vh.friendButton.setTag(latLng);


    }


    @Override
    public int getItemCount() {
        if (groupMembers != null) {
            return groupMembers.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        public final View view;
        public final TextView findName;
        public final TextView findLastSeen;
        public final ImageView batteryImage;
        public final LinearLayout friendButton;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            findName = view.findViewById(R.id.findName);
            findLastSeen = view.findViewById(R.id.findLastSeen);
            batteryImage = view.findViewById(R.id.batteryImage);
            friendButton = view.findViewById(R.id.friendButton);

        }
    }
}