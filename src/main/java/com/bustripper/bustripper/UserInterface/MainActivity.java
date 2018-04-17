package com.bustripper.bustripper.UserInterface;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bustripper.bustripper.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView routefromhalltojurong;
    private TabLayout tabLayout;
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("Tag= " + TAG);
        Log.v(TAG, " XMl start");

        setContentView(R.layout.activity_main);
        Log.v(TAG, " Map start");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Log.v(TAG, " Map end");

        routefromhalltojurong = (ImageView) findViewById(R.id.routefromhalltojurong) ;
        tabLayout = (TabLayout) findViewById(R.id.banner);
        v = (View) findViewById(R.id.map);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ActivityAdapter adapter = new ActivityAdapter(this, getSupportFragmentManager());

        Log.v(TAG, "Before Adding Fragment");

        //Adding Fragments
        adapter.AddFragment(new RoutePlannerFragmentActivity());
        adapter.AddFragment(new FindBusServiceActivity());
        adapter.AddFragment(new SettingFragmentActivity());

        Log.v(TAG, " Before adapter setup");

        //Adapter Setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Log.v(TAG, "After Adding Fragment");

        int[] imageResId = {
                R.drawable.routeplanner,
                R.drawable.findbus,
                R.drawable.settings};

        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }
      //  final int interval = 10000; // 10 Second
      //  Handler handler = new Handler();
      //  Runnable runnable = new Runnable(){
      //      public void run() {
      //          NotificationManager nm = new NotificationManager();
      //          nm.notify(MainActivity.this);
      //      }
      //  };

       // handler.postAtTime(runnable, System.currentTimeMillis()+interval);
      //  handler.postDelayed(runnable, interval);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent intent = new Intent(getApplicationContext(), AccessActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
        LatLng singapore = new LatLng(1.3521, 103.8198);
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.stylr_maps));
            if (!success) {
                Log.e("MapsAct", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsAct", "Can't find style. Error: ", e);

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(singapore));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.5f));
    }

    public void routeput(){

        v.setVisibility(View.INVISIBLE);
        routefromhalltojurong.setVisibility(View.VISIBLE);

        tabLayout.getLayoutParams().height = 330;
        routefromhalltojurong.getLayoutParams().height = 1620;
        routefromhalltojurong.getLayoutParams().width = 1500;
    }

}
