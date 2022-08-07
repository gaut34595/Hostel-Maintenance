package com.example.hostelmaintenance.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.hostelmaintenance.GetLeaveData;
import com.example.hostelmaintenance.LeaveDataAdapter;
import com.example.hostelmaintenance.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LeaveStatusActivity extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseAuth auth;
    RecyclerView leaverecycle;
    ProgressDialog progressDialog;
    LeaveDataAdapter leaveAdapter;

    ArrayList<GetLeaveData> leavelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_status);
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        String email = user.getEmail();
        leavelist= new ArrayList<>();
        leaveAdapter = new LeaveDataAdapter(this,leavelist);
        leaverecycle= findViewById(R.id.recyclerviewleave);
        leaverecycle.setHasFixedSize(true);
        leaverecycle.setLayoutManager(new LinearLayoutManager(this));
        leaverecycle.setAdapter(leaveAdapter);

       FirebaseFirestore.getInstance().collection("Student_Leaves").
               whereEqualTo("Student_Email", email).get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if(task.isSuccessful()){
                           Log.d("---------->>>","Yha tk aa gya hu m");
                           for(DocumentChange dd: task.getResult().getDocumentChanges()){
                               GetLeaveData getLeaveDat = dd.getDocument().toObject(GetLeaveData.class);
                               leavelist.add(getLeaveDat);
                               leaveAdapter.notifyDataSetChanged();
                           }
                       }

                   }
               });
    }
}