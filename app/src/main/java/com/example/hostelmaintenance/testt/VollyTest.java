package com.example.hostelmaintenance.testt;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hostelmaintenance.R;
import com.google.android.material.snackbar.Snackbar;
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
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class VollyTest extends AppCompatActivity {
    ImageView iv;
    Button b1;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volly_test);
//        iv = findViewById(R.id.qr);
//        b1 = findViewById(R.id.btn);
        drawerLayout=findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        b1.setOnClickListener(e -> {

//            String ran = UUID.randomUUID().toString();
//            //get string value
//            String str = "Gautam Jain";
//            //initializing
//            MultiFormatWriter writer = new MultiFormatWriter();
//            try {
//                BitMatrix matrix = writer.encode(ran, BarcodeFormat.QR_CODE,
//                        350, 350);
//                BarcodeEncoder encoder = new BarcodeEncoder();
//                Bitmap bitmap = encoder.createBitmap(matrix);
//                iv.setImageBitmap(bitmap);
//            } catch (WriterException ex) {
//                ex.printStackTrace();
//            }
        //    Snackbar.make(this,b1.getRootView(),"Please Select Course",Snackbar.LENGTH_SHORT).show();

    //    });

    }
}
