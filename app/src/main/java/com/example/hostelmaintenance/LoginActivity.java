package com.example.hostelmaintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText email,password;
    public Button login;
    TextView signup;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup=findViewById(R.id.login_tv3);
        email=findViewById(R.id.ed_user_name);
        password=findViewById(R.id.ed_password);
        login=findViewById(R.id.button_login);
        auth=FirebaseAuth.getInstance();

        // For Signup
        signup.setOnClickListener(e->{
                Intent i = new Intent(this,SignupActivity.class);
                startActivity(i);
        });

        login.setOnClickListener(e->{
            String text_email = email.getText().toString();
            String text_password = password.getText().toString();
            if(TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password)){
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
            else{
                //login
                loginUser(text_email,text_password);
            }

        });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(LoginActivity.this, "User Logged In Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),StudentDashboard.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}