package com.example.studentmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.example.studentmanager.recycleradapters.ClassesRecyclerAdapter;

public class ClassList extends AppCompatActivity {
    //reference for recyclerView and adapter for that
    private RecyclerView recyclerView;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_class);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ClassesRecyclerAdapter(this));
    }

    @Override
    public void onUserInteraction() {
        if (getCurrentFocus() != null){
            inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        super.onUserInteraction();
    }
}