package com.kodexlabs.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 1505560 on 05-Nov-17.
 */

public class Activity_Client extends AppCompatActivity {

    Button connect;
    EditText tutorid, code;

    String username, userroll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        Bundle bundle = getIntent().getExtras();
        userroll = bundle.getString("roll");
        username = bundle.getString("name");

        connect = (Button)findViewById(R.id.connect);
        tutorid = (EditText)findViewById(R.id.tutorid);
        code = (EditText)findViewById(R.id.code);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Activity_Student.class);
                intent.putExtra("roll", userroll);
                intent.putExtra("name", username);
                intent.putExtra("tutorid", tutorid.getText().toString());
                intent.putExtra("code", code.getText().toString());
                startActivity(intent);
            }
        });
    }
}
