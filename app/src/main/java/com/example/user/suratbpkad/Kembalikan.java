package com.example.user.suratbpkad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Kembalikan extends AppCompatActivity {

    SharedPreferences peran;
    String mPeran;
    String sPeran;
    String uploader;
    String pran;
    EditText Alasan;
    String mAlasan;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kembalikan);

        Intent intent = getIntent();
        final String Kunci = intent.getStringExtra("Kunci");

        peran = getSharedPreferences("peran", 0);
        mPeran = peran.getString("peran1","Kosong");

        if (mPeran.equals("Kabid")){
            sPeran = "Kabid";
            View a = findViewById(R.id.alihkan);
            a.setVisibility(View.GONE);
        } else if (mPeran.equals("Kasubbid 1")){
            sPeran = "Subbid 1";
        } else if (mPeran.equals("Kasubbid 2")) {
            sPeran = "Subbid 2";
        } else if (mPeran.equals("Kasubbid 3")) {
            sPeran = "Subbid 3";
        }
        Button mKembalikan = (Button) findViewById(R.id.kembalikan);
        mKembalikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alasan = (EditText) findViewById(R.id.alasan);
                mAlasan = Alasan.getText().toString();
                if (mAlasan.length()==0){
                    Toast tst = Toast.makeText(getApplicationContext(),"Alasan Belum Diisi", Toast.LENGTH_SHORT);
                    tst.show();
                } else if (mPeran.equals("Kabid")){
                    FirebaseDatabase mdata = FirebaseDatabase.getInstance();
                    final DatabaseReference mdb = mdata.getReference("Surat").child(Kunci);
                    DatabaseReference mak = mdata.getReference("Users");
                    mak.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                    Map<String, Object> map1 = (Map<String, Object>) ds1.getValue();
                                    pran = (String) map1.get("Peran");
                                    if (pran.equals("Uploader")){
                                        i= i+1;
                                        uploader = (String) map1.get("Username");
                                        mdb.child("Yang Ditugaskan").child("Uploader").child("Pelaksana").child(Integer.toString(i)).setValue(uploader);
                                        mdb.child("Yang Ditugaskan").child("Uploader").child("Status").setValue("Ditolak");
                                        mdb.child("Yang Ditugaskan").child(sPeran).child("Status").setValue("Ditolak");
                                        mdb.child("Memo").setValue(mAlasan);
                                        if (i==1){
                                            Toast tst = Toast.makeText(getApplicationContext(),"Berhasil Dikembalikan", Toast.LENGTH_SHORT);
                                            tst.show();
                                        }
                                    }
                                }
                                Intent home = new Intent (Kembalikan.this, HomePage.class);
                                startActivity(home);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                } else if (mPeran.equals("Kasubbid 1") || mPeran.equals("Kasubbid 2") || mPeran.equals("Kasubbid 3")){
                    final FirebaseDatabase mdata = FirebaseDatabase.getInstance();
                    final DatabaseReference mdb = mdata.getReference("Surat").child(Kunci);
                    DatabaseReference mak = mdata.getReference("Users");
                    mak.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                    Map<String, Object> map1 = (Map<String, Object>) ds1.getValue();
                                    pran = (String) map1.get("Peran");
                                    if (pran.equals("Kabid")){
                                        uploader = (String) map1.get("Username");
                                        mdb.child("Sifat").setValue("Belum Ada");
                                        mdb.child("Yang Ditugaskan").child("Kabid").child("Pelaksana").child(Integer.toString(i)).setValue(uploader);
                                        mdb.child("Yang Ditugaskan").child("Kabid").child("Status").setValue("Dikembalikan");
                                        mdb.child("Yang Ditugaskan").child(sPeran).child("Status").setValue("Dikembalikan");
                                        mdb.child("Memo").setValue(mAlasan);
                                        Toast tst = Toast.makeText(getApplicationContext(),"Berhasil Dikembalikan", Toast.LENGTH_SHORT);
                                        tst.show();
                                    }
                                }
                                Intent home = new Intent (Kembalikan.this, HomePage.class);
                                startActivity(home);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
        });

        Button mOpenDialogAlihkan=(Button) findViewById(R.id.alihkan);
        mOpenDialogAlihkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alasan = (EditText) findViewById(R.id.alasan);
                mAlasan = Alasan.getText().toString();
                if (mAlasan.length()==0){
                    Toast tst = Toast.makeText(getApplicationContext(),"Alasan Belum Diisi", Toast.LENGTH_SHORT);
                    tst.show();
                } else {
                    Intent i = new Intent(Kembalikan.this, Alihkan.class);
                    i.putExtra("Kunci", Kunci);
                    i.putExtra("Alasan", mAlasan);
                    startActivity(i);
                }
            }
        });
    }
}
