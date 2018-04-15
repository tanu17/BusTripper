package com.bustripper.bustripper.UserInterface;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bustripper.bustripper.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BusStopActivity extends AppCompatActivity implements GetBusTimes.onReceivedBusTimes {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find_bus_service);


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



        String name = null;
        JSONArray name1=null;
        if (num.length()==5) {

            try {
                JSONObject jsonStopInfo = new JSONObject(loadJSONFromAsset("stopInfo.json"));
                name = jsonStopInfo.getJSONObject(num).getString("name");
                stop_name.setText(name);
                stop_no.setText(num);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(BusStopActivity.this, "No such stop!", Toast.LENGTH_SHORT).show();
                kill_activity();
            }
        }
        else if (num.length()<=4){
            try {
                JSONObject jsonStopInfo = new JSONObject(loadJSONFromAsset("busTest.json"));
                name1 = jsonStopInfo.getJSONArray(num);
                stop_name.setText(num);
                stop_no.setText("");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(BusStopActivity.this, "No such stop!", Toast.LENGTH_SHORT).show();
                kill_activity();
            }

        }
        //so that it can be used in the onClick
        final String nam = name;



        final GetBusTimes getBusTimes = new GetBusTimes(this);
        getBusTimes.execute(num);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Refreshing", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();
                GetBusTimes getBusTimes = new GetBusTimes(thisContext);
                getBusTimes.execute(num);
            }
        });

    }

    @Override
    public void onReceived(ArrayList<BusTimes> busTimes) {
        ListView lv = (ListView) findViewById(R.id.services_at_stop);
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
