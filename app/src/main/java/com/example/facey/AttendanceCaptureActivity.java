package com.example.facey;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.facey.config.DataManager;
import com.example.facey.interfaces.RetrofitCallBack;
import com.example.facey.models.Batch;
import com.example.facey.models.Branch;
import com.example.facey.models.StudentResult;
import com.example.facey.models.Subject;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AttendanceCaptureActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView  imageView;
    private AutoCompleteTextView branchAutoCompleteTextView,batchAutoCompleteTextView, subjectAutoCompleteTextView;
    private TextInputLayout branchTextInputLayout, batchTextInputLayout, subjeTextInputLayout;
    private Button captureButton;
    private LinearLayout loader;

    private ArrayList<Branch> branches;
    private ArrayList<Batch> batches;
    private ArrayList<Subject> subjects;
    private ArrayAdapter<String> branchAdapter;
    private ArrayAdapter<String> batchAdapter;
    private ArrayAdapter<String> subjectAdapter;

    private int batchId = 0, subjectId = 0;
    private String currentPhotoPath;
    File photoFile = null;
    Uri photoURI;
    public static final int MY_PERMISSIONS_READ_EXTERNAL = 1122;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL = 2211;


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

        loader = findViewById(R.id.loader);

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
//                if(subjectId != 0 && batchId != 0){
//                    dispatchTakePictureIntent();
//                }
                dispatchTakePictureIntent();
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
        checkStoragePermisstion();
        checkWritePermission();
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
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap myImg = BitmapFactory.decodeFile(currentPhotoPath);
            Log.d("ImageFile", photoFile.getTotalSpace()+"");
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), photoFile);
            RequestBody batch =
                    RequestBody.create(MediaType.parse("text/plain"), batchId+"");


            MultipartBody.Part part = MultipartBody.Part.createFormData("capture_image", photoFile.getName(), requestFile);
            loader.setVisibility(View.VISIBLE);
            DataManager.getDataManager().getResults(batch, part, new RetrofitCallBack<StudentResult>() {
                @Override
                public void Success(StudentResult data) {
                    startActivity(new Intent(AttendanceCaptureActivity.this, AttendanceViewActivity.class)
                    .putExtra("students", data));
                    loader.setVisibility(View.GONE);
                }

                @Override
                public void Failure(String error) {

                }
            });
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public boolean checkStoragePermisstion()
    {

        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Read storage permission is necessary to read image captured !!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(AttendanceCaptureActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_READ_EXTERNAL);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_READ_EXTERNAL);
                }
                return false;

            } else {
                return true;
            }
        } else {
            return true;
        }

    }

    public boolean checkWritePermission()
    {

        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Read storage permission is necessary to read image captured !!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(AttendanceCaptureActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL);
                }
                return false;

            } else {
                return true;
            }
        } else {
            return true;
        }

    }

}

