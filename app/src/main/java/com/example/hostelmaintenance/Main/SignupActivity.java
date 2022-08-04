package com.example.hostelmaintenance.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelmaintenance.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private TextView login;
    private Button register;
    private EditText email,password,name,father_name,enrollment,course,finger,room,cont_stud,cont_father;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        login=findViewById(R.id.register_bottom);
        email= findViewById(R.id.email);
        password= findViewById(R.id.password);
        name= findViewById(R.id.std_name);
        father_name=findViewById(R.id.father_name);
        enrollment= findViewById(R.id.enrollment);
        course = findViewById(R.id.stud_course);
        finger= findViewById(R.id.std_finger);
        room= findViewById(R.id.room_no);
        cont_stud= findViewById(R.id.std_contact);
        cont_father= findViewById(R.id.std_father);
        register=findViewById(R.id.button_signup);
        auth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        login.setOnClickListener(e->{
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });

        register.setOnClickListener(e->{
            String textemail=email.getText().toString();
            String textpassword = password.getText().toString();
            String textname = name.getText().toString();
            String fathername = father_name.getText().toString();
            String textenrollment = enrollment.getText().toString();
            String textcourse = course.getText().toString();
            String textfinger = finger.getText().toString();
            String textroom = room.getText().toString();
            String text_std_cont = cont_stud.getText().toString();
            String text_std_fat = cont_father.getText().toString();
            String text_isUser = "1";

            if(TextUtils.isEmpty(textemail)&& TextUtils.isEmpty(textpassword)&& TextUtils.isEmpty(textname)
                    && TextUtils.isEmpty(textenrollment)&& TextUtils.isEmpty(textfinger)&& TextUtils.isEmpty(textroom)
                    && TextUtils.isEmpty(text_std_cont)&& TextUtils.isEmpty(text_std_fat)&& TextUtils.isEmpty(fathername)
                    && TextUtils.isEmpty(textcourse)){
                Toast.makeText(this, "Please provide all the details", Toast.LENGTH_SHORT).show();
            }
            else if(textpassword.length()<6){

                Toast.makeText(this, "Password to Short", Toast.LENGTH_SHORT).show();
            }else {
                auth.createUserWithEmailAndPassword(textemail,textpassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = auth.getCurrentUser();
                                    HashMap<String ,Object> signup_map = new HashMap<>();
                                    signup_map.put("is_User",text_isUser);
                                    signup_map.put("Email",textemail);
                                    signup_map.put("Name",textname);
                                    signup_map.put("Father's_Name",fathername);
                                    signup_map.put("Enrollment_No",textenrollment);
                                    signup_map.put("Student_Course",textcourse);
                                    signup_map.put("Finger_No",textfinger);
                                    signup_map.put("Room_No",textroom);
                                    signup_map.put("Student_Cont",text_std_cont);
                                    signup_map.put("Father_Cont",text_std_fat);

                                    DocumentReference df  = fStore.collection("Users").document(user.getUid());
                                    df.set(signup_map);
                                    Toast.makeText(SignupActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(i);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignupActivity.this, "Some Error", Toast.LENGTH_SHORT).show();
                                Log.d("------->>>>>>>>",e.getMessage());
                            }
                        });
            }
        });


    }
}
