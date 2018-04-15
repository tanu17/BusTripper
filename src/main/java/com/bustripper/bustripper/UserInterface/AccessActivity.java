package com.bustripper.bustripper.UserInterface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bustripper.bustripper.R;

public class AccessActivity extends AppCompatActivity {

    /**
     * Declaring widgets that will have functions
     */
    Button btnLogin;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access); // maps this class with a layout xml

        /**
         * Maps declared widgets to the actual ones in layout xml
         */
        btnLogin = (Button)findViewById(R.id.login);
        btnSignup = (Button)findViewById(R.id.signup);

        /**
         * Code to execute when Login button is tapped
         */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Layer = new Intent(AccessActivity.this, LoginActivity.class);
                startActivity(Layer);
            }
        }
        );

        /**
         * Code to execute when Sign Up button is tapped
         */
        btnSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Layer = new Intent(AccessActivity.this, SignUpActivity.class);
                startActivity(Layer);
            }
        }
        );
    }
}
