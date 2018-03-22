package com.kodexlabs.attendance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by 1505560 on 12-Nov-17.
 */

public class Activity_Info extends AppCompatActivity{

    Button submit;
    EditText name, roll;

    String username, userroll;

    private static SharedPreferences preferences;
    private String prefName = "MyPref";
    private static final String UID = "UID";
    private static final String USER = "USER";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        submit = (Button)findViewById(R.id.submit);
        name = (EditText)findViewById(R.id.name);
        roll = (EditText)findViewById(R.id.roll);

        preferences = getSharedPreferences(prefName, MODE_PRIVATE);
        String loggedin = preferences.getString(UID, "UID");
        String user = preferences.getString(USER, "USER");

        if (loggedin.equals("UID"))
            mydetails();
        else
            nextActivity(loggedin, user);
    }

    private void mydetails() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userroll = roll.getText().toString();
                username = name.getText().toString();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(UID, userroll);
                editor.putString(USER, username);
                editor.commit();

                nextActivity(userroll, username);
            }
        });
    }

    private void nextActivity(String userroll, String username) {
        if(userroll != null){
            Intent intent = new Intent(getBaseContext(), Activity_Choice.class);
            intent.putExtra("roll", userroll);
            intent.putExtra("name", username);
            startActivity(intent);
            finish();
        }
    }
}
