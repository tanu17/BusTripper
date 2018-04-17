package com.bustripper.bustripper.UserInterface;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bustripper.bustripper.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BusStopActivity extends AppCompatActivity implements GetBusTimes.onReceivedBusTimes {

    static int busOrBusStop=0;
    static ArrayList<BusTimes> differentStops = new ArrayList<BusTimes>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find_bus_stop);
        final Intent intent = getIntent();
        final String num = intent.getStringExtra("number");
        final Context thisContext = this;

        if(num==null){
            Toast.makeText(BusStopActivity.this, "No such stop!", Toast.LENGTH_SHORT).show();
            kill_activity();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        TextView stop_name = (TextView)findViewById(R.id.textView_stop_name);
        TextView stop_no = (TextView)findViewById(R.id.textView_stop_no);
        TextView ServiceView= (TextView) findViewById(R.id.ServicetextView);


        String name = null;
        JSONArray busStops=null;
        if (num.length()==5) {

            try {
                JSONObject jsonStopInfo = new JSONObject(loadJSONFromAsset("stopInfo.json"));
                name = jsonStopInfo.getJSONObject(num).getString("name");
                stop_name.setText(name);
                stop_no.setText(num);
                GetBusTimes getBusTimes = new GetBusTimes(this);
                getBusTimes.execute(num);
                assert fab != null;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(BusStopActivity.this, "No such stop!", Toast.LENGTH_SHORT).show();
            }
        }
        else if (num.length()<=4){
            try {
                JSONObject jsonBusInfo = new JSONObject(loadJSONFromAsset("reverseJson.json"));
                busStops = jsonBusInfo.getJSONArray(num);
                stop_name.setText("");
                stop_no.setText(num);
                ServiceView.setText("BusStops");
                busOrBusStop=1;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(BusStopActivity.this, "No such BusService!", Toast.LENGTH_SHORT).show();
                kill_activity();
            }

        }
        //so that it can be used in the onClick
        final String nam = name;
        if (busOrBusStop ==1){
            GetTimeforBusAndStop getBusTimes = null;
            BusTimes busTime = null;
            for (int i=0;i<busStops.length();i++){
                try {
                    String stopName="";
                    JSONObject jsonStopInfo1 = new JSONObject(loadJSONFromAsset("stopInfo.json"));
                    stopName = jsonStopInfo1.getJSONObject(busStops.getString(i)).getString("name");
                    getBusTimes = new GetTimeforBusAndStop(busStops.getString(i),num,stopName);
                    busTime = getBusTimes.main();
                    differentStops.add(busTime);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
        onReceived(differentStops);}

        assert fab != null;


        final JSONArray finalBusStops = busStops;
        final JSONArray finalBusStops1 = busStops;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refreshing", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                if (busOrBusStop!=1){
                    GetBusTimes getBusTimes = new GetBusTimes(thisContext);
                    getBusTimes.execute(num);
                }
                else{
                    for (int i = 0; i< finalBusStops.length(); i++){
                        GetTimeforBusAndStop getBusTimes = null;
                        differentStops= new ArrayList<BusTimes>();
                        try {
                            String stopName="";
                            JSONObject jsonStopInfo1 = new JSONObject(loadJSONFromAsset("stopInfo.json"));
                            stopName = jsonStopInfo1.getJSONObject(finalBusStops1.getString(i)).getString("name");
                            getBusTimes = new GetTimeforBusAndStop(finalBusStops1.getString(i),num,stopName);
                            differentStops.add(getBusTimes.main());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    onReceived(differentStops);

                }
            }
        });

    }

    @Override
    public void onReceived(ArrayList<BusTimes> busTimes) {
        ListView lv = (ListView) findViewById(R.id.services_at_stop);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.hide_searchview);
        linearLayout.setVisibility(View.GONE);
        ListAdapter mAdapter = new ListAdapter(getBaseContext(), R.layout.service_time_layout_list_item, busTimes);
        assert lv != null;
        lv.setAdapter(mAdapter);
    }


    public void kill_activity(){
        finish();
    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
