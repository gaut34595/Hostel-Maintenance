package com.example.hostelmaintenance.Hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.hostelmaintenance.R;

public class HostelLeaveActivity extends AppCompatActivity {
    ImageButton transact , transacted,incoming;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_leave);
        transact = findViewById(R.id.transact_leave);
        transacted = findViewById(R.id.transacted_leave);
        incoming= findViewById(R.id.leave_incoming);

        transact.setOnClickListener(e->{
            Intent i = new Intent(this, LeaveTransactActivity.class);
            startActivity(i);
            finish();
        });
        transacted.setOnClickListener(e->{
            Intent i = new Intent(this, TransactedLeaveActivity.class);
            startActivity(i);
            finish();
        });
        incoming.setOnClickListener(e->{
            Intent i = new Intent(this,StudentIncomingActivity.class);
            startActivity(i);
        });


    }
}