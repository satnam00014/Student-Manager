package com.example.studentmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.studentmanager.recycleradapters.ClassesRecyclerAdapter;
import com.example.studentmanager.recycleradapters.StudentRecyclerAdapter;

public class StudentList extends AppCompatActivity {

    private String classId, className, classTeacher;
    private final String TAG = "TAG:";

    StudentRecyclerAdapter studentRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        classId = getIntent().getStringExtra("classId");
        className = getIntent().getStringExtra("className");
        classTeacher = getIntent().getStringExtra("classTeacher");
        this.setTitle(className + " - " + classTeacher);
        setRecyclerView();
    }


    private void setRecyclerView() {
        //reference for recyclerView and adapter for that
        RecyclerView recyclerView = findViewById(R.id.recycler_view_student);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentRecyclerAdapter = new StudentRecyclerAdapter(this);
        recyclerView.setAdapter(studentRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,classId);
        Log.d(TAG,className);
        Log.d(TAG,classTeacher);
    }
}