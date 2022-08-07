package com.example.hostelmaintenance;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class GetLeaveData implements Serializable {

    @Exclude private String id;
    String Student_Email, Student_Enrollment, Student_Name, Student_Contact,
            Student_Course, Room_No, Finger_No,
            Father_Name, Father_Contact, No_of_Days,
            Leave_Reason, Leave_Address, Leave_From, Leave_to;

    int Verified_CC, Verified_HOD, Verified_HW;

    public GetLeaveData() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GetLeaveData(String student_Email, String student_Enrollment, String student_Name, String student_Contact, String student_Course, String room_No, String finger_No, String father_Name, String father_Contact, String no_of_Days, String leave_Reason, String leave_Address, String leave_From, String leave_to, int verified_CC, int verified_HOD, int verified_HW) {
        Student_Email = student_Email;
        Student_Enrollment = student_Enrollment;
        Student_Name = student_Name;
        Student_Contact = student_Contact;
        Student_Course = student_Course;
        Room_No = room_No;
        Finger_No = finger_No;
        Father_Name = father_Name;
        Father_Contact = father_Contact;
        No_of_Days = no_of_Days;
        Leave_Reason = leave_Reason;
        Leave_Address = leave_Address;
        Leave_From = leave_From;
        Leave_to = leave_to;
        Verified_CC = verified_CC;
        Verified_HOD = verified_HOD;
        Verified_HW = verified_HW;
    }

    protected GetLeaveData(Parcel in) {
        Student_Email = in.readString();
        Student_Enrollment = in.readString();
        Student_Name = in.readString();
        Student_Contact = in.readString();
        Student_Course = in.readString();
        Room_No = in.readString();
        Finger_No = in.readString();
        Father_Name = in.readString();
        Father_Contact = in.readString();
        No_of_Days = in.readString();
        Leave_Reason = in.readString();
        Leave_Address = in.readString();
        Leave_From = in.readString();
        Leave_to = in.readString();
        Verified_CC = in.readInt();
        Verified_HOD = in.readInt();
        Verified_HW = in.readInt();
    }


    public String getStudent_Email() {
        return Student_Email;
    }

    public void setStudent_Email(String student_Email) {
        Student_Email = student_Email;
    }

    public String getStudent_Enrollment() {
        return Student_Enrollment;
    }

    public void setStudent_Enrollment(String student_Enrollment) {
        Student_Enrollment = student_Enrollment;
    }

    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        Student_Name = student_Name;
    }

    public String getStudent_Contact() {
        return Student_Contact;
    }

    public void setStudent_Contact(String student_Contact) {
        Student_Contact = student_Contact;
    }

    public String getStudent_Course() {
        return Student_Course;
    }

    public void setStudent_Course(String student_Course) {
        Student_Course = student_Course;
    }

    public String getRoom_No() {
        return Room_No;
    }

    public void setRoom_No(String room_No) {
        Room_No = room_No;
    }

    public String getFinger_No() {
        return Finger_No;
    }

    public void setFinger_No(String finger_No) {
        Finger_No = finger_No;
    }

    public String getFather_Name() {
        return Father_Name;
    }

    public void setFather_Name(String father_Name) {
        Father_Name = father_Name;
    }

    public String getFather_Contact() {
        return Father_Contact;
    }

    public void setFather_Contact(String father_Contact) {
        Father_Contact = father_Contact;
    }

    public String getNo_of_Days() {
        return No_of_Days;
    }

    public void setNo_of_Days(String no_of_Days) {
        No_of_Days = no_of_Days;
    }

    public String getLeave_Reason() {
        return Leave_Reason;
    }

    public void setLeave_Reason(String leave_Reason) {
        Leave_Reason = leave_Reason;
    }

    public String getLeave_Address() {
        return Leave_Address;
    }

    public void setLeave_Address(String leave_Address) {
        Leave_Address = leave_Address;
    }

    public String getLeave_From() {
        return Leave_From;
    }

    public void setLeave_From(String leave_From) {
        Leave_From = leave_From;
    }

    public String getLeave_to() {
        return Leave_to;
    }

    public void setLeave_to(String leave_to) {
        Leave_to = leave_to;
    }

    public int getVerified_CC() {
        return Verified_CC;
    }

    public void setVerified_CC(int verified_CC) {
        Verified_CC = verified_CC;
    }

    public int getVerified_HOD() {
        return Verified_HOD;
    }

    public void setVerified_HOD(int verified_HOD) {
        Verified_HOD = verified_HOD;
    }

    public int getVerified_HW() {
        return Verified_HW;
    }

    public void setVerified_HW(int verified_HW) {
        Verified_HW = verified_HW;
    }

}