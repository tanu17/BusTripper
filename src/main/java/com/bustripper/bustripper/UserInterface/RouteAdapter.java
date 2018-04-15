package com.bustripper.bustripper.UserInterface;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bustripper.bustripper.R;

/**
 * Created by User on 3/28/2018.
 */

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView start, end, destination, startCode, endCode, desCode;
        public ViewHolder(TextView v) {
            super(v);
            start = (TextView) itemView.findViewById(R.id.start);
            end = (TextView) itemView.findViewById(R.id.end);
            destination = (TextView) itemView.findViewById(R.id.destination);
            startCode = (TextView) itemView.findViewById(R.id.startCode);
            endCode = (TextView) itemView.findViewById(R.id.endCode);
            desCode = (TextView) itemView.findViewById(R.id.desCode);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RouteAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RouteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_route_recycler, parent, false);
        //...
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.start.setText("NTU");
        holder.end.setText("NTU");
        holder.destination.setText("NTU");
        holder.startCode.setText("NTU");
        holder.endCode.setText("NTU");
        holder.desCode.setText("NTU");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
