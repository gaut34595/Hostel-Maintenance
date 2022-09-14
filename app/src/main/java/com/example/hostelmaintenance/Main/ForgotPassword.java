package com.example.hostelmaintenance.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelmaintenance.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    EditText email;
    Button reset;
    TextView login;
    ProgressDialog progressDialog;
    private FirebaseAuth auth;
    String temail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email= findViewById(R.id.ed_user_name);
        reset= findViewById(R.id.reset);
        login = findViewById(R.id.loginbtn);
        auth = FirebaseAuth.getInstance();

        reset.setOnClickListener(e->{
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Sending Password Reset Link...");
            progressDialog.show();
           temail = email.getText().toString();
           if(TextUtils.isEmpty(temail)){
               Toast.makeText(this, "Please Enter Your Email Address", Toast.LENGTH_SHORT).show();
               if(progressDialog.isShowing()){
                   progressDialog.dismiss();
               }
           }
           else{
               //resetlink
               resetPass(temail);
           }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ForgotPassword.this,LoginActivity.class);
                startActivity(i);
                finish();

            }
        });
    }

    private void resetPass(String temail) {
        auth.sendPasswordResetEmail(temail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Intent i = new Intent(ForgotPassword.this,LoginActivity.class);
                    startActivity(i);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(ForgotPassword.this,"User Not Found", Toast.LENGTH_SHORT).show();
            }
        });

    }


}