package com.example.hostelmaintenance.College;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.hostelmaintenance.GetLeaveData;
import com.example.hostelmaintenance.LeaveVerifyAdapter;
import com.example.hostelmaintenance.R;

import java.util.ArrayList;

public class CC_College_Stats extends AppCompatActivity implements LeaveVerifyAdapter.OnLeaveListener {
    RecyclerView recyclerView;
    LeaveVerifyAdapter leaveVerifyAdapter;
    ArrayList<GetLeaveData> statslist;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cc_college_stats);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        statslist = new ArrayList<>();
        recyclerView= findViewById(R.id.recylerstats);

    }


    @Override
    public void OnLeaveClick(int position) {

    }
}