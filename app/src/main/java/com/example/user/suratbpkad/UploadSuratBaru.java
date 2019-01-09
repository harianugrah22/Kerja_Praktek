package com.example.user.suratbpkad;

import android.app.Activity;
import android.app.FragmentManager;
import android.nfc.Tag;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UploadSuratBaru extends AppCompatActivity {

    Button mOpenDialog;
    EditText mNomorSurat;
    EditText mTanggalSurat;
    EditText mTanggalTerima;
    EditText mPengirim;
    EditText mPerihal;
    EditText mMemo;
    Button mUpload;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_surat_baru);
        mOpenDialog=(Button) findViewById(R.id.upload_file);
        mNomorSurat=(EditText) findViewById(R.id.nomor_surat);
        mTanggalSurat=(EditText) findViewById(R.id.tanggal_surat);
        mTanggalTerima=(EditText) findViewById(R.id.tanggal_terima);
        mPengirim=(EditText) findViewById(R.id.pengirim_surat);
        mPerihal=(EditText) findViewById(R.id.perihal);
        mMemo=(EditText) findViewById(R.id.memo);
        mUpload=(Button) findViewById(R.id.upload);

        mOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PilihFile pilihFile = new PilihFile();
                pilihFile.show(getFragmentManager(),"Pilih File");
            }
        });

        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Surat").push();
                myRef.child("Nomor Surat").setValue(mNomorSurat.getText().toString());
                myRef.child("Tanggal Surat").setValue(mTanggalSurat.getText().toString());
                myRef.child("Tanggal Terima").setValue(mTanggalTerima.getText().toString());
                myRef.child("Pengirim").setValue(mPengirim.getText().toString());
                myRef.child("Perihal").setValue(mPerihal.getText().toString());
                myRef.child("Memo").setValue(mMemo.getText().toString());
                myRef.child("Status").setValue("Baru");
            }
        });
    }
}
