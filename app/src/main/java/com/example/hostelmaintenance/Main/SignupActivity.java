package com.example.hostelmaintenance.Main;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelmaintenance.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView login;
    private Button register;
    private EditText email, password, name, father_name, enrollment, course, finger, room, cont_stud, cont_father;
    String textemail,textpassword,textname,fathername,textenrollment,textcourse,textfinger,textroom,
            text_std_cont,text_std_fat,text_isUser,textHostel,textDept;
    Spinner course1;
    Spinner hostel;
    Spinner stud_dept;
    private ImageView userimage;
    ScrollView scrollView;
    private FirebaseAuth auth;
   private FirebaseUser user;
    private FirebaseFirestore fStore;
    byte [] imagebyte;
    String imageurl;
    Boolean flag=false;
    ActivityResultLauncher<String> mGetContent;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrollView=findViewById(R.id.root);
        progressDialog = new ProgressDialog(this);
        setContentView(R.layout.activity_signup);
     //   user = auth.getCurrentUser();
        userimage= findViewById(R.id.user_image);
        login = findViewById(R.id.register_bottom);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.std_name);
        father_name = findViewById(R.id.father_name);
        enrollment = findViewById(R.id.enrollment);
       // course = findViewById(R.id.stud_course);
        finger = findViewById(R.id.std_finger);
        room = findViewById(R.id.room_no);
        cont_stud = findViewById(R.id.std_contact);
        cont_father = findViewById(R.id.std_father);
        register = findViewById(R.id.button_signup);
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        course1= findViewById(R.id.stud_course);
        hostel = findViewById(R.id.stud_hostel);
        stud_dept= findViewById(R.id.stud_dept);

        login.setOnClickListener(e->{
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });

        register.setOnClickListener(this);

        userimage.setOnClickListener(e->{
            mGetContent.launch("image/*");
        });
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Intent i = new Intent(SignupActivity.this,CropperActivity.class);
                i.putExtra("DATA",result.toString());
                startActivityForResult(i,100);
            }
        });

    }


    public void onClick(View e) {
         textemail = email.getText().toString();
         textpassword = password.getText().toString();
         textname = name.getText().toString();
         fathername = father_name.getText().toString();
         textenrollment = enrollment.getText().toString();
         textcourse = course1.getSelectedItem().toString();
         textHostel = hostel.getSelectedItem().toString();
         textDept = stud_dept.getSelectedItem().toString();
         textfinger = finger.getText().toString();
         textroom = room.getText().toString();
         text_std_cont = cont_stud.getText().toString();
         text_std_fat = cont_father.getText().toString();
         text_isUser = "1";


        if (TextUtils.isEmpty(textemail) || TextUtils.isEmpty(textpassword) || TextUtils.isEmpty(textname)
                    || TextUtils.isEmpty(textenrollment)  || TextUtils.isEmpty(textfinger) || TextUtils.isEmpty(textroom)
                    || TextUtils.isEmpty(text_std_cont) || TextUtils.isEmpty(text_std_fat) || TextUtils.isEmpty(fathername)
                    || TextUtils.isEmpty(textcourse))
            {
                Toast.makeText(this, "Please provide all the details", Toast.LENGTH_SHORT).show();

            }
        else if(text_std_cont.trim().length() < 10 || text_std_fat.trim().length() < 10){
            Toast.makeText(this, "Contact details are not correct", Toast.LENGTH_SHORT).show();
        }
            else if(!textemail.contains("tmu.ac.in")){
                Toast.makeText(this, "Please use your official email id", Toast.LENGTH_SHORT).show();
            }
            else if(textpassword.length()<6){

                Toast.makeText(this, "Password to Short", Toast.LENGTH_SHORT).show();
              }
            else if(textcourse.equals("Select Course")){
                    Toast.makeText(this, "Please select a Course", Toast.LENGTH_SHORT).show();
            //Snackbar.make(this,course1.getRootView(),"Please Select Course",Snackbar.LENGTH_LONG).show();

        }
        else if(textHostel.equals("Select Hostel")){
            Toast.makeText(this, "Please select a Hostel", Toast.LENGTH_SHORT).show();
            //Snackbar.make(this,course1.getRootView(),"Please Select Course",Snackbar.LENGTH_LONG).show();

        }
        else if(textDept.equals("Select Department")){
            Toast.makeText(this, "Please select a Department", Toast.LENGTH_SHORT).show();
            //Snackbar.make(this,course1.getRootView(),"Please Select Course",Snackbar.LENGTH_LONG).show();

        }
                else{
                    progressDialog.setMessage("Please Wait");
                    progressDialog.show();
                    uploadImage(imagebyte);
         }


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1 && requestCode == 100){
            String imageuri = data.getStringExtra("RESULT");
            Uri imageu = Uri.parse(imageuri);
            try{
                Bitmap original = MediaStore.Images.Media.getBitmap(getContentResolver(),imageu);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if(imageu!= null){
                    original.compress(Bitmap.CompressFormat.JPEG,80,stream);
                    userimage.setImageBitmap(original);
                    imagebyte = stream.toByteArray();

                }
            } catch (FileNotFoundException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

    }

    private void uploadImage(byte[] imagebyte) {
        StorageReference ref = FirebaseStorage.getInstance().getReference("Images")
                .child(String.valueOf(System.currentTimeMillis()));
        ref.putBytes(imagebyte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageurl= uri.toString();
                        Log.d(">>>>>>>>>",imageurl);
                        uploadData();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(">>>>>>>>>",e.getMessage());
                    }
                });
                Toast.makeText(SignupActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void uploadData() {
        auth.createUserWithEmailAndPassword(textemail, textpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            HashMap<String, Object> signup_map = new HashMap<>();
                            signup_map.put("is_User", text_isUser);
                            signup_map.put("Email", textemail);
                            signup_map.put("Name", textname);
                            signup_map.put("Father's_Name", fathername);
                            signup_map.put("Enrollment_No", textenrollment);
                            signup_map.put("Student_Department",textDept);
                            signup_map.put("Student_Course", textcourse);
                            signup_map.put("Student_Hostel",textHostel);
                            signup_map.put("Finger_No", textfinger);
                            signup_map.put("Room_No", textroom);
                            signup_map.put("Student_Cont", text_std_cont);
                            signup_map.put("Father_Cont", text_std_fat);
                            signup_map.put("Image",imageurl);

                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            df.set(signup_map);
                            if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                            }
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(SignupActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(SignupActivity.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                        Log.d("------->>>>>>>>", e.getMessage());
                    }
                });
    }

}