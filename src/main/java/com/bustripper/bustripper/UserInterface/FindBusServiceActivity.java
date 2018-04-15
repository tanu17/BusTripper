package com.bustripper.bustripper.UserInterface;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import com.bustripper.bustripper.Entity.BusService;
import com.bustripper.bustripper.R;
import java.util.ArrayList;
import java.util.List;
import android.support.v4.content.Loader;
import android.widget.Toast;
import static android.graphics.PorterDuff.*;

public class FindBusServiceActivity extends Fragment implements LoaderManager.LoaderCallbacks<List<BusService>> {
    public FindBusServiceActivity(){}
    private static final String LTA_ROUTE_REQUEST_URL = "http://datamall2.mytransport.sg/ltaodataservice/BusRoutes";
    private static final String LTA_BUS_SERVICE_REQUEST_URL = "http://datamall2.mytransport.sg/ltaodataservice/BusArrivalv2";
    private static final int BUS_SERVICE_LOADER_ID = 1;
    private BusServiceAdapter mAdapter;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1234;
    private TextView mEmptyStateTextView;
    private String userServiceNo;
    View rootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_find_bus_stop, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final EditText busNoEditText = (EditText)rootView.findViewById(R.id.find_bus_no_input);


        //search button
        final Button findBusButton = (Button) rootView.findViewById(R.id.find_bus_no_button);
        findBusButton.setOnClickListener(new View.OnClickListener() {
                                             @Override public void onClick(View view){
                                                 //input from xml
                                                 userServiceNo = busNoEditText.getText().toString();
                                             }
                                         }
        );

        findBusButton.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), Mode.MULTIPLY);


        findBusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getLoaderManager().restartLoader(BUS_SERVICE_LOADER_ID, null, FindBusServiceActivity.this);
                Intent intent = new Intent(getContext(), BusStopActivity.class);
                intent.putExtra("number", busNoEditText.getText().toString());
                startActivity(intent);
            }
        });

        busNoEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()== KeyEvent.ACTION_DOWN){
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            Intent intent = new Intent(getContext(), BusStopActivity.class);
                            intent.putExtra("number",busNoEditText.getText().toString());
                            startActivity(intent);
                            return true;
                        default:
                            break;}
                }
                return false;
            }
        }) ;

    }

    @Override
    public Loader<List<BusService>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BusServiceLoader(getActivity(), LTA_ROUTE_REQUEST_URL, LTA_BUS_SERVICE_REQUEST_URL, userServiceNo); }

    @Override
    public void onLoadFinished(Loader<List<BusService>> loader, List<BusService> busServices) {
        // Hide loading indicator because the data has been loaded
        //View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        //loadingIndicator.setVisibility(View.GONE);

        //mEmptyStateTextView.setText(R.string.no_bus_services);
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (busServices != null && !busServices.isEmpty()) {
            updateUi(busServices); }
    }

    private void updateUi(List<BusService> busServices){ mAdapter.addAll(busServices); }

    @Override
    public void onLoaderReset(Loader<List<BusService>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear(); }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
