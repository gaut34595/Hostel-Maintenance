package com.example.hostelmaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hostelmaintenance.Main.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Course_Coordinator_Dashboard extends AppCompatActivity {
    ImageView verify,processed,stats;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_coordinator_dashboard);

        verify= findViewById(R.id.verify_leave);
        processed = findViewById(R.id.procc_verification);
        stats= findViewById(R.id.stats_leave);
        logout= findViewById(R.id.LogoutBtn);

        //for logout
        logout.setOnClickListener(e->{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Course_Coordinator_Dashboard.this, LoginActivity.class);
            startActivity(i);
            finish();
        });

        // for Different buttons
        verify.setOnClickListener(e->{
            Intent i = new Intent(this,CC_Verify_Leave.class);
            startActivity(i);
        });
        processed.setOnClickListener(e->{
            Intent i = new Intent(this,CC_Processed_Verification.class);
            startActivity(i);
        });
        stats.setOnClickListener(e->{
            Intent i = new Intent(this,CC_College_Stats.class);
            startActivity(i);
        });


    }
}