package com.cards.nightsafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class phoneActivity extends AppCompatActivity {

    private RecyclerView calls;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        ArrayList<GroupCall> calls = GroupCall.makeGroupCall();

        this.calls = (RecyclerView) findViewById(R.id.calls);
        RecyclerView.LayoutManager mLayourManager = new LinearLayoutManager(this);
        this.calls.setLayoutManager(mLayourManager);

        adapter = new phoneAdaptor(calls);
        this.calls.setAdapter(adapter);
    }


    public void testCall (View view) {
        String phoneNumber = (String) view.getTag();
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

}
