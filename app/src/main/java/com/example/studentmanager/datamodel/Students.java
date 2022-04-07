package com.example.studentmanager.datamodel;

import com.google.firebase.firestore.Exclude;

public class Students {

    private String id, studentId, name, fatherName, motherName, address, image, dob;

    public Students() {
    }

    public Students(String studentId, String name, String fatherName, String motherName, String address, String image, String dob) {
        this.studentId = studentId;
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.address = address;
        this.image = image;
        this.dob = dob;
    }

    public Students(String name, String fatherName, String motherName, String address, String image, String dob) {
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.address = address;
        this.image = image;
        this.dob = dob;
    }

    @Exclude
    public String getId(){
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
