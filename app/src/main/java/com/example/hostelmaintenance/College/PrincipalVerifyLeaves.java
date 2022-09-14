package com.example.hostelmaintenance.College;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class PrincipalVerifyLeaves extends AppCompatActivity implements LeaveVerifyAdapter.OnLeaveListener {
    RecyclerView recyclerView1;
    LeaveVerifyAdapter leaveVerifyAdapter;
    ArrayList<GetLeaveData> leavelist;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_verify_leaves);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        firebaseFirestore= FirebaseFirestore.getInstance();
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        int num = 0;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String uid = user.getUid();
        leavelist= new ArrayList<>();
        recyclerView1 = findViewById(R.id.recyclerprin);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        DocumentReference dr = firebaseFirestore.collection("Users").document(uid);
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String college = String.valueOf(documentSnapshot.get("College"));
                FirebaseFirestore.getInstance().collection("Student_Leaves").whereEqualTo("Verified_CC",num).whereEqualTo("Verified_HOD",num).whereEqualTo("Student_College",college)
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()!=0){
                                    for(DocumentChange ds: queryDocumentSnapshots.getDocumentChanges()){
                                        GetLeaveData geta = ds.getDocument().toObject(GetLeaveData.class);
                                        geta.setId(ds.getDocument().getId());
                                        leavelist.add(geta);

                                        leaveVerifyAdapter = new LeaveVerifyAdapter(getApplicationContext(), leavelist, PrincipalVerifyLeaves.this);
                                        recyclerView1.setAdapter(leaveVerifyAdapter);
                                        leaveVerifyAdapter.notifyDataSetChanged();
                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }
                                    }

                                }
                                else{
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(PrincipalVerifyLeaves.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                    Toast.makeText(PrincipalVerifyLeaves.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                    Toast.makeText(PrincipalVerifyLeaves.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                leaveVerifyAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void OnLeaveClick(int position) {

        Intent i = new Intent(this, Principal_Final_Verification.class);
        i.putExtra("Leave_Item_Principal",leavelist.get(position));
        startActivity(i);
        finish();
    }
}