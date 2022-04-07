package com.example.studentmanager.datamodel;

import com.google.firebase.firestore.Exclude;

public class ClassOfSchool {

    private String name;
    private String Id;
    private String[] students;
    private String teacher;

    public ClassOfSchool() {
        name = "";
        Id = "";
        students = new String[0];
        teacher = "";
    }

    public ClassOfSchool(String name, String id, String[] students, String teacher) {
        this.name = name;
        Id = id;
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
    public String getId() {
        return Id;
    }

    public String[] getStudents() {
        return students;
    }


    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
