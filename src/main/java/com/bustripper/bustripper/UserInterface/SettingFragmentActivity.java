package com.bustripper.bustripper.UserInterface;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bustripper.bustripper.Entity.BusService;
import com.bustripper.bustripper.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingFragmentActivity.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingFragmentActivity newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragmentActivity extends Fragment {

    // TODO: Rename and change types of parameters
    private View rootView;
    EditText oriPass;
    EditText newPass;
    EditText confirmPass;
    EditText arrivalTime;
    EditText alightDist;

    public SettingFragmentActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        // Find a reference to the password section in the layout
        oriPass = (EditText)rootView.findViewById(R.id.setting_original_pass_edit);
        newPass = (EditText)rootView.findViewById(R.id.setting_new_pass_edit);
        confirmPass = (EditText)rootView.findViewById(R.id.setting_confirm_pass_edit);

        Button passButton = (Button) rootView.findViewById(R.id.setting_save_button_pass);

        passButton.setOnClickListener(new View.OnClickListener() {
                                             @Override public void onClick(View view){
                                                 Toast.makeText(getActivity(), "Password Saved",
                                                         Toast.LENGTH_LONG).show();
                                             }
                                         }
        );

        // Find a reference to the notification section in the layout
        arrivalTime = (EditText)rootView.findViewById(R.id.setting_arrival_edit);
        Switch arrivalSwitch = (Switch) rootView.findViewById(R.id.setting_arrival_switch);
        alightDist = (EditText)rootView.findViewById(R.id.setting_alight_edit);
        Switch alightSwitch = (Switch) rootView.findViewById(R.id.setting_alight_switch);

        Button notiButton = (Button) rootView.findViewById(R.id.setting_save_button_noti);
            notiButton.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  if (verifyArrival(arrivalTime.getText().toString()) && verifyAlight(alightDist.getText().toString())) {
                                                      Toast.makeText(getActivity(), "Notification Settings Saved",
                                                              Toast.LENGTH_LONG).show();
                                                  } else {
                                                      Toast.makeText(getActivity(), "Input Out of Range!",
                                                              Toast.LENGTH_LONG).show();
                                                  }
                                              }
                                          }
            );


        return rootView;
    }

    private boolean verifyArrival(String arrivalTime){
        if (arrivalTime.isEmpty()){
            return false;
        }

        double time = Double.valueOf(arrivalTime);
        if (time < 1 || time > 15){
            return false;
        }
        else{
            return true;
        }
    }

    private boolean verifyAlight(String alightDist){
        if (alightDist.isEmpty()){
            return false;
        }

        double dist = Double.valueOf(alightDist);
        if (dist < 100 || dist > 1000){
            return false;
        }
        else{
            return true;
        }
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
