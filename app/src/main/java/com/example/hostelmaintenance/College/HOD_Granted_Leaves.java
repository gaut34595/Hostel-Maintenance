package com.example.hostelmaintenance.College;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hostelmaintenance.GetLeaveData;
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

public class HOD_Granted_Leaves extends AppCompatActivity implements LeaveVerifyAdapter.OnLeaveListener {
    RecyclerView recyclerView3;
    LeaveVerifyAdapter leaveGrantAdapter;
    ArrayList<GetLeaveData> grantlist;
    ProgressDialog progressDialog;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_granted_leaves);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        String uid = user.getUid();
        grantlist= new ArrayList<>();
        int num = 1;
        int num2 =0;
        recyclerView3 = findViewById(R.id.recyclercheck3);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        DocumentReference dr = firebaseFirestore.collection("Users").document(uid);
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String dept = String.valueOf(documentSnapshot.get("Department"));
                FirebaseFirestore.getInstance().collection("Student_Leaves").whereEqualTo("Verified_HOD",num).whereEqualTo("Verified_HW",num2)
                        .whereEqualTo("Student_Department",dept).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()!=0) {
                                    for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                                        GetLeaveData getdata = ds.getDocument().toObject(GetLeaveData.class);
                                        getdata.setId(ds.getDocument().getId());
                                        grantlist.add(getdata);

                                        leaveGrantAdapter = new LeaveVerifyAdapter(getApplicationContext(), grantlist, HOD_Granted_Leaves.this);
                                        recyclerView3.setAdapter(leaveGrantAdapter);
                                        leaveGrantAdapter.notifyDataSetChanged();
                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }

                                    }
                                }
                                else{
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(HOD_Granted_Leaves.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                    }

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

            }
        });

    }

    @Override
    public void OnLeaveClick(int position) {

    }
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_item,menu);
        MenuItem menuItem = menu.findItem(R.id.searchaction);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                leaveGrantAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }
}