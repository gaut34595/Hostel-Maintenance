package com.example.hostelmaintenance.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hostelmaintenance.Main.LoginActivity;
import com.example.hostelmaintenance.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class StudentDashboard extends AppCompatActivity {
    ImageButton ib1,ib2,ib3,ib4,ib5,ib6;
    private Button logout;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        ib1=findViewById(R.id.complaint);
        ib2=findViewById(R.id.leave);
        ib3=findViewById(R.id.outing);
        ib4=findViewById(R.id.elec_bill);
        ib5=findViewById(R.id.mess);
        ib6=findViewById(R.id.emergency);
        logout=findViewById(R.id.LogoutBtn);

        Calendar cal = Calendar.getInstance();
        final int day = cal.get(Calendar.DAY_OF_WEEK);
        if(day!=Calendar.SUNDAY){
            ib3.setEnabled(false);
            ib3.setBackgroundColor(R.color.green);
        }


        // for image buttons
        ib1.setOnClickListener(e->{
            Intent i = new Intent(this, ComplaintActivity.class);
            startActivity(i);
        });
        ib2.setOnClickListener(e->{
            Intent i = new Intent(this,LeaveActivity.class);
            startActivity(i);
        });
        ib3.setOnClickListener(e->{
            Intent i = new Intent(this,OutingActivity.class);
            startActivity(i);
        });
        ib4.setOnClickListener(e->{
            Intent i = new Intent(this,ElectricityBillActivity.class);
            startActivity(i);
        });
        ib5.setOnClickListener(e->{
            Intent i = new Intent(this,MessActivity.class);
            startActivity(i);
        });
        ib6.setOnClickListener(e->{
            Intent i = new Intent(this,EmergencyActivity.class);
            startActivity(i);
        });


        //for logout button

        logout.setOnClickListener(e->{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        });

    }
}