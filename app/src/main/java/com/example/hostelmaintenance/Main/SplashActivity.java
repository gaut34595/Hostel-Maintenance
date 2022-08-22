package com.example.hostelmaintenance.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.hostelmaintenance.College.Course_Coordinator_Dashboard;
import com.example.hostelmaintenance.College.HOD_Dashboard_Activity;
import com.example.hostelmaintenance.GateManDashboard;
import com.example.hostelmaintenance.Hostel.HostelWardenDashboard;
import com.example.hostelmaintenance.R;
import com.example.hostelmaintenance.Student.StudentDashboard;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();
        auth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        String num = "1";
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(auth.getCurrentUser()!=null){
             String uid = auth.getCurrentUser().getUid();
                    DocumentReference df = fStore.collection("Users").document(uid);
                    df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.getString("is_User") != null ){
                                Intent i = new Intent(getApplicationContext(), StudentDashboard.class);
                                startActivity(i);
                                finish();
                            }if(documentSnapshot.getString("is_Program_Coordinator") != null){
                                Intent i = new Intent(getApplicationContext(), Course_Coordinator_Dashboard.class);
                                startActivity(i);
                                finish();
                            }
                            if(documentSnapshot.getString("is_Hostel_Admin") != null){
                                Intent i = new Intent(SplashActivity.this, HostelWardenDashboard.class);
                                startActivity(i);
                                finish();
                            }
                            if(documentSnapshot.getString("is_HOD") != null){
                                Intent i = new Intent(SplashActivity.this, HOD_Dashboard_Activity.class);
                                startActivity(i);
                                finish();
                            }
                            if(documentSnapshot.getString("is_Security")!= null){
                                Intent i = new Intent(SplashActivity.this, GateManDashboard.class);
                                startActivity(i);
                                finish();

                            }

                        }
                    });
                }
                else{
                    Intent i = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        },2000);
    }
}