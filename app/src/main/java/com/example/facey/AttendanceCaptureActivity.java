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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class AttendanceCaptureActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView  imageView;
    private AutoCompleteTextView branchAutoCompleteTextView,batchAutoCompleteTextView, subjectAutoCompleteTextView;


    private Button captureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_capture);

        captureButton = findViewById(R.id.captureButton);
        branchAutoCompleteTextView =  findViewById(R.id.branch);
        batchAutoCompleteTextView =  findViewById(R.id.batch);
        subjectAutoCompleteTextView =  findViewById(R.id.subject);

        final ArrayAdapter<String> branchAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        branchAdapter.add("CSE");
        branchAdapter.add("MECH");
        branchAdapter.add("EEE");

        final ArrayAdapter<String> batchAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        batchAdapter.add("Batch 1");
        batchAdapter.add("Batch 2");
        batchAdapter.add("Batch 3");
        batchAdapter.add("Batch 4");

        final ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        subjectAdapter.add("Sub 1");
        subjectAdapter.add("Sub 2");
        subjectAdapter.add("Sub 3");

        branchAutoCompleteTextView.setAdapter(branchAdapter);
        batchAutoCompleteTextView.setAdapter(batchAdapter);
        subjectAutoCompleteTextView.setAdapter(subjectAdapter);

        batchAutoCompleteTextView.setEnabled(false);
        subjectAutoCompleteTextView.setEnabled(false);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        branchAutoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), branchAdapter.getItem(position), Toast.LENGTH_LONG).show();
                batchAutoCompleteTextView.setEnabled(true);
                subjectAutoCompleteTextView.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        batchAutoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), batchAdapter.getItem(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subjectAutoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), subjectAdapter.getItem(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
