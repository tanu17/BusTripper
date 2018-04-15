package com.bustripper.bustripper.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.bustripper.bustripper.Entity.Account;
import com.bustripper.bustripper.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    /**
     * Declaring widgets that will have functions
     */
    EditText txtUsername;
    IAccountDAO mydb;
    EditText txtEmail;
    Button btnProceed;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        mydb = new SQLiteDBHelper(this);

        /**
         * Maps declared widgets to the actual ones in layout xml
         */
        txtUsername = (EditText) findViewById(R.id.username);
        txtEmail = (EditText) findViewById(R.id.email);
        btnProceed = (Button) findViewById(R.id.proceed);
        btnCancel = (Button) findViewById(R.id.cancel);

        /**
         * Code to execute when Proceed button is tapped
         */
        btnProceed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: send email hahaha maybe don't need to do

                if (!mydb.isUsernameExists(txtUsername.getText().toString()) && !mydb.isEmailExists(txtEmail.getText().toString())) {
                    Toast.makeText(ForgetPasswordActivity.this, "An email has been sent", Toast.LENGTH_LONG).show();
                    Intent Layer = new Intent(ForgetPasswordActivity.this, LoginActivity.class); // bring back to login page after sending link
                    startActivity(Layer);
                } else {
                    //User Logged in Failed
                    Toast.makeText(ForgetPasswordActivity.this, "Please enter valid User Details", Toast.LENGTH_LONG).show();
                }
            }
        });


        /**
         * Code to execute when Cancel button is tapped
         */
        btnCancel.setOnClickListener(new View.OnClickListener() {
                                         public void onClick(View v) {
                                             Intent Layer = new Intent(ForgetPasswordActivity.this, LoginActivity.class); // go back to login page
                                             startActivity(Layer);
                                         }
                                     }
        );
    }

}