package com.cards.nightsafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class phoneActivity extends AppCompatActivity {


    private RecyclerView.Adapter adapter;
    ArrayList<GroupCall> callsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        new Download(phoneActivity.this).execute();
        //ArrayList<GroupCall> calls = GroupCall.makeGroupCall();




    }



    public void testCall (View view) {
        String phoneNumber = (String) view.getTag();
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }


    private class Download extends AsyncTask<Void, Void, String> {

        Context context;
        private RecyclerView calls;

        public Download(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        protected String doInBackground(Void... params) {
            callsList = Queries.GroupCallQuery(MainActivity.username, 10);
            return "Complete";
        }

        // Once the query is complete, we set the Recycler view content  to display the names with
        // The call button and the respective phone number tagged to the button
        protected void onPostExecute(String result) {
            if (result.equals("Complete")) {
                this.calls = (RecyclerView) findViewById(R.id.calls);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                this.calls.setLayoutManager(mLayoutManager);

                adapter = new phoneAdaptor(callsList);
                this.calls.setAdapter(adapter);
            }
        }





    }



}
