package com.example.facey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facey.config.DataManager;
import com.example.facey.config.Session;
import com.example.facey.interfaces.RetrofitCallBack;
import com.example.facey.models.Auth;
import com.example.facey.models.ResponseResult;
import com.example.facey.models.Student;
import com.example.facey.models.StudentResult;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class AttendanceViewActivity extends AppCompatActivity {

    private RecyclerView studentRecyclerView;
    private MaterialButton confirmButton;
    private StudentAdapter adapter;
    private StudentAdapter.StudentAdapterInterface studentAdapterInterface;
    ArrayList<Student> students;
    ArrayList finalStudentIds;

    int batchId=0, subjectId = 0, hour = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_view);

        StudentResult studentResult = (StudentResult)  getIntent().getSerializableExtra("students");
        subjectId = getIntent().getIntExtra("subject", 0);
        batchId = getIntent().getIntExtra("batch", 0);;
        hour = getIntent().getIntExtra("hour", 0);

        if(studentResult != null)
            students =  studentResult.getStudents();
        else
            students =  new ArrayList();


        studentRecyclerView = findViewById(R.id.studentRecyclerView);
        confirmButton = findViewById(R.id.confirm_attendance);

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

        studentRecyclerView.setAdapter(adapter);
        studentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentRecyclerView.setHasFixedSize(false);



        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getDataManager().confirmAttendance(getParams(batchId, subjectId, hour, finalStudentIds), new RetrofitCallBack<ResponseResult>() {
                    @Override
                    public void Success(ResponseResult data) {
                        Toast.makeText(getApplicationContext(), "Successfully Marked attendance",Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void Failure(String error) {
                        Toast.makeText(getApplicationContext(), "Something went wrong please try again",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private HashMap<String, String> getParams(int batch, int subject, int hour, ArrayList studentIds) {
        JSONArray array =  new JSONArray(studentIds);
        String json = new Gson().toJson(studentIds);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("batch", batch+"");
        hashMap.put("subject", subject+"");
        hashMap.put("hour", hour+"");
        hashMap.put("student_ids", studentIds.toString());

        return hashMap;
    }

}
