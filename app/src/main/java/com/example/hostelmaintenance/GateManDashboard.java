package com.example.hostelmaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hostelmaintenance.Main.LoginActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class GateManDashboard extends AppCompatActivity {
    ImageView scanqr;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_man_dashboard);
        scanqr=findViewById(R.id.scan_qr);
        logout=findViewById(R.id.LogoutBtn);

        logout.setOnClickListener(e->{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        });

        scanqr.setOnClickListener(e->{
            Intent i = new Intent(this,QRScanActivity.class);
            startActivity(i);
        });

    }
}