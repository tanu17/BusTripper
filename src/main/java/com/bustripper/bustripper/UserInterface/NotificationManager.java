package com.bustripper.bustripper.UserInterface;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.bustripper.bustripper.R;

/**
 * Created by chankennard on 16/4/18.
 */

public class NotificationManager {


    public void notify(Context context){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        final ImageView iv = new ImageView(context);
        iv.setImageResource(R.drawable.notification1);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);


        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(iv);

        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
        alertDialog.getWindow().setLayout(1200, 750); //Controlling width and height.



    }


    public void notify_alighting(Context context){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        final ImageView iv = new ImageView(context);
        iv.setImageResource(R.drawable.notification2);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);


        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(iv);

        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
        alertDialog.getWindow().setLayout(1200, 750); //Controlling width and height.



    }


}
