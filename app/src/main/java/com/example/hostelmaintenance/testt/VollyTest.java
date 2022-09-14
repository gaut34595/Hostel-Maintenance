package com.example.hostelmaintenance.testt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hostelmaintenance.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class VollyTest extends AppCompatActivity {
    EditText ed1;
    Spinner spinner;
    Button add;
    DatabaseReference dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volly_test);
        ed1 = findViewById(R.id.ed1);
        spinner= findViewById(R.id.spin);
        add = findViewById(R.id.btn);
        dbref= FirebaseDatabase.getInstance().getReference("College");
        add.setOnClickListener(e->{
            insertdata();
        });

        fetchdata();
      //  send();

    }

//    private void send() {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("from","TMU-HOSTEL <hostel@warden.tmuhostel.me>")
//                .addFormDataPart("to","jain23771@gmail.com")
//                .addFormDataPart("to","jain23771@gmail.com")
//                .addFormDataPart("subject","Hello Man")
//                .addFormDataPart("text","Testing some Mailgun awesomeness!")
//                .build();
//        Request request = new Request.Builder()
//                .url("https://api.mailgun.net/v3/warden.tmuhostel.me/messages")
//                .method("POST", body)
//                .addHeader("Authorization", "Basic YXBpOjkxMGIwNjYxY2EyZDYwZmUzN2JlNTI0MDAxNDBlYzc5LTY4MGJjZDc0LTJhMTdjOWNh")
//                .build();
//        Response response = client.newCall(request).execute();
//    }

    private void fetchdata() {

    }

    private void insertdata() {
        
        String d = ed1.getText().toString();
        dbref.push().setValue(d).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ed1.setText("");
                Toast.makeText(VollyTest.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
