package com.example.hostelmaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hostelmaintenance.Main.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HOD_Dashboard_Activity extends AppCompatActivity {
    ImageView grant,granted,stats;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_dashboard);
        grant= findViewById(R.id.grant_leave);
        granted = findViewById(R.id.granted_leave);
        stats= findViewById(R.id.stats);
        logout= findViewById(R.id.LogoutBtn);

        logout.setOnClickListener(e->{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(HOD_Dashboard_Activity.this, LoginActivity.class);
            startActivity(i);
            finish();
        });

        // for Different buttons
        grant.setOnClickListener(e->{
            Intent i = new Intent(this,HOD_Grant_Leave.class);
            startActivity(i);
        });
        granted.setOnClickListener(e->{
            Intent i = new Intent(this,HOD_Granted_Leaves.class);
            startActivity(i);
        });
        stats.setOnClickListener(e->{
            Intent i = new Intent(this,HOD_Stats_Activity.class);
            startActivity(i);
        });
    }
}