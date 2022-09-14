package com.example.hostelmaintenance.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.hostelmaintenance.GetLeaveData;
import com.example.hostelmaintenance.LeaveVerifyAdapter;
import com.example.hostelmaintenance.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LeaveStatusActivity extends AppCompatActivity implements LeaveVerifyAdapter.OnLeaveListener{
    private FirebaseUser user;
    private FirebaseAuth auth;
    RecyclerView leaverecycle;
    ProgressDialog progressDialog;
    LeaveVerifyAdapter leaveAdapter;

    ArrayList<GetLeaveData> leavelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_status);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data.....");
        progressDialog.show();
        leavelist= new ArrayList<>();
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        String email = user.getEmail();
        leaverecycle= findViewById(R.id.recyclerviewleave);
        leaverecycle.setHasFixedSize(true);
        leaverecycle.setLayoutManager(new LinearLayoutManager(this));
        FirebaseFirestore.getInstance().collection("Student_Leaves").whereEqualTo("Student_Email",email)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()!=0){
                            for(DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()){
                                GetLeaveData getLeaveData = ds.getDocument().toObject(GetLeaveData.class);
                                getLeaveData.setId(ds.getDocument().getId());
                                leavelist.add(getLeaveData);

                                leaveAdapter = new LeaveVerifyAdapter(getApplicationContext(),leavelist,LeaveStatusActivity.this);
                                leaverecycle.setAdapter(leaveAdapter);
                                leaveAdapter.notifyDataSetChanged();
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();

                                }
                            }
                        }
                        else{
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                                Toast.makeText(LeaveStatusActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            Toast.makeText(LeaveStatusActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void OnLeaveClick(int position) {
        Intent i = new Intent(this,ShowQRActivity.class);
        i.putExtra("QR_Code",leavelist.get(position));
        startActivity(i);
        finish();
    }
}