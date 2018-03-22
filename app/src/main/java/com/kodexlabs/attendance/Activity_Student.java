package com.kodexlabs.attendance;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Activity_Student extends AppCompatActivity {

    private EditText input;
    private TextView step, time;

    private String username, userroll;
    private String getinput = "", getkeys = "", getontime;
    private String tutorid, code;
    private int match, ms, count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Bundle bundle = getIntent().getExtras();
        tutorid = bundle.getString("tutorid");
        code = bundle.getString("code");
        userroll = bundle.getString("roll");
        username = bundle.getString("name");

        input = (EditText)findViewById(R.id.input);
        step = (TextView)findViewById(R.id.step);
        time = (TextView)findViewById(R.id.time);

        enter_keys();
    }

    private void enter_keys() {

        input.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            /*if (count == 4 ) {
                                input.setVisibility(View.GONE);
                                step.setVisibility(View.GONE);
                                time.setVisibility(View.GONE);
                                status.setVisibility(View.VISIBLE);
                            } else {
                                count++;
                                getinput += input.getText().toString();
                                input.setText("");
                                step.setText("Step "+count);
                            }*/
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                ms = (int) (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                time.setText(""+String.format("%d Sec", (ms%5)+1));
                if ((ms+1) % 5 == 0){

                    if (count == 0)
                        getinput += "";
                    else if (input.getText().toString().length() == 4)
                        getinput += input.getText().toString();
                    else
                        getinput += "0000";

                    count++;
                    step.setText("Step "+count);
                    input.setText("");
                }
            }
            public void onFinish() {
                if (input.getText().toString().length() == 4)
                    getinput += input.getText().toString();
                else
                    getinput += "0000";

                check_validity(getinput);
            }
        }.start();

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return true;
            }
        });

    }

    private void check_validity(final String getinput) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Attendance/Teachers/"+tutorid+"/sessions/"+code);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, String> map = (Map<String, String>) snapshot.getValue();

                getkeys = map.get("keys");
                getontime = map.get("ontime");

                for (int i = 0; i < 4; i++){
                    if (getkeys.substring(i*4, i*4+4).compareTo(getinput.substring(i*4, i*4+4))==0)
                        match++;
                }
                Long limit = Long.parseLong(getontime)-System.currentTimeMillis();
                if (match >= 3 && limit >= 0){
                    firebase_entry();
                }else {
                    Intent intent = new Intent(getBaseContext(), Activity_Status.class);
                    intent.putExtra("result", 0);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "No Server",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void firebase_entry() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Attendance/Teachers/"+tutorid+"/sessions/"+code);
        databaseReference.child("rolls").child(userroll).setValue(username);

        Intent intent = new Intent(getBaseContext(), Activity_Status.class);
        intent.putExtra("result", 1);
        startActivity(intent);
    }
}
