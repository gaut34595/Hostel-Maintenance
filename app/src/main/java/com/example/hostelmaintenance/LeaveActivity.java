package com.example.hostelmaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.hostelmaintenance.R;

public class LeaveActivity extends AppCompatActivity {
    ImageButton register,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        register = findViewById(R.id.leave_register);
        status = findViewById(R.id.leave_status);

        register.setOnClickListener(e->{
            Intent i = new Intent(this,LeaveRegistrationActivity.class);
            startActivity(i);
            finish();
        });
        status.setOnClickListener(e->{
            Intent i = new Intent(this,LeaveStatusActivity.class);
            startActivity(i);
            finish();
        });
    }
}