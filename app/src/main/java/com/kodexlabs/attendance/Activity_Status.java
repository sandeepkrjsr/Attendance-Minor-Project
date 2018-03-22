package com.kodexlabs.attendance;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by 1505560 on 05-Nov-17.
 */

public class Activity_Status extends AppCompatActivity{

    TextView status;
    int result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Bundle bundle = getIntent().getExtras();
        result = bundle.getInt("result");

        status = (TextView)findViewById(R.id.status);

        if (result == 1) {
            status.setText("Attendance Marked\nSuccessfully");
            status.setTextColor(Color.GREEN);
        }else if (result == 0){
            status.setText("Attendance Marked\nUnSuccessfully");
            status.setTextColor(Color.RED);
        }
    }
}
