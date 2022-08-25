package com.example.hostelmaintenance.Hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hostelmaintenance.GetLeaveData;
import com.example.hostelmaintenance.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class FinalIncomingActivity extends AppCompatActivity {
    String stud_enroll,stud_name,stud_course
            ,leave_from,leave_to,in_Date,room_no,finger;
    EditText stud_enroll_text,stud_name_text,stud_course_text,leave_from_text,leave_to_text,roomn_text,
    fingern_text,leavein_text;
    Button accept;
    ImageView userimage;
    String imageurl;
    private FirebaseFirestore db;
    private GetLeaveData incoming_data;
    DatePickerDialog picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_incoming);
        Intent i = getIntent();
        int num = -1;
        stud_enroll_text= findViewById(R.id.incoming_student_enrol);
        stud_name_text= findViewById(R.id.incoming_student_name);
        stud_course_text= findViewById(R.id.incoming_stud_program);
        leave_from_text= findViewById(R.id.incoming_leavefrom);
        leave_to_text= findViewById(R.id.incoming_leaveto);
        roomn_text= findViewById(R.id.incoming_roomNo);
        fingern_text = findViewById(R.id.incoming_finger);
        leavein_text = findViewById(R.id.incoming_indate);
        accept= findViewById(R.id.button_accept);
        userimage= findViewById(R.id.hosuser_image);

        incoming_data= (GetLeaveData) i.getSerializableExtra("Incoming_Item");
        db= FirebaseFirestore.getInstance();

        stud_enroll= incoming_data.getStudent_Enrollment();
        stud_name = incoming_data.getStudent_Name();
        stud_course = incoming_data.getStudent_Course();
        leave_from = incoming_data.getLeave_From();
        leave_to = incoming_data.getLeave_to();
        room_no= incoming_data.getRoom_No();
        finger = incoming_data.getFinger_No();
        imageurl= incoming_data.getImageLink();

        Picasso.get().load(imageurl).into(userimage);
        stud_enroll_text.setText(stud_enroll);
        stud_name_text.setText(stud_name);
        stud_course_text.setText(stud_course);
        leave_from_text.setText(leave_from);
        leave_to_text.setText(leave_to);
        fingern_text.setText(finger);
        roomn_text.setText(room_no);


        accept.setOnClickListener(e-> {
            in_Date = leavein_text.getText().toString();
            String late = null;
            if (TextUtils.isEmpty(in_Date)) {
                Toast.makeText(this, "Please fill the in-Date", Toast.LENGTH_SHORT).show();
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String leavedateto = leave_to_text.getText().toString();
                String leaveindate = leavein_text.getText().toString();
                try {
                    Date d1 = dateFormat.parse(leaveindate);
                    Date d2 = dateFormat.parse(leavedateto);
                    long diff = d1.getTime() - d2.getTime();
                    late = String.valueOf((int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + "");
                } catch (ParseException a) {
                    a.printStackTrace();
                }
            }
            DocumentReference dd = db.collection("Student_Leaves").document(incoming_data.getId());
            HashMap<String, Object> map = new HashMap<>();
            map.put("Late_by", late);
            map.put("Verified_HW", num);
            map.put("Gate_Validation_In", num);
            map.put("QRCode", FieldValue.delete());
            dd.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(FinalIncomingActivity.this, "Student Incoming Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(FinalIncomingActivity.this, StudentIncomingActivity.class);
                    startActivity(i);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(FinalIncomingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });

        leavein_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(FinalIncomingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        leavein_text.setText(i2 + "/" + (i1+1) + "/" + i);
                    }
                },year,month,day);
                picker.show();

            }
        });








    }

}