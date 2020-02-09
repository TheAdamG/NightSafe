package com.cards.nightsafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static int username = 3;

    int batteryPercent;
    String lastSeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.help_button).setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            launchEmergency(v);
            return true;
        }});


        Calendar currentTime = Calendar.getInstance();
        DateFormat sdf = new SimpleDateFormat("hh:mm");
        lastSeen = sdf.format(currentTime.getTime());
        Log.v("TIMETEST", lastSeen);
        getBattery_percentage();

        new MainActivity.Upload(MainActivity.this).execute();
    }



    void getBattery_percentage()
    {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level / (float)scale;
        float p = batteryPct * 100;

        batteryPercent = Math.round(p);

    }

    private void launchEmergency(View view) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:999"));
        startActivity(callIntent);
    }


    public void launchPhone(View view) {
        Intent intent = new Intent(this, phoneActivity.class);
        startActivity(intent);
    }

    public void launchGoHome(View view) {
        Intent intent = new Intent(this, goHome.class);
        startActivity(intent);
    }

    public void launchFind(View view) {
        Intent intent = new Intent(this, find_friends.class);
        startActivity(intent);
    }


    private class Upload extends AsyncTask<Void, Void, String> {

        Context context;
        private RecyclerView calls;

        public Upload(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        protected String doInBackground(Void... params) {
             Queries.ChangeStatusQuery(MainActivity.username, batteryPercent, lastSeen );
            return "Complete";
        }

        protected void onPostExecute(String result) {
            if (result.equals("Complete")) {

            }
            }
        }




    }
