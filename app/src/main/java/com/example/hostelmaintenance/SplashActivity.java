package com.example.hostelmaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

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
                            if(documentSnapshot.getString("is_User") != null){
                                Intent i = new Intent(getApplicationContext(), StudentDashboard.class);
                                startActivity(i);
                                finish();
                            }
                            if(documentSnapshot.getString("is_Admin") != null){
                                Intent i = new Intent(getApplicationContext(), HostelWardenDashboard.class);
                                startActivity(i);
                                finish();
                            }

                        }
                    });
                }
                else{
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        },2000);
    }
}