package com.example.studentmanager.datamodel;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class ClassOfSchool {

    private String name;
    private String documentId;
    private ArrayList<String> students = new ArrayList<>();
    private String teacher;

    public ClassOfSchool() {
        name = "";
        documentId = "";
        teacher = "";
    }

    public ClassOfSchool(String name, String teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public ClassOfSchool(String name, String documentId, ArrayList<String> students, String teacher) {
        this.name = name;
        this.documentId = documentId;
        this.students = students;
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public String getDocumentId(){
        return documentId;
    }

    public void setDocumentId(String documentId){
        this.documentId = documentId;
    }

    public ArrayList<String> getStudents() {
        return students;
    }


    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
