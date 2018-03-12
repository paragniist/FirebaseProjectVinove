package com.example.vinove.firebaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AfterLogin extends AppCompatActivity {

    private Button createSession;
    private Button joinAsMember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        createSession = findViewById(R.id.createSession);
        joinAsMember = findViewById(R.id.joinAsMember);


        createSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateSessionActivity.class));

            }
        });

        joinAsMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), JoinActivity.class));

            }
        });

    }


}
