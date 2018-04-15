package com.bustripper.bustripper.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bustripper.bustripper.Entity.Account;
import com.bustripper.bustripper.R;

public class LoginActivity extends AppCompatActivity {
    /**
     * Declaring widgets that will have functions
     */
    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    Button btnForgetPass;
    CheckBox checkBoxRememberMe;
    IAccountDAO mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mydb = new SQLiteDBHelper(this);

        /**
         * Maps declared widgets to the actual ones in layout xml
         */
        txtUsername = (EditText)findViewById(R.id.username);
        txtPassword = (EditText)findViewById(R.id.password);
        btnLogin = (Button)findViewById(R.id.login);
        btnForgetPass = (Button)findViewById(R.id.forgetPassword);
        checkBoxRememberMe = (CheckBox)findViewById(R.id.rememberMe);

        /**
         * Code to execute when Log in button is tapped
         */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                //Check user input is correct or not
                if (validate()) {
                    //Authenticate user
                    Account currentUser = mydb.Authenticate(txtUsername.getText().toString(), txtPassword.getText().toString());

                    //Check Authentication is successful or not
                    if (currentUser != null) {

                        Toast.makeText(LoginActivity.this, "Successfully signed in", Toast.LENGTH_LONG).show();
                        Intent Layer = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(Layer);
                    } else {
                        //User Logged in Failed
                        Toast.makeText(LoginActivity.this, "Failed to log in , please try again", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
        );
        /**
         * Code to execute when Forget Password button is tapped
         */
        btnForgetPass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Layer = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(Layer);
            }}
        );
    }

    public boolean validate(){
        boolean valid = false;
        //Get values from EditText fields
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        //Handling validation for Email field
        if (username.isEmpty()) {
            valid = false;
            txtUsername.setError("Please enter valid username!");
        } else {
            valid = true;
            txtUsername.setError(null);
        }

        //Handling validation for Password field
        if (password.isEmpty()) {
            valid = false;
            txtPassword.setError("Please enter valid password!");
        } else {
            if (password.length() < 8) {
                valid = false;
            } else {
                char c;
                int dCount = 0, lCount = 0, uCount = 0, sCount = 0;
                for (int i = 0; i < password.length(); i++) {
                    c = password.charAt(i);
                    if (Character.isDigit(c)) {
                        dCount++;
                    } else if (Character.isLowerCase(c)) {
                        lCount++;
                    } else if (Character.isUpperCase(c)) {
                        uCount++;
                    } else if (c >= 33 && c <= 46 || c == 64) {
                        sCount++;
                    }

                    if ((dCount > 0) && (lCount > 0) && (uCount > 0) && (sCount > 0)) {
                        valid = true;
                    }
                }
            }
        }
        return valid;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signup:
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

