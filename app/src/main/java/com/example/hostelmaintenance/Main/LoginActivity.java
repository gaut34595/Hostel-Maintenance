package com.example.hostelmaintenance.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelmaintenance.College.Course_Coordinator_Dashboard;
import com.example.hostelmaintenance.College.HOD_Dashboard_Activity;
import com.example.hostelmaintenance.College.CollegePrincipalDashboard;
import com.example.hostelmaintenance.Gate.GateManDashboard;
import com.example.hostelmaintenance.Hostel.HostelWardenDashboard;
import com.example.hostelmaintenance.R;
import com.example.hostelmaintenance.Student.StudentDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText email,password;
    public Button login;
    TextView signup,forgot;
    private FirebaseAuth auth;
    private  FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseFirestore fStore;
    String text_email;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(">>>>>>>>>>", FieldValue.serverTimestamp().toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        signup=findViewById(R.id.login_tv3);
        email=findViewById(R.id.ed_user_name);
        password=findViewById(R.id.ed_password);
        login=findViewById(R.id.login);
        auth=FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        fStore=FirebaseFirestore.getInstance();
        forgot= findViewById(R.id.forgot_pass);

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
            text_email = email.getText().toString();
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
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(i);

            }
        });
    }

    private void loginUser(String email, String password) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   // FirebaseUser nuser = FirebaseAuth.getInstance().getCurrentUser();
                 //   Boolean emailflag =nuser.isEmailVerified();
                    
                   // if(emailflag){
                        Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        checkUserAccessLevel(task.getResult().getUser().getUid());
                    }
//                else{
//                        if(progressDialog.isShowing()){
//                            progressDialog.dismiss();
//                        }
//                        Toast.makeText(LoginActivity.this, "Email not verified", Toast.LENGTH_SHORT).show();
//                        nuser.sendEmailVerification();
//                        Toast.makeText(LoginActivity.this, "Verification Email has been sent", Toast.LENGTH_SHORT).show();
//                        auth.signOut();
//
//                    }
//                }
                
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
           if(documentSnapshot.getString("is_Principal") != null){
                    Intent i = new Intent(LoginActivity.this, CollegePrincipalDashboard.class);
                    startActivity(i);
                    finish();
                }
                if(documentSnapshot.getString("is_Admin") != null){
                    Intent i = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}