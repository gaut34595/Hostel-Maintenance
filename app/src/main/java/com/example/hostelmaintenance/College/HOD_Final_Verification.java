package com.example.hostelmaintenance.College;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hostelmaintenance.GetLeaveData;
import com.example.hostelmaintenance.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class HOD_Final_Verification extends AppCompatActivity {
    Button accept,reject;
    EditText stud_enroll_text,stud_name_text,stud_cont_text,stud_course_text,stud_fath_text,fath_con_text
            ,num_days_text,reason_text,leave_add_text,leave_from_text,leave_to_text;
    ImageButton callfather,callstudent;
    FirebaseFirestore db;
    private static final int REQUEST_CALL = 1;
    String stud_enroll,stud_name,stud_cont,stud_course,stud_fath,fath_con
            ,leave_from,leave_to,num_days,reason,leave_add,stud_email,room_no,finger;
    int cc, hod, hw;

    private GetLeaveData grant_leave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_final_verification);
        stud_enroll_text = findViewById(R.id.grant_student_enrol);
        stud_name_text = findViewById(R.id.grant_student_name);
        stud_cont_text = findViewById(R.id.grant_stud_contact);
        stud_course_text = findViewById(R.id.grant_stud_program);
        stud_fath_text = findViewById(R.id.grant_stud_father);
        fath_con_text = findViewById(R.id.grant_student_father_cont);
        leave_from_text = findViewById(R.id.grant_leavefrom);
        leave_to_text = findViewById(R.id.grant_leaveto);
        num_days_text = findViewById(R.id.grant_num_days);
        reason_text = findViewById(R.id.grant_reason_leave);
        leave_add_text = findViewById(R.id.grant_add_leave);
        accept = findViewById(R.id.button_accept);
        reject = findViewById(R.id.button_reject);
        callfather=findViewById(R.id.grant_call_father);
        callstudent=findViewById(R.id.grant_call_stud);


        grant_leave= (GetLeaveData) getIntent().getSerializableExtra("Grant_Item");
        db=FirebaseFirestore.getInstance();

        stud_email = grant_leave.getStudent_Email();
        stud_enroll = grant_leave.getStudent_Enrollment();
        stud_name = grant_leave.getStudent_Name();
        stud_cont = grant_leave.getStudent_Contact();
        stud_course = grant_leave.getStudent_Course();
        stud_fath = grant_leave.getFather_Name();
        fath_con = grant_leave.getFather_Contact();
        leave_from = grant_leave.getLeave_From();
        leave_to = grant_leave.getLeave_to();
        num_days = grant_leave.getNo_of_Days();
        reason = grant_leave.getLeave_Reason();
        leave_add = grant_leave.getLeave_Reason();
        room_no= grant_leave.getRoom_No();
        finger = grant_leave.getFinger_No();
        cc=grant_leave.getVerified_CC();
        hod= grant_leave.getVerified_HOD();
        hw= grant_leave.getVerified_HW();

        stud_enroll_text.setText(stud_enroll);
        stud_name_text.setText(stud_name);
        stud_cont_text.setText(stud_cont);
        stud_course_text.setText(stud_course);
        stud_fath_text.setText(stud_fath);
        fath_con_text.setText(fath_con);
        leave_from_text.setText(leave_from);
        leave_to_text.setText(leave_to);
        num_days_text.setText(num_days);
        reason_text.setText(reason);
        leave_add_text.setText(leave_add);

        accept.setOnClickListener(e->{
            DocumentReference dd = db.collection("Student_Leaves").document(grant_leave.getId());
            HashMap<String,Object> map = new HashMap<>();
            map.put("Verified_HOD",1);
            dd.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(HOD_Final_Verification.this, "Leave Granted Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(HOD_Final_Verification.this,HOD_Grant_Leave.class);
                    startActivity(i);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(">>>>>>>>>>>>>>>>>>>>>", e.getMessage());
                }
            });
        });

        callfather.setOnClickListener(e->{
            makePhoneCall();
        });
        callstudent.setOnClickListener(e->{
            makePhoneCall1();
        });

    }
    private void makePhoneCall() {
        if(fath_con.trim().length()>0){
            if(ContextCompat.
                    checkSelfPermission(HOD_Final_Verification.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(HOD_Final_Verification.this,new String[]{
                        Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{
                String dial = "tel:" +"+91" +fath_con;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText(this, "Check Phone Number", Toast.LENGTH_SHORT).show();
        }
    }
    private void makePhoneCall1() {
        if(stud_cont.trim().length()>0){
            if(ContextCompat.
                    checkSelfPermission(HOD_Final_Verification.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(HOD_Final_Verification.this,new String[]{
                        Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{
                String dial = "tel:" +"+91" +stud_cont;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText(this, "Check Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}