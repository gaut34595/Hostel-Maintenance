package com.example.hostelmaintenance.College;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hostelmaintenance.CollegeReportActivity;
import com.example.hostelmaintenance.Main.LoginActivity;
import com.example.hostelmaintenance.R;
import com.google.firebase.auth.FirebaseAuth;

public class CollegePrincipalDashboard extends AppCompatActivity {
    ImageView verify,processed,stats,report;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_principal_dashboard);

        verify= findViewById(R.id.verify_leave);
        processed = findViewById(R.id.procc_verification);
        stats= findViewById(R.id.stats_leave);
        logout= findViewById(R.id.LogoutBtn);
        report= findViewById(R.id.generate_report);

        logout.setOnClickListener(e->{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(CollegePrincipalDashboard.this, LoginActivity.class);
            startActivity(i);
            finish();
        });

        verify.setOnClickListener(e->{
            Intent i = new Intent(this, PrincipalVerifyLeaves.class);
            startActivity(i);
        });
        processed.setOnClickListener(e->{
            Intent i = new Intent(this, PrincipalProcessedVerification.class);
            startActivity(i);
        });
        stats.setOnClickListener(e->{
            Intent i = new Intent(this, Principal_Stats.class);
            startActivity(i);
        });
        report.setOnClickListener(e->{
            Intent i = new Intent(this, CollegeReportActivity.class);
            startActivity(i);
        });
    }
}