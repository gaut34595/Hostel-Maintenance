package com.example.hostelmaintenance.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hostelmaintenance.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class LeaveRegistrationActivity extends AppCompatActivity {
    EditText enroll,student_name,stud_cont,course,roomNo,Finger_no,father_name,father_cont,
            noofdays,reason,add_leave;
    String student_email,student_enroll,std_name,std_cont,std_course,std_room,std_finger,std_father,
            leave_f,leave_t,fat_cont,no_of_days,leave_reason,leave_add,stud_dept,stud_hostel,stud_college;
    String imageurl;
    int IsVerifiedbyCC,IsVerifiedbyHOD,IsVerifiedbyHW;

    int gateout,gatein;
    EditText leaveFrom,leaveTo;
    Button submit;
    DatePickerDialog picker;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_registration);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        auth = FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        enroll= findViewById(R.id.student_enrol);
        student_name= findViewById(R.id.student_name);
        stud_cont= findViewById(R.id.stud_contact);
        course= findViewById(R.id.stud_program);
        roomNo= findViewById(R.id.stud_room);
        Finger_no= findViewById(R.id.stud_finger);
        father_name= findViewById(R.id.stud_father);
        father_cont= findViewById(R.id.student_father_cont);
        leaveFrom = findViewById(R.id.leavefrom);
        leaveTo = findViewById(R.id.leaveto);
        noofdays= findViewById(R.id.num_days);
        reason= findViewById(R.id.reason_leave);
        add_leave= findViewById(R.id.add_leave);
        submit=findViewById(R.id.button_leave);
        fetchdata();

        // registering complaint button

        submit.setOnClickListener(e->{
            progressDialog= new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Submitting Leave");
                    student_email=user.getEmail();
                    student_enroll = enroll.getText().toString();
                    std_name= student_name.getText().toString();
                    std_cont= stud_cont.getText().toString();
                    std_course=course.getText().toString();
                    std_room=roomNo.getText().toString();
                    std_finger=Finger_no.getText().toString();
                    std_father=father_name.getText().toString();
                    leave_f= leaveFrom.getText().toString();
                    leave_t = leaveTo.getText().toString();
                    fat_cont = father_cont.getText().toString();
                    no_of_days = noofdays.getText().toString();
                    leave_reason = reason.getText().toString();
                    leave_add = add_leave.getText().toString();
                    IsVerifiedbyCC= 0;
                    IsVerifiedbyHOD  = 0;
                    IsVerifiedbyHW= 0;
                    gatein=0;
                    gateout = 0;

                    FirebaseFirestore db= FirebaseFirestore.getInstance();
                    if(TextUtils.isEmpty(leave_f) ||TextUtils.isEmpty(leave_t) ||
                     TextUtils.isEmpty(no_of_days) ||  TextUtils.isEmpty(leave_reason)
                    || TextUtils.isEmpty(leave_add)){
                        Toast.makeText(this, "All details are mandatory", Toast.LENGTH_SHORT).show();
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                    else{
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("Student_Email",student_email);
                        map.put("Student_Enrollment",student_enroll);
                        map.put("Student_Name",std_name);
                        map.put("Student_Contact",std_cont);
                        map.put("Student_Course",std_course);
                        map.put("Room_No",std_room);
                        map.put("Finger_No",std_finger);
                        map.put("Father_Name",std_father);
                        map.put("Leave_From",leave_f);
                        map.put("Leave_to",leave_t);
                        map.put("Father_Contact",fat_cont);
                        map.put("No_of_Days",no_of_days);
                        map.put("Leave_Reason",leave_reason);
                        map.put("Leave_Address",leave_add);
                        map.put("Verified_CC",IsVerifiedbyCC);
                        map.put("Verified_HOD",IsVerifiedbyHOD);
                        map.put("Verified_HW",IsVerifiedbyHW);
                        map.put("QRCode","");
                        map.put("ImageLink",imageurl);
                        map.put("Gate_Validation_Out",gateout);
                        map.put("Gate_Validation_In",gatein);
                        map.put("Gate_Validation_Out_Time","");
                        map.put("Gate_Validation_In_Time","");
                        map.put("Late_by","");
                        map.put("Student_Department",stud_dept);
                        map.put("Student_Hostel",stud_hostel);
                        map.put("Student_College",stud_college);


                        db.collection("Student_Leaves").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                              if(task.isSuccessful()){
                                  Toast.makeText(LeaveRegistrationActivity.this, "Leave Registered Successfully", Toast.LENGTH_SHORT).show();
                                  if(progressDialog.isShowing()){
                                      progressDialog.dismiss();
                                  }
                                  Intent i = new Intent(LeaveRegistrationActivity.this,LeaveActivity.class);
                                  startActivity(i);
                                  finish();
                              }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                    Toast.makeText(LeaveRegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }


        });
        // Date Picker for leave from
        leaveFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Date Picker

                picker = new DatePickerDialog(LeaveRegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        leaveFrom.setText(i2 + "/" + (i1+1) + "/" + i);
                    }
                },year,month,day);
                picker.show();
            }
        });

        // Date Picker for leave to
        leaveTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Date Picker

                picker = new DatePickerDialog(LeaveRegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        leaveTo.setText(i2 + "/" + (i1+1) + "/" + i);
                    }
                },year,month,day);
                picker.show();
            }
        });

    }

    private void fetchdata() {
        DocumentReference doc = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    enroll.setText(documentSnapshot.getString("Enrollment_No"));
                    student_name.setText(documentSnapshot.getString("Name"));
                    stud_cont.setText(documentSnapshot.getString("Student_Cont"));
                    course.setText(documentSnapshot.getString("Student_Course"));
                    roomNo.setText(documentSnapshot.getString("Room_No"));
                    Finger_no.setText(documentSnapshot.getString("Finger_No"));
                    father_name.setText(documentSnapshot.getString("Father's_Name"));
                    father_cont.setText(documentSnapshot.getString("Father_Cont"));
                    imageurl= documentSnapshot.getString("Image");
                    stud_dept = documentSnapshot.getString("Student_Department");
                    stud_hostel = documentSnapshot.getString("Student_Hostel");
                    stud_college = documentSnapshot.getString("College");


                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    leaveTo.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String leavefromdate = leaveFrom.getText().toString();
                            String leavedateto = leaveTo.getText().toString();
                            try{
                                Date d1 = dateFormat.parse(leavefromdate);
                                Date d2 = dateFormat.parse(leavedateto);
                                long diff = d2.getTime() - d1.getTime();
                                if((int)TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS)==0){
                                    noofdays.setText("1");
                                }
                                else{
                                    noofdays.setText((int)TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS)+ "");
                                }

                            } catch (ParseException e) {
                                Toast.makeText(LeaveRegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                else{
                    Toast.makeText(LeaveRegistrationActivity.this, "Row not found", Toast.LENGTH_SHORT).show();

                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            Toast.makeText(LeaveRegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}