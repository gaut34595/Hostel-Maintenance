package com.example.hostelmaintenance.Hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.net.URI;
import java.math.BigDecimal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hostelmaintenance.GetLeaveData;
import com.example.hostelmaintenance.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FinalTransactionActivity extends AppCompatActivity {
    String stud_enroll,stud_name,stud_cont,stud_course,stud_fath,fath_con
            ,leave_from,leave_to,num_days,reason,leave_add,stud_email,room_no,finger;
    String imageurl;
    ImageView userimage;
    ScrollView scrollView;
    String url = "https://api.twilio.com/2010-04-01/Accounts/ACf0340978828bc1b671943f92ec12c0b3/Messages.json";
    int cc, hod, hw;
    EditText stud_enroll_text,stud_name_text,roomNo,fingerNo,stud_course_text,stud_fath_text,fath_con_text
            ,num_days_text,reason_text,leave_add_text;
    EditText leave_from_text,leave_to_text;
    Button accept;
    private FirebaseFirestore db;
    private GetLeaveData t_data;
    private  static final int REQUEST_CALL = 1;
    ImageButton callfather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrollView= findViewById(R.id.hostelscroll);
        setContentView(R.layout.activity_final_transaction);
        Intent i = getIntent();
        userimage = findViewById(R.id.hosuser_image);
        stud_enroll_text = findViewById(R.id.transact_student_enrol);
        stud_name_text = findViewById(R.id.transact_student_name);
        stud_course_text = findViewById(R.id.transact_stud_program);
        stud_fath_text = findViewById(R.id.transact_stud_father);
        fath_con_text = findViewById(R.id.transact_student_father_cont);
        leave_from_text = findViewById(R.id.transact_leavefrom);
        leave_to_text = findViewById(R.id.transact_leaveto);
        num_days_text = findViewById(R.id.transact_num_days);
        reason_text = findViewById(R.id.transact_reason_leave);
        leave_add_text = findViewById(R.id.transact_add_leave);
        accept = findViewById(R.id.button_accept);
        callfather=findViewById(R.id.transact_call_father);
        roomNo = findViewById(R.id.transact_roomNo);
        fingerNo= findViewById(R.id.transact_finger);

        t_data= (GetLeaveData) i.getSerializableExtra("Transact_Item");
        db= FirebaseFirestore.getInstance();
        stud_email = t_data.getStudent_Email();
        stud_enroll = t_data.getStudent_Enrollment();
        stud_name = t_data.getStudent_Name();
        stud_cont = t_data.getStudent_Contact();
        stud_course = t_data.getStudent_Course();
        stud_fath = t_data.getFather_Name();
        fath_con = t_data.getFather_Contact();
        leave_from = t_data.getLeave_From();
        leave_to = t_data.getLeave_to();
        num_days = t_data.getNo_of_Days();
        reason = t_data.getLeave_Reason();
        leave_add = t_data.getLeave_Reason();
        room_no= t_data.getRoom_No();
        finger = t_data.getFinger_No();
        cc=t_data.getVerified_CC();
        hod= t_data.getVerified_HOD();
        hw= t_data.getVerified_HW();
        imageurl= t_data.getImageLink();

        Picasso.get().load(imageurl).into(userimage);
        stud_enroll_text.setText(stud_enroll);
        stud_name_text.setText(stud_name);
        stud_course_text.setText(stud_course);
        stud_fath_text.setText(stud_fath);
        fath_con_text.setText(fath_con);
        leave_from_text.setText(leave_from);
        leave_to_text.setText(leave_to);
        num_days_text.setText(num_days);
        roomNo.setText(room_no);
        fingerNo.setText(finger);
        reason_text.setText(reason);
        leave_add_text.setText(leave_add);

        accept.setOnClickListener(e->{

            DocumentReference dd = db.collection("Student_Leaves").document(t_data.getId());
            HashMap<String,Object> map = new HashMap<>();
            map.put("QRCode",getQrcode());
            map.put("Verified_HW",1);
            dd.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    sendSms();
                    sendWhatsappSms();
                    Toast.makeText(FinalTransactionActivity.this, "Leave Granted Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(FinalTransactionActivity.this,LeaveTransactActivity.class);
                    startActivity(i);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("----->>>>>>>",e.getMessage());
                }
            });
        });
        callfather.setOnClickListener(e->{
            makePhoneCall();
        });

    }

    public String getQrcode(){
      return UUID.randomUUID().toString();
    }

    private void sendWhatsappSms() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(">>>>responsewhatsapp",response+"");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(">>>>errwhatsapp",error.getMessage()+"");

            }
        }) {
            protected Map<String, String> getParams() {

                Map<String,String> params = new HashMap<String, String>();
                params.put("To","whatsapp:+918439046950");
                params.put("From","whatsapp:+14155238886");
                params.put("Body","Respected Sir, Your Ward " + stud_name + " has been checked out from Hostel from " + leave_from + " to " + leave_to + " Thank You-TMU HOSTEL");
                return params;
            }
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Basic QUNmMDM0MDk3ODgyOGJjMWI2NzE5NDNmOTJlYzEyYzBiMzo3ZTUyODQ4ZDgyNGQzOWFhMmUyNzU4MWZhOTIyZDVkYw==");
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;


            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void sendSms() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(">>>>response",response+"");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(">>>>err",error.getMessage()+"");

            }
        }) {
            protected Map<String, String> getParams() {

                Map<String,String> params = new HashMap<String, String>();
                params.put("To","+918439046950");
                params.put("From","+15802036610");
                params.put("MessagingServiceSid", "MG09b2716158c1a5a75c8598a0da3d546e");
                params.put("Body","Respected Sir, Your Ward " + stud_name + " has been checked out from Hostel from " + leave_from + " to " + leave_to + " Thank You-TMU HOSTEL");
                return params;
            }
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Basic QUNmMDM0MDk3ODgyOGJjMWI2NzE5NDNmOTJlYzEyYzBiMzo3ZTUyODQ4ZDgyNGQzOWFhMmUyNzU4MWZhOTIyZDVkYw==");
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;


            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void makePhoneCall() {
        if(fath_con.trim().length()>0){
            if(ContextCompat.
                    checkSelfPermission(FinalTransactionActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(FinalTransactionActivity.this,new String[]{
                        Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{
                String dial = "tel:" +"+91" +fath_con;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText(this, "Check Phone Number", Toast.LENGTH_SHORT).show();
        }
    }
}