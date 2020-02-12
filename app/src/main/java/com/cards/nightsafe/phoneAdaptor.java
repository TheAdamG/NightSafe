package com.cards.nightsafe;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class phoneAdaptor extends androidx.recyclerview.widget.RecyclerView.Adapter {

    private ArrayList<GroupCall> calls;

    public phoneAdaptor(ArrayList<GroupCall> calls) {
        this.calls = calls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_call, parent, false);
        return new ViewHolder(v);
    }


    // Set what the elements to be displayed on the recycler does after we create them.
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ViewHolder vh = (ViewHolder) holder;
    GroupCall call = calls.get(position);
    vh.name.setText(call.getName());
    // Set the button's tag to the phone number. We can access this tag later when we need to
    // set the number the button is supposed to call
    vh.button.setTag(call.getPhoneNumber());
    }


    @Override
    public int getItemCount() {
        if (calls != null) {
            return calls.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        public final View view;
        public final TextView name;
        public final Button button;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            name = view.findViewById(R.id.name);
            button = view.findViewById(R.id.call);

        }
    }
}