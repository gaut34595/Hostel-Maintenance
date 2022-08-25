package com.example.hostelmaintenance.Hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
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

public class HostelStats extends AppCompatActivity implements LeaveVerifyAdapter.OnLeaveListener {
    RecyclerView recyclerViewTransact;
    LeaveVerifyAdapter lVA;
    ArrayList<GetLeaveData> transactlist;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_stats);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data.....");
        progressDialog.show();
        firebaseFirestore= FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String uid = user.getUid();
        int num = 1;
        transactlist = new ArrayList<>();
        recyclerViewTransact = findViewById(R.id.hostel_recycle);
        recyclerViewTransact.setHasFixedSize(true);
        recyclerViewTransact.setLayoutManager(new LinearLayoutManager(this));

        DocumentReference dr = firebaseFirestore.collection("Users").document(uid);
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String hostel = String.valueOf(documentSnapshot.get("Hostel"));
                FirebaseFirestore.getInstance().collection("Student_Leaves").whereEqualTo("Student_Hostel",hostel)
                        .whereEqualTo("Verified_HOD", num).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()!=0){
                                    for(DocumentChange ds :queryDocumentSnapshots.getDocumentChanges()){
                                        GetLeaveData getLeaveData = ds.getDocument().toObject(GetLeaveData.class);
                                        getLeaveData.setId(ds.getDocument().getId());
                                        transactlist.add(getLeaveData);
                                        lVA = new LeaveVerifyAdapter(getApplicationContext(),transactlist,HostelStats.this);
                                        recyclerViewTransact.setAdapter(lVA);
                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();

                                        }
                                    }
                                }
                                else{
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(HostelStats.this, "No Data Found", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(HostelStats.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                    Toast.makeText(HostelStats.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_item, menu);
        MenuItem menuItem = menu.findItem(R.id.searchaction);
        android.widget.SearchView searchView = (android.widget.SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                lVA.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void OnLeaveClick(int position) {

    }
}