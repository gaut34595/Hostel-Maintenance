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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private TextView tv1;
    private Button register;
    private EditText email,username,password;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        tv1=findViewById(R.id.register_bottom);
        email= findViewById(R.id.register_email);
        username= findViewById(R.id.register_username);
        password= findViewById(R.id.text_input_password);
        register=findViewById(R.id.button_register);
        auth=FirebaseAuth.getInstance();

        tv1.setOnClickListener(e->{
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });

        register.setOnClickListener(e->{
            String textemail=email.getText().toString();
            String textpassword = password.getText().toString();

            if(TextUtils.isEmpty(textemail)|| TextUtils.isEmpty(textpassword)){
                Toast.makeText(this, "Empty Credentials", Toast.LENGTH_SHORT).show();
            }
            else if(textpassword.length()<6){

                Toast.makeText(this, "Password to Short", Toast.LENGTH_SHORT).show();
            }else{
                registerUser(textemail,textpassword);
            }
        });


    }

    private void registerUser(String textemail, String textpassword) {
            auth.createUserWithEmailAndPassword(textemail,textpassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignupActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(SignupActivity.this, "Registering User Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}