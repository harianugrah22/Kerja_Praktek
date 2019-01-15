package com.example.user.suratbpkad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;
import java.util.Map;

public class UploadSuratBaru extends AppCompatActivity {

    Button mOpenDialog;
    EditText mNomorSurat;
    EditText mTanggalSurat;
    EditText mTanggalTerima;
    EditText mPengirim;
    EditText mPerihal;
    EditText mMemo;
    Button mUpload;
    Button mHome;
    Button mClear;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    DatabaseReference mData;
    String kesediaan;


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
        mHome= (Button) findViewById(R.id.homepage);
        mClear = (Button) findViewById(R.id.clear);

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
                if (mNomorSurat.getText().length() == 0 || mTanggalSurat.getText().length() == 0 || mTanggalTerima.getText().length() == 0
                        || mPengirim.getText().length() == 0 || mPerihal.getText().length() == 0)
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Terdapat Bagian yang Belum Diisi";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                {
                    CekKesediaan();
                }
            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNomorSurat.setText("");
                mTanggalSurat.setText("");
                mTanggalTerima.setText("");
                mPengirim.setText("");
                mPerihal.setText("");
                mMemo.setText("");
            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UploadSuratBaru.this, HomePage.class);
                startActivity(i);
            }
        });
    }

    public void CekKesediaan(){
        mData = database.getReference("Surat");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Kesediaan(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void Kesediaan(DataSnapshot dataSnapshot){
        String nomor_surat;
        String pengirim;
        String tanggal_surat;
        if (dataSnapshot.exists()){
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Map<String, Object> map = (Map<String, Object>) ds.getValue();
                nomor_surat = (String) map.get("Nomor Surat");
                pengirim = (String) map.get("Pengirim");
                tanggal_surat = (String) map.get("Tanggal Surat");
                 if (nomor_surat.equals(mNomorSurat.getText().toString()) && pengirim.equals(mPengirim.getText().toString())
                         && tanggal_surat.equals(mTanggalSurat.getText().toString())){
                    kesediaan = "No";
                    break;
                } else{
                     kesediaan = "Yes";
                 }
            }
            if (kesediaan.equals("Yes")){
                myRef = database.getReference("Surat").push();
                myRef.child("Status").setValue("Baru Diupload");
                myRef.child("Nomor Surat").setValue(mNomorSurat.getText().toString());
                myRef.child("Tanggal Surat").setValue(mTanggalSurat.getText().toString());
                myRef.child("Tanggal Terima").setValue(mTanggalTerima.getText().toString());
                myRef.child("Pengirim").setValue(mPengirim.getText().toString());
                myRef.child("Perihal").setValue(mPerihal.getText().toString());
                myRef.child("Sifat").setValue("Belum Ada");
                myRef.child("Yang Ditugaskan").setValue("Belum Ada");

                if (mMemo.getText().length() == 0)
                {
                    myRef.child("Memo").setValue("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore e");
                } else{
                    myRef.child("Memo").setValue(mMemo.getText().toString());
                }

                Context context = getApplicationContext();
                CharSequence text = "Surat Berhasil Diupload";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            } else {
                Context context = getApplicationContext();
                CharSequence text = "Surat Telah Ada Dalam Database";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        } else{
            myRef = database.getReference("Surat").push();
            myRef.child("Status").setValue("Baru Diupload");
            myRef.child("Nomor Surat").setValue(mNomorSurat.getText().toString());
            myRef.child("Tanggal Surat").setValue(mTanggalSurat.getText().toString());
            myRef.child("Tanggal Terima").setValue(mTanggalTerima.getText().toString());
            myRef.child("Pengirim").setValue(mPengirim.getText().toString());
            myRef.child("Perihal").setValue(mPerihal.getText().toString());
            myRef.child("Sifat").setValue("Belum Ada");
            myRef.child("Yang Ditugaskan").setValue("Belum Ada");

            if (mMemo.getText().length() == 0)
            {
                myRef.child("Memo").setValue("Kosong");
            } else{
                myRef.child("Memo").setValue(mMemo.getText().toString());
            }

            Context context = getApplicationContext();
            CharSequence text = "Surat Berhasil Diupload";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
