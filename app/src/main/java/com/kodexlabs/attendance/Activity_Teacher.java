package com.kodexlabs.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

/**
 * Created by 1505560 on 04-Nov-17.
 */

public class Activity_Teacher extends AppCompatActivity {

    private TextView keys, step, time;
    private String getkeys = "";
    private String cls;
    private int myid, code, ms, count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        Bundle bundle = getIntent().getExtras();
        myid = bundle.getInt("myid");
        code = bundle.getInt("code");

        keys = (TextView)findViewById(R.id.keys);
        step = (TextView)findViewById(R.id.step);
        time = (TextView)findViewById(R.id.time);

        generate_keys();
    }

    private void generate_keys() {
        long random = (long) (Math.random() * 10000000000000000L);
        getkeys += random;

        firebase_session(getkeys);

        new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                ms = (int) (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                time.setText(""+String.format("%d Sec", (ms%5)+1));
                if ((ms+1) % 5 == 0){
                    keys.setText(getkeys.substring(count*4, (count*4)+4));
                    count++;
                    step.setText("Step " + count);
                }
            }
            public void onFinish() {
                Intent intent = new Intent(getBaseContext(), Activity_Status.class);
                intent.putExtra("result", 2);
                startActivity(intent);
            }
        }.start();
    }

    private void firebase_session(String random) {
        cls = "CS08 3rd Yr";

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Attendance/Teachers/"+myid+"/sessions/"+code);
        databaseReference.child("keys").setValue(random);
        databaseReference.child("id").setValue(""+code);
        databaseReference.child("cls").setValue(cls);
        databaseReference.child("ontime").setValue(""+(System.currentTimeMillis()+25000));
        databaseReference.child("rolls/0000000").setValue(""+System.currentTimeMillis());
    }
}