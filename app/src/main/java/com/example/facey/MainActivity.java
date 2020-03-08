package com.example.facey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import com.example.facey.config.Session;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView profileName;

    private MaterialButton takeAttendance,viewAttendance,logout;
    private FirebaseAuth mAuth;
    private  FirebaseUser currentUser;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        profileName =  findViewById(R.id.profileName);
        takeAttendance = findViewById(R.id.takeAttendence);
        viewAttendance = findViewById(R.id.viewAttendence);
        logout = findViewById(R.id.logout);

        if(currentUser != null)
            profileName.setText(Session.getName());

        takeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), AttendanceCaptureActivity.class));
            }
        });
        viewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), Report.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), LoginActivity.class));
            }
        });
    }
}
