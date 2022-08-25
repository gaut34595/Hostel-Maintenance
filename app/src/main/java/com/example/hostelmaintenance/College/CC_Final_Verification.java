package com.example.hostelmaintenance.College;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class CC_Final_Verification extends AppCompatActivity {
    String stud_enroll,stud_name,stud_cont,stud_course,stud_fath,fath_con
    ,leave_from,leave_to,num_days,reason,leave_add,stud_email,room_no,finger;
    String imageurl;
    int cc, hod, hw;
    EditText stud_enroll_text,stud_name_text,stud_cont_text,stud_course_text,stud_fath_text,fath_con_text
            ,num_days_text,reason_text,leave_add_text;
    Button accept,reject;
    ImageView userimage;
    private FirebaseFirestore db;
    private GetLeaveData leave_data;
    EditText leave_from_text,leave_to_text;
    DatePickerDialog picker;
    private static final int REQUEST_CALL = 1;
    ImageButton callfather,callstudent;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrollView= findViewById(R.id.ccscroll);
        setContentView(R.layout.activity_cc_final_verification);
        Intent intent = getIntent();
        stud_enroll_text = findViewById(R.id.verify_student_enrol);
        stud_name_text = findViewById(R.id.verify_student_name);
        stud_cont_text = findViewById(R.id.verify_stud_contact);
        stud_course_text = findViewById(R.id.verify_stud_program);
        stud_fath_text = findViewById(R.id.verify_stud_father);
        fath_con_text = findViewById(R.id.verify_student_father_cont);
        leave_from_text = findViewById(R.id.verify_leavefrom);
        leave_to_text = findViewById(R.id.verify_leaveto);
        num_days_text = findViewById(R.id.verify_num_days);
        reason_text = findViewById(R.id.verify_reason_leave);
        leave_add_text = findViewById(R.id.verify_add_leave);
        accept = findViewById(R.id.button_accept);
        reject = findViewById(R.id.button_reject);
        callfather=findViewById(R.id.call_father);
        callstudent=findViewById(R.id.call_stud);
        userimage= findViewById(R.id.ccuser_image);

        leave_data = (GetLeaveData) intent.getSerializableExtra("Leave_Item");
        db=FirebaseFirestore.getInstance();
        stud_email = leave_data.getStudent_Email();
        stud_enroll = leave_data.getStudent_Enrollment();
        stud_name = leave_data.getStudent_Name();
        stud_cont = leave_data.getStudent_Contact();
        stud_course = leave_data.getStudent_Course();
        stud_fath = leave_data.getFather_Name();
        fath_con = leave_data.getFather_Contact();
        leave_from = leave_data.getLeave_From();
        leave_to = leave_data.getLeave_to();
        num_days = leave_data.getNo_of_Days();
        reason = leave_data.getLeave_Reason();
        leave_add = leave_data.getLeave_Address();
        room_no= leave_data.getRoom_No();
        finger = leave_data.getFinger_No();
        cc=leave_data.getVerified_CC();
        hod= leave_data.getVerified_HOD();
        hw= leave_data.getVerified_HW();
        imageurl= leave_data.getImageLink();

        Picasso.get().load(imageurl).into(userimage);
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
                String leavefrom= leave_from_text.getText().toString();
                String leaveto = leave_to_text.getText().toString();
                String numdays= num_days_text.getText().toString();
      DocumentReference dd= db.collection("Student_Leaves").document(leave_data.getId());
                HashMap<String,Object> map = new HashMap<>();
                map.put("Verified_CC",1);
                map.put("Leave_From",leavefrom);
                map.put("Leave_to",leaveto);
                map.put("No_of_Days",numdays);
                dd.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(CC_Final_Verification.this, "Forwarded to HOD Sir", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(CC_Final_Verification.this,CC_Verify_Leave.class);
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
        reject.setOnClickListener(e->{
            db.collection("Student_Leaves").document(leave_data.getId())
                    .update("Verified_CC",-1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(CC_Final_Verification.this, "Leave Rejected", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(CC_Final_Verification.this,CC_Verify_Leave.class);
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

        //for changing date
        leave_from_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Date Picker

                picker = new DatePickerDialog(CC_Final_Verification.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        leave_from_text.setText(i2 + "/" + (i1+1) + "/" + i);
                    }
                },year,month,day);
                picker.show();
            }
        });

        leave_to_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Date Picker

                picker = new DatePickerDialog(CC_Final_Verification.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        leave_to_text.setText(i2 + "/" + (i1+1) + "/" + i);
                    }
                },year,month,day);
                picker.show();
            }
        });
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        leave_to_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String leavefromdate = leave_from_text.getText().toString();
                String leavedateto = leave_to_text.getText().toString();
                try{
                    Date d1 = dateFormat.parse(leavefromdate);
                    Date d2 = dateFormat.parse(leavedateto);
                    long diff = d2.getTime() - d1.getTime();
                    if((int) TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS)==0){
                        num_days_text.setText("1");
                    }
                    else{
                        num_days_text.setText((int)TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS)+ "");
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        
        //making calls
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
                    checkSelfPermission(CC_Final_Verification.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(CC_Final_Verification.this,new String[]{
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
                    checkSelfPermission(CC_Final_Verification.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(CC_Final_Verification.this,new String[]{
                        Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{
                String dial1 = "tel:" +"+91" +stud_cont;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial1)));
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