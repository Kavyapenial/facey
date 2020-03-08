package com.example.facey;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.facey.config.DataManager;
import com.example.facey.interfaces.RetrofitCallBack;
import com.example.facey.models.Batch;
import com.example.facey.models.Branch;
import com.example.facey.models.Subject;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AttendanceCaptureActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView  imageView;
    private AutoCompleteTextView branchAutoCompleteTextView,batchAutoCompleteTextView, subjectAutoCompleteTextView;
    private TextInputLayout branchTextInputLayout, batchTextInputLayout, subjeTextInputLayout;
    private Button captureButton;

    private ArrayList<Branch> branches;
    private ArrayList<Batch> batches;
    private ArrayList<Subject> subjects;
    private ArrayAdapter<String> branchAdapter;
    private ArrayAdapter<String> batchAdapter;
    private ArrayAdapter<String> subjectAdapter;

    private int batchId = 0, subjectId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_capture);

        captureButton = findViewById(R.id.captureButton);
        branchAutoCompleteTextView =  findViewById(R.id.branch);
        batchAutoCompleteTextView =  findViewById(R.id.batch);
        subjectAutoCompleteTextView =  findViewById(R.id.subject);

        batchTextInputLayout = findViewById(R.id.batch_container);
        branchTextInputLayout = findViewById(R.id.branch_container);
        subjeTextInputLayout = findViewById(R.id.subject_container);

        branchAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item);
        batchAdapter= new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item);
        subjectAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item);

        branchAutoCompleteTextView.setAdapter(branchAdapter);
        batchAutoCompleteTextView.setAdapter(batchAdapter);
        subjectAutoCompleteTextView.setAdapter(subjectAdapter);

        batchTextInputLayout.setEnabled(false);
        subjeTextInputLayout.setEnabled(false);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subjectId != 0 && batchId != 0){
                    dispatchTakePictureIntent();
                }
            }
        });


        branchAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                batchTextInputLayout.setEnabled(false);
                subjeTextInputLayout.setEnabled(false);

                getSubjects(branches.get(position).getId());
                getBatches(branches.get(position).getId());
                branchTextInputLayout.clearFocus();

                // Clear the previously selected value
                batchAutoCompleteTextView.setText("", false);
                subjectAutoCompleteTextView.setText("", false);

            }
        });

        batchAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                batchTextInputLayout.clearFocus();
                batchId = batches.get(position).getId();
            }
        });

        subjectAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subjeTextInputLayout.clearFocus();
                subjectId = subjects.get(position).getId();
            }
        });

        getBranchs();
    }


    private void getBranchs(){

        DataManager.getDataManager().getBranchs(new RetrofitCallBack<ArrayList<Branch>>() {
            @Override
            public void Success(ArrayList<Branch> data) {
                branches = data;
                batchAdapter.clear();
                for(int i =0; i< branches.size(); i++)
                    branchAdapter.add(branches.get(i).getName());
            }

            @Override
            public void Failure(String error) {

            }
        });

    }


    private void getSubjects(int branchId){
        DataManager.getDataManager().getSubjects(branchId, new RetrofitCallBack<ArrayList<Subject>>() {
            @Override
            public void Success(ArrayList<Subject> data) {
                subjects = data;
                subjectAdapter.clear();
                if(data.size() == 0)
                    subjeTextInputLayout.setEnabled(false);
                else
                    subjeTextInputLayout.setEnabled(true);
                for (int i=0; i< subjects.size(); i++)
                    subjectAdapter.add(subjects.get(i).getSubjectName());
            }

            @Override
            public void Failure(String error) {

            }
        });
    }


    private void getBatches(int branchId){
        DataManager.getDataManager().getBatches(branchId, new RetrofitCallBack<ArrayList<Batch>>() {
            @Override
            public void Success(ArrayList<Batch> data) {
                batches = data;
                batchAdapter.clear();
                if(data.size() == 0)
                    batchTextInputLayout.setEnabled(false);
                else
                    batchTextInputLayout.setEnabled(true);
                for (int i=0; i< batches.size(); i++)
                    batchAdapter.add(batches.get(i).getYear()+"");
            }

            @Override
            public void Failure(String error) {

            }
        });
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imageView.setImageBitmap(imageBitmap);
        }
    }
}
