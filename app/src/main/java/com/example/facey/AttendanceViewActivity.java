package com.example.facey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facey.config.Session;
import com.example.facey.models.Student;
import com.example.facey.models.StudentResult;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AttendanceViewActivity extends AppCompatActivity {

    private RecyclerView studentRecyclerView;
    private StudentAdapter adapter;
    private StudentAdapter.StudentAdapterInterface studentAdapterInterface;
    ArrayList<Student> students;
    ArrayList finalStudentIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_view);

        StudentResult studentResult = (StudentResult)  getIntent().getSerializableExtra("students");
        if(studentResult != null)
            students =  studentResult.getStudents();
        else
            students =  new ArrayList();

        finalStudentIds = new ArrayList();

        studentAdapterInterface = new StudentAdapter.StudentAdapterInterface() {
            @Override
            public void onCheckedChangeListener(Student student, boolean isChecked) {
                Log.d("ViewAct", finalStudentIds.toString());
                if(isChecked){
                    finalStudentIds.add(student.getId());
                }else{
                    Log.d("ViewAct", finalStudentIds.toString());
                    int index = finalStudentIds.indexOf(student.getId());
                    if(index != -1)
                        finalStudentIds.remove(index);
                }
            }
        };



        adapter = new StudentAdapter(this, students, studentAdapterInterface);
        studentRecyclerView = findViewById(R.id.studentRecyclerView);

        studentRecyclerView.setAdapter(adapter);
        studentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentRecyclerView.setHasFixedSize(false);


    }
}
