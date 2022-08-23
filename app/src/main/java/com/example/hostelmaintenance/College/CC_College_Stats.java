package com.example.hostelmaintenance.College;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hostelmaintenance.GetLeaveData;
import com.example.hostelmaintenance.IncomingLeaveAdapter;
import com.example.hostelmaintenance.LeaveVerifyAdapter;
import com.example.hostelmaintenance.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CC_College_Stats extends AppCompatActivity implements IncomingLeaveAdapter.IncomingListener{
    RecyclerView recyclerView;
    IncomingLeaveAdapter incomingLeaveAdapter;
    ArrayList<GetLeaveData> incoml;
    ProgressDialog progressDialog;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cc_college_stats);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        firebaseFirestore= FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String uid = user.getUid();
        int num = -1;
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        incoml= new ArrayList<>();
        recyclerView= findViewById(R.id.recylerstats);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DocumentReference dr = firebaseFirestore.collection("Users").document(uid);
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String course = String.valueOf(documentSnapshot.get("Course"));
                FirebaseFirestore.getInstance().collection("Student_Leaves").whereEqualTo("Student_Course",course)
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()!=0){
                                    for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()){
                                        GetLeaveData getLeaveData = ds.getDocument().toObject(GetLeaveData.class);
                                        getLeaveData.setId(ds.getDocument().getId());
                                        incoml.add(getLeaveData);

                                        incomingLeaveAdapter = new IncomingLeaveAdapter(CC_College_Stats.this,incoml,CC_College_Stats.this);
                                        recyclerView.setAdapter(incomingLeaveAdapter);
                                        incomingLeaveAdapter.notifyDataSetChanged();
                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }
                                    }
                                }
                                else
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(CC_College_Stats.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                                Log.d(">>>>>>>>>",e.getMessage());
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(">>>>>>>",e.getMessage());
            }
        });


    }


    @Override
    public void oLeaveClick(int position) {

    }
}