package com.example.hostelmaintenance.College;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.hostelmaintenance.GetLeaveData;
import com.example.hostelmaintenance.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class HOD_Final_Verification extends AppCompatActivity {
    ScrollView scrollView;
    Button accept,reject;
    EditText stud_enroll_text,stud_name_text,stud_cont_text,stud_course_text,stud_fath_text,fath_con_text
            ,num_days_text,reason_text,leave_add_text,leave_from_text,leave_to_text;
    ImageButton callfather,callstudent;
    FirebaseFirestore db;
    String reject_string;
    String imageurl;
    ImageView imageView;
    private static final int REQUEST_CALL = 1;
    String stud_enroll,stud_name,stud_cont,stud_course,stud_fath,fath_con
            ,leave_from,leave_to,num_days,reason,leave_add,stud_email,room_no,finger,stud_dept,stud_college;
    int cc, hod, hw;
    AlertDialog dialog;
    private GetLeaveData grant_leave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrollView=findViewById(R.id.hodscroll);
        setContentView(R.layout.activity_hod_final_verification);
        stud_enroll_text = findViewById(R.id.grant_student_enrol);
        imageView= findViewById(R.id.hoduser_image);
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

        // Dialog box for rejection
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Reject");
        View v = getLayoutInflater().inflate(R.layout.custom_dialog,null);
        EditText reject_reason;
        Button reject_final,back;
        reject_final= v.findViewById(R.id.reject_final);
        back= v.findViewById(R.id.back);
        reject_reason=v.findViewById(R.id.reject_reason);

        reject_final.setOnClickListener(e->{
            if(reject_reason.getText().toString().isEmpty()){
                Toast.makeText(this, "Please fill the reason", Toast.LENGTH_SHORT).show();
            }else{
                reject_string= reject_reason.getText().toString();
                db.collection("Student_Leaves").document(grant_leave.getId())
                        .update("Verified_HOD",-1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        sendSimpleRejectMessage();
                                    }
                                });
                                t.start();
                                Toast.makeText(HOD_Final_Verification.this, "Leave Rejected", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(HOD_Final_Verification.this,CC_Verify_Leave.class);
                                startActivity(i);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(HOD_Final_Verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.dismiss();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        builder.setView(v);
        dialog = builder.create();

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
        leave_add = grant_leave.getLeave_Address();
        room_no= grant_leave.getRoom_No();
        finger = grant_leave.getFinger_No();
        cc=grant_leave.getVerified_CC();
        hod= grant_leave.getVerified_HOD();
        hw= grant_leave.getVerified_HW();
        imageurl= grant_leave.getImageLink();
        stud_dept= grant_leave.getStudent_Department();
        stud_college= grant_leave.getStudent_College();


        Picasso.get().load(imageurl).into(imageView);
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
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            sendSimpleMessage();
                        }
                    });
                    t.start();
                    Toast.makeText(HOD_Final_Verification.this, "Leave Granted Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(HOD_Final_Verification.this,HOD_Grant_Leave.class);
                    startActivity(i);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(HOD_Final_Verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
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
    public MessageResponse sendSimpleMessage() {
        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config("910b0661ca2d60fe37be52400140ec79-680bcd74-2a17c9ca")
                .createApi(MailgunMessagesApi.class);

        Message message = Message.builder()
                .from("TMU-HOSTEL <hostel@warden.tmuhostel.me>")
                .to(stud_email)
                .subject("Leave approved for " + leave_from + " " +"to" + " " + leave_to)
                .text("Hi " + stud_name + ",\n" + "We have received your leave request for these dates: " + leave_from + " to " +
                        leave_to  + " ("  +num_days +" days) " + ", for the reason " + reason+ ".\n" + "As of today we have accepted your leave.\n" + "All the very best for your future goals.\n\n"
                        + "Thanks,\n" + "Head Of Department, " + stud_dept + ".\n" + stud_college +",\n" + "Teerthanker Mahaveer University, Moradabad")
                .build();

        return mailgunMessagesApi.sendMessage("warden.tmuhostel.me", message);


    }
    public MessageResponse sendSimpleRejectMessage() {
        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config("910b0661ca2d60fe37be52400140ec79-680bcd74-2a17c9ca")
                .createApi(MailgunMessagesApi.class);

        Message message = Message.builder()
                .from("TMU-HOSTEL <hostel@warden.tmuhostel.me>")
                .to(stud_email)
                .subject("Leave rejected for " + leave_from + " " +"to" + " " + leave_to)
                .text("Hi " + stud_name + ",\n" + "We have received your leave request for these dates: " + leave_from + " to " +
                        leave_to + " (" +  num_days +" days) " + ", for the reason " + reason+ ".\n" + "As of today we have rejected your leave.\n\n" + "Reject Reason: " + reject_string +"\n"+ "All the very best for your future goals.\n\n"
                        + "Thanks,\n" + "Head Of Department, " + stud_dept + ".\n" + stud_college +",\n" + "Teerthanker Mahaveer University, Moradabad")
                .build();

        return mailgunMessagesApi.sendMessage("warden.tmuhostel.me", message);


    }
}