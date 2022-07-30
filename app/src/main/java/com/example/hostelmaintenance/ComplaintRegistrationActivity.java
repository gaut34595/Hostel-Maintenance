package com.example.hostelmaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ComplaintRegistrationActivity extends AppCompatActivity {
    EditText name,title,roomNo;
    Button register_comp;
    private FirebaseUser user;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_registration);
        name=findViewById(R.id.complain_username);
        title=findViewById(R.id.complain_title);
        roomNo=findViewById(R.id.room_no);
        register_comp=findViewById(R.id.compl_submit);
        auth =FirebaseAuth.getInstance();
        user =auth.getCurrentUser();

        register_comp.setOnClickListener(e->{
                    String user_id = user.getEmail();
                    String std_name = name.getText().toString();
                    String comp_title = title.getText().toString();
                    String room_num = roomNo.getText().toString();

                    if(TextUtils.isEmpty(std_name) && TextUtils.isEmpty(comp_title) &&TextUtils.isEmpty(room_num)){
                        Toast.makeText(this, "Please fill all the required fields", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        HashMap<String ,Object> map = new HashMap<>();
                        map.put("Email",user_id);
                        map.put("Name" , std_name);
                        map.put("Complaint_Title" , comp_title);
                        map.put("Room_No" , room_num);

                        FirebaseDatabase.getInstance().getReference()
                                .child("Maintenance").child("Complaints").push().updateChildren(map);
                        Toast.makeText(this, "Complaint Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this,ComplaintActivity.class);
                        startActivity(i);
                        finish();
                    }


        });


    }
}