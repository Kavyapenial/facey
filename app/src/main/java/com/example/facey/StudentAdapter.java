package com.example.facey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facey.models.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {


    ArrayList<Student> students;
    Context mContext;
    StudentAdapterInterface studentAdapterInterface;


    public  StudentAdapter(Context mContext, ArrayList<Student> students, StudentAdapterInterface studentAdapterInterface){
        this.mContext =  mContext;
        this.students =  students;
        this.studentAdapterInterface = studentAdapterInterface;
    }

    public interface  StudentAdapterInterface {
        public void  onCheckedChangeListener(Student student, boolean isChecked);
    }


    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.student_info_layout, parent, false);
        StudentViewHolder viewHolder = new StudentViewHolder(listItem);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        final Student student =  students.get(position);
        holder.studName.setText(student.getName());
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                studentAdapterInterface.onCheckedChangeListener(student, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }


    public  class StudentViewHolder extends RecyclerView.ViewHolder {
        ImageView studPhoto;
        TextView studName;
        CheckBox radioButton;

        public  StudentViewHolder(View itemView) {
            super(itemView);

            studPhoto =  itemView.findViewById(R.id.student_photo);
            studName =  itemView.findViewById(R.id.student_name);
            radioButton = itemView.findViewById(R.id.radio_checkbox);
        }
    }
}
