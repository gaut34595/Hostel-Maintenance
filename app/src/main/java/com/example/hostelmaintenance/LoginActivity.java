package com.example.hostelmaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        fStore=FirebaseFirestore.getInstance();

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
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                checkUserAccessLevel(authResult.getUser().getUid());
            }
        });
    }

    private void checkUserAccessLevel(String uid) {
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
                if(documentSnapshot.getString("is_Admin") != null){
                    Intent i = new Intent(getApplicationContext(), HostelWardenDashboard.class);
                    startActivity(i);
                    finish();
                }

            }
        });
    }
}