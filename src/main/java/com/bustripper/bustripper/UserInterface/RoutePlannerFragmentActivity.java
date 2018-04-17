package com.bustripper.bustripper.UserInterface;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bustripper.bustripper.R;

public class RoutePlannerFragmentActivity extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ImageButton searchButton;
    private ImageView routedata1;
    private ImageView arrow;
    private ImageView routedata2;
    private Button routedata1button;
    private EditText endAdd;
    private EditText startAdd;
    private ImageButton current_loc_icon;
    private ProgressBar pb;
    private Button weatherbutton;
    private Button secretbutton;


    public RoutePlannerFragmentActivity() {
    }

/**
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
**/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_route_planner,container,false);


        searchButton = (ImageButton) view.findViewById(R.id.searchGlassEnd);
        routedata1 = (ImageView) view.findViewById(R.id.routedata1);
        routedata2 = (ImageView) view.findViewById(R.id.routedata2);
        arrow = (ImageView) view.findViewById(R.id.arrow);

        endAdd = (EditText) view.findViewById(R.id.endAdd);
        startAdd = (EditText) view.findViewById(R.id.startAdd);
        current_loc_icon = (ImageButton) view.findViewById(R.id.current_loc_icon);

        routedata1button = (Button) view.findViewById(R.id.routedata1button);
        pb = (ProgressBar) view.findViewById(R.id.loadingPanel);
        weatherbutton = (Button) view.findViewById(R.id.weatherbutton);
        secretbutton = (Button) view.findViewById(R.id.secretbutton);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // delay for 1 s
                        pb.setVisibility(View.INVISIBLE);
                        routedata1.setVisibility(View.VISIBLE);
                        arrow.setVisibility(View.INVISIBLE);
                        routedata1button.setVisibility(View.VISIBLE);
                        endAdd.setVisibility(View.INVISIBLE);
                        startAdd.setVisibility(View.INVISIBLE);
                        current_loc_icon.setVisibility(View.INVISIBLE);

                    }
                }, 1000);   //1 seconds


            }
        });
        routedata1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pb.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // delay for 1 s
                        pb.setVisibility(View.INVISIBLE);
                        routedata1.setImageResource(R.drawable.weather2);
                        routedata1.setScaleY(1.0f);
                        weatherbutton.setVisibility(View.VISIBLE);
                        secretbutton.setVisibility(View.VISIBLE);

                        ((MainActivity)getActivity()).routeput();


                        //routedata1.setVisibility(View.INVISIBLE);
                        // routedata2.setVisibility(View.VISIBLE);

                    }
                }, 1000);   //2 seconds


            }
        });

        weatherbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //routedata1.setImageResource(R.drawable.weather1);
                //routedata1.setScaleY(1.05f);
                NotificationManager nm = new NotificationManager();
                nm.notify(getActivity());


            }
        });

        secretbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //routedata1.setImageResource(R.drawable.weather1);
                //routedata1.setScaleY(1.05f);
                NotificationManager nm = new NotificationManager();
                nm.notify_alighting(getActivity());


            }
        });



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
