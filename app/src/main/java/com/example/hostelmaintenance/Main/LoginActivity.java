package com.example.hostelmaintenance.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelmaintenance.College.Course_Coordinator_Dashboard;
import com.example.hostelmaintenance.College.HOD_Dashboard_Activity;
import com.example.hostelmaintenance.GateManDashboard;
import com.example.hostelmaintenance.Hostel.HostelWardenDashboard;
import com.example.hostelmaintenance.R;
import com.example.hostelmaintenance.Student.StudentDashboard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText email,password;
    public Button login;
    TextView signup;
    private FirebaseAuth auth;
    private  FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseFirestore fStore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        signup=findViewById(R.id.login_tv3);
        email=findViewById(R.id.ed_user_name);
        password=findViewById(R.id.ed_password);
        login=findViewById(R.id.button_login);
        auth=FirebaseAuth.getInstance();
         currentUser = auth.getCurrentUser();
        fStore=FirebaseFirestore.getInstance();

        // For Signup
        signup.setOnClickListener(e->{
                Intent i = new Intent(this,SignupActivity.class);
                startActivity(i);
        });

        login.setOnClickListener(e->{

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Logging in....");
            progressDialog.show();
            String text_email = email.getText().toString();
            String text_password = password.getText().toString();
            if(TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password)){
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
            else{
                //login
                loginUser(text_email,text_password);
            }

        });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                checkUserAccessLevel(authResult.getUser().getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(LoginActivity.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUserAccessLevel(String uid) {
        String num = "1";
        DocumentReference dr= fStore.collection("Users").document(uid);
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Log.d("-------->>>.",documentSnapshot.getData());
           if(documentSnapshot.getString("is_User") != null){
               Intent i = new Intent(getApplicationContext(), StudentDashboard.class);
               startActivity(i);
               finish();
           }
            if(documentSnapshot.getString("is_Program_Coordinator") != null){
                    Intent i = new Intent(getApplicationContext(), Course_Coordinator_Dashboard.class);
                    startActivity(i);
                    finish();
                }
              if(documentSnapshot.getString("is_Hostel_Admin") != null){
                    Intent i = new Intent(LoginActivity.this, HostelWardenDashboard.class);
                    startActivity(i);
                    finish();
                }
                if(documentSnapshot.getString("is_HOD") != null){
                    Intent i = new Intent(LoginActivity.this, HOD_Dashboard_Activity.class);
                    startActivity(i);
                    finish();
                }
                if(documentSnapshot.getString("is_Security") != null){
                    Intent i = new Intent(LoginActivity.this, GateManDashboard.class);
                    startActivity(i);
                    finish();
                }

            }
        });
    }
}