package com.kodexlabs.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by 1505560 on 05-Nov-17.
 */

public class Activity_Server extends AppCompatActivity {

    private Button connect;
    private TextView myid, code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        connect = (Button)findViewById(R.id.connect);
        myid = (TextView)findViewById(R.id.myid);
        code = (TextView)findViewById(R.id.code);

        final int random = (int)(Math.random() * 1000);
        myid.setText("My ID : " + "125");
        code.setText("Session Code : " + random);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Activity_Teacher.class);
                intent.putExtra("myid", 125);
                intent.putExtra("code", random);
                startActivity(intent);
            }
        });
    }
}
