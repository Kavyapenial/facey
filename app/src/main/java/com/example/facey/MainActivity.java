package com.example.facey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView profileName;
    private MaterialButton takeAttendance;
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

        if(currentUser != null)
            profileName.setText(currentUser.getDisplayName());

        takeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), AttendanceCaptureActivity.class));
            }
        });
    }
}
