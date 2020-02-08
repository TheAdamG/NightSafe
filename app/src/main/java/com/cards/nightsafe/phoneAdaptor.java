package com.cards.nightsafe;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class phoneAdaptor extends androidx.recyclerview.widget.RecyclerView.Adapter {

    private ArrayList<GroupCall> groupCalls;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (groupCalls == null) {
            return 0;
        }
        return groupCalls.size();
    }

    /*
    public static class phoneViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {

        public final View view;
        public final TextView name;
        public final Button button;

        public phoneViewHolder(View view) {
            super(view);

        }

    } */

}


