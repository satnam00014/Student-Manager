package com.example.studentmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanager.datamodel.ClassOfSchool;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class CreateClass extends AppCompatActivity {

    private String userId;
    private boolean isCreateClickedDisabled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        // calling the action bar
        // showing the back button in action bar
        //onOptionsItemSelected is override to handle functionality
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //check if user is login or not
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null)
            this.userId = user.getUid();
        userId = "GAArz0vHmyRq3DG5gStDKIzIfKo2";//This line is added for testing only

        findViewById(R.id.cancel).setOnClickListener(view -> {
            this.finish();
        });

        findViewById(R.id.create_button).setOnClickListener(view -> {
            String className = ((EditText)findViewById(R.id.class_name)).getText().toString();
            String teacherName = ((EditText)findViewById(R.id.teacher_name)).getText().toString();
            Toast.makeText(getApplicationContext(),className + teacherName,Toast.LENGTH_LONG).show();
            if (!className.equals("") && !teacherName.equals("") ){
                checkDataToUpdate(className,teacherName);
            }
        });
    }

    @Override
    public void onUserInteraction() {
        if (getCurrentFocus() != null){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        super.onUserInteraction();
    }

    private void checkDataToUpdate(String className,String teacherName){
        if (isCreateClickedDisabled)
            return;
        isCreateClickedDisabled = true;
        FirebaseFirestore.getInstance().collection("Schools/"+this.userId+"/Classes").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        boolean exist = false;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ClassOfSchool classOfSchool = document.toObject(ClassOfSchool.class);
                            exist = classOfSchool.getName().equalsIgnoreCase(className);
                        }
                        if (!exist){ //if class with same name doesn't exit before
                            ClassOfSchool cos = new ClassOfSchool(className,teacherName);
                            FirebaseFirestore.getInstance().collection("Schools/"+this.userId+"/Classes")
                                    .add(cos)
                                    .addOnSuccessListener(documentReference -> {
                                        isCreateClickedDisabled = false;
                                        Toast.makeText(getApplicationContext(),"Class Created",Toast.LENGTH_LONG).show();
                                        this.finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        isCreateClickedDisabled = false;
                                        Toast.makeText(getApplicationContext(),"Error while creating class",Toast.LENGTH_LONG).show();
                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                    });
                        }else{
                            isCreateClickedDisabled = false;
                            Toast.makeText(getApplicationContext(),"Class already exit with same name",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        isCreateClickedDisabled = false;
                    }
                });
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}