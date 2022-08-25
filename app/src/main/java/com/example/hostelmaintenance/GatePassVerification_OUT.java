package com.example.hostelmaintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class GatePassVerification_OUT extends AppCompatActivity {
    EditText enroll,name,course,fathname,fathercont,leavefrom,leaveto;
    ScrollView scrollView;
    ImageView userimage;
    String imagelink;
    String gatetimeout;
    Button validate;
    ProgressDialog progressDialog;
    public GetLeaveData geta;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrollView= findViewById(R.id.gatescroll);
        setContentView(R.layout.activity_gate_pass_verification);
        Intent intent = getIntent();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        enroll=findViewById(R.id.gatestudent_enrol);
        name = findViewById(R.id.gatestudent_name);
        course = findViewById(R.id.gatestud_program);
        fathname= findViewById(R.id.gatestud_father);
        fathercont= findViewById(R.id.gatestudent_father_cont);
        leavefrom = findViewById(R.id.gateleavefrom);
        leaveto = findViewById(R.id.gateleaveto);
        validate=findViewById(R.id.button_validate);
        userimage= findViewById(R.id.gateuser_image);
        String qrid = intent.getStringExtra("QRid");
        gatetimeout = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());


        FirebaseFirestore.getInstance().collection("Student_Leaves").whereEqualTo("QRCode",qrid)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size() != 0) {
                            for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                             geta = ds.getDocument().toObject(GetLeaveData.class);
                                imagelink = geta.getImageLink();
                                enroll.setText(geta.getStudent_Enrollment());
                                name.setText(geta.getStudent_Name());
                                course.setText(geta.getStudent_Course());
                                fathname.setText(geta.getFather_Name());
                                fathercont.setText(geta.getFather_Contact());
                                leavefrom.setText(geta.getLeave_From());
                                leaveto.setText(geta.getLeave_to());
                                geta.setId(ds.getDocument().getId());
                                Picasso.get().load(imagelink).into(userimage);
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }

                            }
                        }
                        else{
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                                Toast.makeText(GatePassVerification_OUT.this, "Not a valid Gate Pass",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(GatePassVerification_OUT.this,GateManDashboard.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GatePassVerification_OUT.this, "No data available", Toast.LENGTH_SHORT).show();
                        Log.d(">>>>>>>>>",e.getMessage());
                    }
                });

        validate.setOnClickListener(e->{
            DocumentReference dd= FirebaseFirestore.getInstance().collection("Student_Leaves").document(geta.getId());
            HashMap<String,Object> map = new HashMap<>();
            map.put("Gate_Validation_Out",1);
            map.put("Gate_Validation_Out_Time",gatetimeout);
            dd.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(GatePassVerification_OUT.this, "Gate Pass Verified Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(GatePassVerification_OUT.this,GateManDashboard.class);
                    startActivity(i);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("----->>>>>>>",e.getMessage());
                }
            });
        });
    }
}