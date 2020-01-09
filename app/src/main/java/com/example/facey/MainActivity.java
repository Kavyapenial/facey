package com.example.facey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
<<<<<<< HEAD
import android.widget.ImageView;
=======
>>>>>>> bebf7f12d471eaf9be94d78faa240ae1779f741f
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView profileName;
    private  MaterialButton takeAttendance;
    private ImageView imageView;

    private FirebaseAuth mAuth;
    private  FirebaseUser currentUser;

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
