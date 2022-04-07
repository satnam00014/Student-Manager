package com.example.studentmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.studentmanager.datamodel.ClassOfSchool;
import com.example.studentmanager.datamodel.Students;
import com.example.studentmanager.recycleradapters.ClassesRecyclerAdapter;
import com.example.studentmanager.recycleradapters.StudentRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class StudentList extends AppCompatActivity {

    private String userId, classId, className, classTeacher;
    private final String TAG = "TAG:";
    private FirebaseFirestore db;
    private ArrayList<Students> studentList;

    StudentRecyclerAdapter studentRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        classId = getIntent().getStringExtra("classId");
        className = getIntent().getStringExtra("className");
        classTeacher = getIntent().getStringExtra("classTeacher");
        studentList = new ArrayList<>();
        this.setTitle(className + " - " + classTeacher);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null)
            this.userId = user.getUid();
        Log.d(TAG,"Student list START");
        userId = "GAArz0vHmyRq3DG5gStDKIzIfKo2";//This line is added for testing only
        setRecyclerView();
        db = FirebaseFirestore.getInstance();
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
        db.document("Schools/"+this.userId+"/Classes/"+classId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()){
                        ClassOfSchool classOfSchool = documentSnapshot.toObject(ClassOfSchool.class);
                        for (String studentId : classOfSchool.getStudents()){
                            Log.d(TAG,studentId + "Inside loop of listner");
                            addingStudent(studentId);
                        }
                    }else{
                        Log.d(TAG,"Document doesn't exist at all");
                        Toast.makeText(getApplicationContext(),"Document doesn't exit",Toast.LENGTH_LONG).show();
                    }



                });
    }

    private void addingStudent(String studentId){
        db.document("Schools/"+this.userId+"/Students/"+studentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                   if (documentSnapshot.exists()){
                       Students student = documentSnapshot.toObject(Students.class);
                       student.setDocumentId(documentSnapshot.getId());
                       Log.d(TAG,student.getDocumentId());
                       Log.d(TAG,student.getStudentId());
                       Log.d(TAG,student.getAddress());
                       Log.d(TAG,student.getDob());
                       Log.d(TAG,student.getName());
                       Log.d(TAG,student.getMotherName());
                       Log.d(TAG,student.getImage());
                       studentList.add(student);
                       if (studentRecyclerAdapter!=null)
                           studentRecyclerAdapter.updateData(studentList);
                   }
                });
    }
}