package com.example.hostelmaintenance.testt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.example.hostelmaintenance.College.CC_Verify_Leave;
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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PdfActivity extends AppCompatActivity {
    ArrayList<GetLeaveData> report;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    FirebaseUser user;
    File file,pFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                Log.d(">>>>>>",Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/"+ System.currentTimeMillis() +".pdf");
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/"+ System.currentTimeMillis() +".pdf");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                // intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        });
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        report = new ArrayList<>();
        file=new File("/storage/emulated/0/Download/");
        if ( !file.exists()) {
            file.mkdirs();
        }
        pFile = new File(file, System.currentTimeMillis()+".pdf");
        FirebaseFirestore.getInstance().collection("Student_Leaves")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()!=0){
                            for(DocumentChange ds: queryDocumentSnapshots.getDocumentChanges()){
                                GetLeaveData geta = ds.getDocument().toObject(GetLeaveData.class);
                                report.add(geta);
                                try {
                                    createpdf(report);


                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                            }
                            Log.d(">>>>>>",report.size()+"");

                        }

                    }

                });



    }

    private void createpdf(ArrayList<GetLeaveData> report)throws DocumentException, FileNotFoundException {


        Log.d(">>>>>>","inside report");
        BaseColor colorWhite = WebColors.getRGBColor("#ffffff");
        BaseColor colorBlue = WebColors.getRGBColor("#056FAA");
        BaseColor grayColor = WebColors.getRGBColor("#425066");



        Font white = new Font(Font.FontFamily.HELVETICA, 15.0f, Font.BOLD, colorWhite);
        FileOutputStream output = new FileOutputStream(pFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{6, 25, 40, 20});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        Chunk noText = new Chunk("No.", white);
        PdfPCell noCell = new PdfPCell(new Phrase(noText));
        noCell.setFixedHeight(50);
        noCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        noCell.setVerticalAlignment(Element.ALIGN_CENTER);

        Chunk nameText = new Chunk("First Name", white);
        PdfPCell nameCell = new PdfPCell(new Phrase(nameText));
        nameCell.setFixedHeight(50);
        nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        nameCell.setVerticalAlignment(Element.ALIGN_CENTER);

        Chunk phoneText = new Chunk("Other Names", white);
        PdfPCell phoneCell = new PdfPCell(new Phrase(phoneText));
        phoneCell.setFixedHeight(50);
        phoneCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        phoneCell.setVerticalAlignment(Element.ALIGN_CENTER);

        Chunk amountText = new Chunk("Phone Number", white);
        PdfPCell amountCell = new PdfPCell(new Phrase(amountText));
        amountCell.setFixedHeight(50);
        amountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        amountCell.setVerticalAlignment(Element.ALIGN_CENTER);


        Chunk footerText = new Chunk("Moses Njoroge - Copyright @ 2020");
        PdfPCell footCell = new PdfPCell(new Phrase(footerText));
        footCell.setFixedHeight(70);
        footCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        footCell.setVerticalAlignment(Element.ALIGN_CENTER);
        footCell.setColspan(4);


        table.addCell(noCell);
        table.addCell(nameCell);
        table.addCell(phoneCell);
        table.addCell(amountCell);
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();


        for (PdfPCell cell : cells) {
            cell.setBackgroundColor(grayColor);
        }
        for (int i = 0; i < report.size(); i++) {
            GetLeaveData pay = report.get(i);

            String id = String.valueOf(i + 1);
            String name = pay.getStudent_Name();
            Log.d(">>>>>>",name);
            String sname =pay.getStudent_Email();
            String phone = pay.getStudent_Contact();


            table.addCell(id + ". ");
            table.addCell(name);
            table.addCell(sname);
            table.addCell(phone);

        }

        PdfPTable footTable = new PdfPTable(new float[]{6, 20, 30, 20});
        footTable.setTotalWidth(PageSize.A4.getWidth());
        footTable.setWidthPercentage(100);
        footTable.addCell(footCell);

        PdfWriter.getInstance(document, output);
        document.open();
        Font g = new Font(Font.FontFamily.HELVETICA, 25.0f, Font.NORMAL, grayColor);
        document.add(new Paragraph(" Teerthanker Mahaveer University\n\n", g));
        document.add(table);
        document.add(footTable);
        document.close();





    }
}