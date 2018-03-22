package com.kodexlabs.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by 1505560 on 04-Nov-17.
 */

public class Activity_Choice extends AppCompatActivity {

    Button professor, student;

    String username, userroll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        Bundle bundle = getIntent().getExtras();
        userroll = bundle.getString("roll");
        username = bundle.getString("name");

        professor = (Button)findViewById(R.id.professor);
        student = (Button)findViewById(R.id.student);

        professor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Activity_Server.class);
                startActivity(intent);
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Activity_Client.class);
                intent.putExtra("roll", userroll);
                intent.putExtra("name", username);
                startActivity(intent);
            }
        });
    }
}
