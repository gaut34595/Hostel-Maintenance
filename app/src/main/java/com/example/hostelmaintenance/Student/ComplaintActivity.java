package com.example.hostelmaintenance.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.hostelmaintenance.R;

public class ComplaintActivity extends AppCompatActivity {
ViewPager vpg;
ImageButton complaint_reg,complaint_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        vpg=findViewById(R.id.myviewpager);
        complaint_reg=findViewById(R.id.complaint_bt);
        complaint_status=findViewById(R.id.status_bt);
        viewpager_complaint_adapter vpga = new viewpager_complaint_adapter(this);
        vpg.setAdapter(vpga);

        complaint_reg.setOnClickListener(e->{
            Intent i = new Intent(this, ComplaintRegistrationActivity.class);
            startActivity(i);
            finish();
        });
        complaint_status.setOnClickListener(e->{
            Intent i = new Intent(this, ComplaintStatusActivity.class);
            startActivity(i);
            finish();
        });

    }
}