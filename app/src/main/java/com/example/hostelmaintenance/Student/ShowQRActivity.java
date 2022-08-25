package com.example.hostelmaintenance.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hostelmaintenance.GetLeaveData;
import com.example.hostelmaintenance.IncomingLeaveAdapter;
import com.example.hostelmaintenance.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.nio.channels.MulticastChannel;

public class ShowQRActivity extends AppCompatActivity {
    String qrs;
    ImageView qr;
    private GetLeaveData qr_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qractivity);
        Intent intent = getIntent();
        try{
            qr= findViewById(R.id.qrshow);
            qr_data = (GetLeaveData) intent.getSerializableExtra("QR_Code");
            qrs= qr_data.getQRCode();
            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(qrs, BarcodeFormat.QR_CODE,700,700);
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.createBitmap(matrix);
                qr.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Gate Pass Not generated yet", Toast.LENGTH_SHORT).show();
        }


    }
}