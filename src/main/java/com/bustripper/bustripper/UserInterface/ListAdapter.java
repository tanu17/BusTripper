package com.bustripper.bustripper.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bustripper.bustripper.R;

import java.util.List;

public class ListAdapter extends ArrayAdapter<BusTimes> {

    public ListAdapter(Context context, int resource){
        super(context, resource);
    }

    List<BusTimes> myList;

    public ListAdapter(Context context, int resource, List<BusTimes> items){
        super(context, resource, items);
        myList = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.service_time_layout_list_item, null);
        }

        BusTimes x = myList.get(position);

        if(x==null){return v;}

        TextView tv1 = (TextView) v.findViewById(R.id.service_no);
        TextView tv2 = (TextView) v.findViewById(R.id.time_one);
        TextView tv3 = (TextView) v.findViewById(R.id.time_two);
        TextView tv4 = (TextView) v.findViewById(R.id.time_three);

        //Log.d("Custom Adapter", "Setting Text :" +position);
        //Log.d("Custom Adapter", "Text :" + );

        tv1.setText(x.getNum());
        if(x.getT1()>0)
            tv2.setText(""+x.getT1());
        else if(x.getT1()<=0 && x.getT1()>-3){
            tv2.setText("Arr");
        }
        else if(x.getT1()<-2){
            tv2.setText("No ETA");
        }

        if(x.getT2()>0){
            tv3.setText(""+x.getT2());
        }
//        else if(x.getT2()<=0){
//            tv3.setText("Arr");
//        }
        else{
            tv3.setText("No ETA");
        }

        if(x.getT3()>0){
            tv4.setText(""+x.getT3());
        }else{
            tv4.setText("No ETA");
        }

        if(x.getT2()<0 && x.getT3()<0){
            tv3.setText("No");
            tv4.setText("ETA");
        }

        if((x.getT1()<0 && x.getT2()<0) && x.getT3()<0){
            tv2.setText("No");
            tv3.setText("Est.");
            tv4.setText("Avail");
        }

        //Log.d("Custom Adapter", "Set Text :" +position);

        return v;
    }
}
