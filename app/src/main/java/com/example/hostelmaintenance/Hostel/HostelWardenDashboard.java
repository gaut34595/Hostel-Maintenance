package com.example.hostelmaintenance.Hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hostelmaintenance.Main.LoginActivity;
import com.example.hostelmaintenance.R;
import com.google.firebase.auth.FirebaseAuth;

public class HostelWardenDashboard extends AppCompatActivity {
    ImageButton view_comp,view_leaves,hos_stats,
                    staff_det,electricity_stats,stud_direc;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_warden_dashboard);
        view_comp=findViewById(R.id.view_complaints);
        view_leaves=findViewById(R.id.verify_leaves);
        hos_stats=findViewById(R.id.hostel_stats);
        staff_det=findViewById(R.id.staff_details);
        electricity_stats=findViewById(R.id.elec_bill_stats);
        stud_direc=findViewById(R.id.student_directory);
        logout=findViewById(R.id.LogoutBtn);



        // for clicking of buttons on dashboard
        view_comp.setOnClickListener(e->{
            Intent i = new Intent(this,ComplaintCheckHostelActivity.class);
            startActivity(i);
        });
        view_leaves.setOnClickListener(e->{
            Intent i = new Intent(this,HostelLeaveActivity.class);
            startActivity(i);
        });
        hos_stats.setOnClickListener(e->{
            Intent i = new Intent(this,HostelStats.class);
            startActivity(i);
        });
        staff_det.setOnClickListener(e->{
            Intent i = new Intent(this,StaffDetailsHostel.class);
            startActivity(i);
        });
        electricity_stats.setOnClickListener(e->{
            Intent i = new Intent(this,ElectricityBillStats.class);
            startActivity(i);
        });
        stud_direc.setOnClickListener(e->{
            Intent i = new Intent(this,StudentDirectoryHostel.class);
            startActivity(i);
        });

        //for logout button

        logout.setOnClickListener(e->{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
        });
    }
}