package com.example.hostelmaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScanActivity_IN extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView zXingScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zXingScannerView = new ZXingScannerView(this);
        setContentView(zXingScannerView);

        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        zXingScannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void handleResult(Result result) {
        String qrid = result.getText();
        Intent i = new Intent(this,GatePassVerification_IN.class);
        i.putExtra("QRID_IN",qrid);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }
}