package com.example.studentmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.studentmanager.datamodel.ClassOfSchool;
import com.example.studentmanager.recycleradapters.ClassesRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ClassList extends AppCompatActivity {

    private String userId;
    private FirebaseFirestore db;
    private ClassesRecyclerAdapter classesRecyclerAdapter;
    private final String TAG = "TAG:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        this.setTitle("List of Classes");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null)
            this.userId = user.getUid();
        Log.d("TAG:","ACTIVITY START");
        userId = "GAArz0vHmyRq3DG5gStDKIzIfKo2";//This line is added for testing only
        setRecyclerView();
    }

    private void setRecyclerView() {
        //reference for recyclerView and adapter for that
        RecyclerView recyclerView = findViewById(R.id.recycler_view_class);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        classesRecyclerAdapter = new ClassesRecyclerAdapter(this);
        recyclerView.setAdapter(classesRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.classlist_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.class_search_menu);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public void onUserInteraction() {
        if (getCurrentFocus() != null){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        super.onUserInteraction();
    }


    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData(){
        db = FirebaseFirestore.getInstance();
        db.collection("Schools/"+this.userId+"/Classes").addSnapshotListener(this,(value, error) -> {
           if (error!=null){
               Toast.makeText(getApplicationContext(),"Error while getting data",Toast.LENGTH_LONG).show();
               Log.d(TAG,error.getLocalizedMessage());
               return;//we have to return if any error occured
           }
           ArrayList<ClassOfSchool> arrayList = new ArrayList<>();
           for (DocumentSnapshot snapshot : value){
               ClassOfSchool classOfSchool = snapshot.toObject(ClassOfSchool.class);
               Log.d(TAG,classOfSchool.getName());
               arrayList.add(classOfSchool);
           }

           if (classesRecyclerAdapter!=null)
               classesRecyclerAdapter.updateData(arrayList);
        });

        //Following is to get image of students
        db.document("Schools/"+this.userId).addSnapshotListener(this,(value, error) -> {
           if (error!=null){
               Toast.makeText(getApplicationContext(),"Error while getting Image",Toast.LENGTH_LONG).show();
               Log.d(TAG,error.getLocalizedMessage());
               return;//we have to return if any error occured
           }
           if (value.exists()){
               String url = value.getString("image");
               String name = value.getString("name");
               this.setTitle(name);
               if (classesRecyclerAdapter!=null)
                   classesRecyclerAdapter.updateImage(url);
           }
        });
    }
}