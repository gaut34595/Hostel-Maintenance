package com.example.hostelmaintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ComplaintRegistrationActivity extends AppCompatActivity {
    EditText name,title,roomNo,description;
    Button register_comp;
    Spinner hostel,comp_type;
    private FirebaseUser user;
    private FirebaseAuth auth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_registration);
        name=findViewById(R.id.complain_username);
        title=findViewById(R.id.complain_title);
        roomNo=findViewById(R.id.complain_roomNo);
        description=findViewById(R.id.complain_description);
        hostel=findViewById(R.id.add_complaint_hostel);
        comp_type=findViewById(R.id.add_complaint_type);
        register_comp=findViewById(R.id.complain_submit);
        auth =FirebaseAuth.getInstance();
        user =auth.getCurrentUser();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a",Locale.getDefault());


//        register_comp.setOnClickListener(e->{
//                    String user_id = user.getEmail();
//                    String std_name = name.getText().toString();
//                    String comp_title = title.getText().toString();
//                    String room_num = roomNo.getText().toString();
//
//                    if(TextUtils.isEmpty(std_name) && TextUtils.isEmpty(comp_title) &&TextUtils.isEmpty(room_num)){
//                        Toast.makeText(this, "Please fill all the required fields", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        HashMap<String ,Object> map = new HashMap<>();
//                        map.put("Email",user_id);
//                        map.put("Name" , std_name);
//                        map.put("Complaint_Title" , comp_title);
//                        map.put("Room_No" , room_num);
//
//                        FirebaseDatabase.getInstance().getReference()
//                                .child("Maintenance").child("Complaints").push().updateChildren(map);
//                        Toast.makeText(this, "Complaint Registered Successfully", Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(this,ComplaintActivity.class);
//                        startActivity(i);
//                        finish();
//                    }
//
//
//        });

        register_comp.setOnClickListener(e->{
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Submitting Complaint...");
            String user_email = user.getEmail();
            String user_name = name.getText().toString();
            String comp_title = title.getText().toString();
            String room_num = roomNo.getText().toString();
            String comp_desc = description.getText().toString();
            String hostel_type = hostel.getSelectedItem().toString();
            String compl_type = comp_type.getSelectedItem().toString();
            String comp_date = dateFormat.format(date);
            String time = timeFormat.format(date);
            int comp_status =0;

            FirebaseFirestore db= FirebaseFirestore.getInstance();

            if(TextUtils.isEmpty(user_email) && TextUtils.isEmpty(user_name) &&TextUtils.isEmpty(comp_title)
                    &&TextUtils.isEmpty(room_num)&&TextUtils.isEmpty(comp_desc)&&TextUtils.isEmpty(hostel_type)
                    &&TextUtils.isEmpty(compl_type)){
                Toast.makeText(this, "Please fill all the required fields", Toast.LENGTH_SHORT).show();
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
            else {
                HashMap<String ,Object> map = new HashMap<>();
                map.put("Email",user_email);
                map.put("Name" , user_name);
                map.put("Complaint_Title" , comp_title);
                map.put("Room_No" , room_num);
                map.put("Description" , comp_desc);
                map.put("Hostel" , hostel_type);
                map.put("Complaint_Type" , compl_type);
                map.put("Complaint_Time" , time);
                map.put("Complaint_Date",comp_date);
                map.put("Complaint_Status" , comp_status);


                db.collection("Complaints").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Toast.makeText(ComplaintRegistrationActivity.this, "Complaint Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), ComplaintActivity.class);
                            startActivity(i);
                            finish();

                        }
                    }
                });
            }


        });




    }
}