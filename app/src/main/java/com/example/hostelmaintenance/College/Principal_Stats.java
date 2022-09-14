package com.example.hostelmaintenance.College;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hostelmaintenance.GetLeaveData;
import com.example.hostelmaintenance.IncomingLeaveAdapter;
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

public class Principal_Stats extends AppCompatActivity implements IncomingLeaveAdapter.IncomingListener {
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
        setContentView(R.layout.activity_principal_stats);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        firebaseFirestore= FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String uid = user.getUid();
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        incoml= new ArrayList<>();
        recyclerView= findViewById(R.id.recylerprincipalstats);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DocumentReference dr = firebaseFirestore.collection("Users").document(uid);
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String college = String.valueOf(documentSnapshot.get("College"));
                FirebaseFirestore.getInstance().collection("Student_Leaves").whereEqualTo("Student_College",college)
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()!=0){
                                    for(DocumentChange ds: queryDocumentSnapshots.getDocumentChanges()){
                                        GetLeaveData getData = ds.getDocument().toObject(GetLeaveData.class);
                                        getData.setId(ds.getDocument().getId());
                                        incoml.add(getData);

                                        incomingLeaveAdapter = new IncomingLeaveAdapter(Principal_Stats.this,incoml,Principal_Stats.this);
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
                                    Toast.makeText(Principal_Stats.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Principal_Stats.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Toast.makeText(Principal_Stats.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void oLeaveClick(int position) {

    }
}