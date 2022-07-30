package com.example.hostelmaintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ComplaintStatusActivity extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseAuth auth;
    private ListView listView;
    DatabaseReference mRef;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_status);
        listView=findViewById(R.id.lv1);
        auth=FirebaseAuth.getInstance();
        user =auth.getCurrentUser();
        tv=findViewById(R.id.text11);

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_itemd,list);
        listView.setAdapter(adapter);
        Log.d(">>>>>>",  "inside if");

        mRef= FirebaseDatabase.getInstance().getReference();
        mRef.child("Maintenance").child("Complaints").push().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map map = (Map)snapshot.getValue();
                tv.setText(map.get("Name").toString()+ "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//               Map map = (Map)snapshot.getValue();
//               Log.d("---->",map.get("Email").toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        if(FirebaseDatabase.getInstance().getReference().child("Maintenance").child("Complaints").child("Email").equals(user.getEmail())) {
//            Log.d(">>>>>>",  "inside if");
//            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Maintenance").child("Complaints");
//            reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//                    list.clear();
//                    Log.d(">>>>>>", datasnapshot.exists() + "");
//                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
//                        democ1 info = snapshot.getValue(democ1.class);
//                        String txt_n = info.getName() + ":" + info.getRoom_No();
//                        Log.d(">>>>>", txt_n);
//                        list.add(txt_n);
//
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Log.d(">>>>>", error.toString());
//
//                }
//            });
//        }
//        else{
//            Log.d(">>>>>","Something wrong");
//        }
    }
}