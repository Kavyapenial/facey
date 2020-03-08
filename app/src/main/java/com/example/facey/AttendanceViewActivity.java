package com.example.facey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facey.config.Session;
import com.example.facey.models.Student;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AttendanceViewActivity extends AppCompatActivity {

    private RecyclerView studentRecyclerView;
    private StudentAdapter adapter;
    private StudentAdapter.StudentAdapterInterface studentAdapterInterface;
    ArrayList<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_view);


        students =  new ArrayList();

        Student student;
        student = new Student();
        student.setName("Kavya");

        students.add(student);

        student = new Student();
        student.setName("Anjna");

        students.add(student);

        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student); student = new Student();
        student.setName("Anjna");

        students.add(student); student = new Student();
        student.setName("Anjna");

        students.add(student);

        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student); student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);
        student = new Student();
        student.setName("Anjna");

        students.add(student);







        studentAdapterInterface = new StudentAdapter.StudentAdapterInterface() {
            @Override
            public void onCheckedChangeListener(Student student, boolean isChecked) {
                Toast.makeText(getApplicationContext(), "RadioButton", Toast.LENGTH_LONG).show();
            }
        };



        adapter = new StudentAdapter(this, students, studentAdapterInterface);
        studentRecyclerView = findViewById(R.id.studentRecyclerView);

        studentRecyclerView.setAdapter(adapter);
        studentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentRecyclerView.setHasFixedSize(false);


    }
}
