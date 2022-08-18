package com.example.hostelmaintenance.Hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hostelmaintenance.GetComplaintData;
import com.example.hostelmaintenance.MyAdap;
import com.example.hostelmaintenance.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ComplaintCheckHostelActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdap dataAdap;
    ArrayList<GetComplaintData> complaintList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_check_hostel);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        complaintList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseFirestore.getInstance().collection("Complaints").orderBy("Complaint_Status", Query.Direction.ASCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size() != 0) {
                            for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                                GetComplaintData get = ds.getDocument().toObject(GetComplaintData.class);
                                complaintList.add(get);
                                dataAdap = new MyAdap(getApplicationContext(), complaintList);
                                recyclerView.setAdapter(dataAdap);
                                dataAdap.notifyDataSetChanged();
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                        }
                        else{
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                                Toast.makeText(ComplaintCheckHostelActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }
    @Override
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
                dataAdap.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}