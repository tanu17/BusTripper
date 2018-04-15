package com.bustripper.bustripper.UserInterface;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bustripper.bustripper.Entity.BusService;
import com.bustripper.bustripper.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * An {@link BusServiceAdapter} knows how to create a list item layout for each earthquake
 * in the data source (a list of {@link com.bustripper.bustripper.Entity.BusService} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class BusServiceAdapter extends ArrayAdapter<BusService> {

//    /**
//     * The part of the location string from the USGS service that we use to determine
//     * whether or not there is a location offset present ("5km N of Cairo, Egypt").
//     */
//    private static final String LOCATION_SEPARATOR = " of ";

    /**
     * Constructs a new {@link BusServiceAdapter}.
     *
     * @param context of the app
     * @param busServices is the list of bus service, which is the data source of the adapter
     */
    public BusServiceAdapter(Context context, List<BusService> busServices) {
        super(context, 0, busServices);
    }
    /**
     * Returns a list item view that displays information about the earthquake at the given position
     * in the list of earthquakes.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.busservice_list_item, parent, false);
        }

        BusService currentBusService = getItem(position);

        TextView busStopNameView = (TextView) listItemView.findViewById(R.id.bus_stop_name);
        busStopNameView.setText(currentBusService.getBusStopName());

        TextView busStopCodeView = (TextView) listItemView.findViewById(R.id.bus_stop_code);
        busStopCodeView.setText(currentBusService.getBusStopCode());

        ImageView nextArrivalwcView = (ImageView) listItemView.findViewById(R.id.next_arrival_1_wca);
        //nextArrival1wcView.setImageResource(R.drawable.no_wheelchair);
        TextView nextArrivalTimeView = (TextView) listItemView.findViewById(R.id.next_arrival_1);
        nextArrivalTimeView.setText(currentBusService.getArrivalTimes().get(0));

        ImageView nextArrival2wcView = (ImageView) listItemView.findViewById(R.id.next_arrival_2_wca);
        //nextArrival2wcView.setImageResource(R.drawable.no_wheelchair);
        TextView nextArrivalTime2View = (TextView) listItemView.findViewById(R.id.next_arrival_2);
        nextArrivalTime2View.setText(currentBusService.getArrivalTimes().get(1));

        ImageView nextArriva13wcView = (ImageView) listItemView.findViewById(R.id.next_arrival_3_wca);
        //nextArrival3wcView.setImageResource(R.drawable.no_wheelchair);
        TextView nextArrivalTime3View = (TextView) listItemView.findViewById(R.id.next_arrival_3);
        nextArrivalTime3View.setText(currentBusService.getArrivalTimes().get(2));

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}
