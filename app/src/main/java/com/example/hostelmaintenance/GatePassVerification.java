package com.example.hostelmaintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class GatePassVerification extends AppCompatActivity {
    EditText enroll,name,course,fathname,fathercont,leavefrom,leaveto;
    Button validate;
    ProgressDialog progressDialog;
    public GetLeaveData geta;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        String id = intent.getStringExtra("QRid");



        FirebaseFirestore.getInstance().collection("Student_Leaves").whereEqualTo("QRCode",id)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size() != 0) {
                            for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                             geta = ds.getDocument().toObject(GetLeaveData.class);
                                enroll.setText(geta.getStudent_Enrollment());
                                name.setText(geta.getStudent_Name());
                                course.setText(geta.getStudent_Course());
                                fathname.setText(geta.getFather_Name());
                                fathercont.setText(geta.getFather_Contact());
                                leavefrom.setText(geta.getLeave_From());
                                leaveto.setText(geta.getLeave_to());
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }

                            }
                        }
                        else{
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                                Toast.makeText(GatePassVerification.this, "Not a valid Gate Pass",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(),GateManDashboard.class);
                                startActivity(i);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GatePassVerification.this, "No data available", Toast.LENGTH_SHORT).show();
                        Log.d(">>>>>>>>>",e.getMessage());
                    }
                });

    }
}