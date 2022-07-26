package com.example.hostelmaintenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class ComplaintActivity extends AppCompatActivity {
ViewPager vpg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        vpg=findViewById(R.id.myviewpager);
        viewpager_complaint_adapter vpga = new viewpager_complaint_adapter(this);

        vpg.setAdapter(vpga);

    }
}