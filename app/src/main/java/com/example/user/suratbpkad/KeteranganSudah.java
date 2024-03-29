package com.example.user.suratbpkad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class KeteranganSudah extends AppCompatActivity {
    private FirebaseDatabase mdata;
    DatabaseReference mdb;
    SharedPreferences peran;
    String mPeran;
    String sPeran;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keterangan_sudah);

        Intent intent = getIntent();
        final String Kunci = intent.getStringExtra("Kunci");

        mdata = FirebaseDatabase.getInstance();
        mdb = mdata.getReference("Surat").child(Kunci);
        peran = getSharedPreferences("peran", 0);
        mPeran = peran.getString("peran1","Kosong");

        if (mPeran.equals("Kabid")){
            sPeran = "Kabid";
        } else if (mPeran.equals("Kasubbid 1") || mPeran.equals("Staff Subbid 1")){
            sPeran = "Subbid 1";
        } else if (mPeran.equals("Kasubbid 2") || mPeran.equals("Staff Subbid 2")) {
            sPeran = "Subbid 2";
        } else if (mPeran.equals("Kasubbid 3") || mPeran.equals("Staff Subbid 3")) {
            sPeran = "Subbid 3";
        }
        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> name = new ArrayList<String>();
                String nama;
                for (DataSnapshot ab : dataSnapshot.child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                    String key2 = ab.getKey();
                    nama = (String) dataSnapshot.child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                    name.add(nama);
                }
                String no_surat = (String) dataSnapshot.child("Nomor Surat").getValue();
                String tanggal_surat = (String) dataSnapshot.child("Tanggal Surat").getValue();
                String tanggal_terima = (String) dataSnapshot.child("Tanggal Terima").getValue();
                String pengirim_surat = (String) dataSnapshot.child("Pengirim").getValue();
                String perihal_surat = (String) dataSnapshot.child("Perihal").getValue();
                String status = (String) dataSnapshot.child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                String memo = (String) dataSnapshot.child("Memo").getValue();
                String output = (String) dataSnapshot.child("Output").getValue();
                String sifat = (String) dataSnapshot.child("Sifat").getValue();

                TextView sifatTxt = (TextView) findViewById(R.id.sft);
                TextView outputTxt = (TextView) findViewById(R.id.opt);
                TextView no_suratTxt = (TextView) findViewById(R.id.nomor_surat);
                TextView tanggal_suratTxt = (TextView) findViewById(R.id.tanggal_surat);
                TextView tanggal_terimaTxt = (TextView) findViewById(R.id.tanggal_terima);
                TextView pengirim_suratTxt = (TextView) findViewById(R.id.pengirim_surat);
                TextView perihal_suratTxt = (TextView) findViewById(R.id.perihal);
                TextView pelaksanaTxt = (TextView) findViewById(R.id.nama_pelaksana);
                TextView statusTxt = (TextView) findViewById(R.id.status_surat);
                TextView memoTxt = (TextView) findViewById(R.id.memo);

                no_suratTxt.setText(no_surat);
                sifatTxt.setText(sifat);
                outputTxt.setText(output);
                tanggal_suratTxt.setText(tanggal_surat);
                tanggal_terimaTxt.setText(tanggal_terima);
                pengirim_suratTxt.setText(pengirim_surat);
                perihal_suratTxt.setText(perihal_surat);
                pelaksanaTxt.setText(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                statusTxt.setText(status);
                memoTxt.setText(memo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button Home = (Button) findViewById(R.id.back_home);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KeteranganSudah.this,HomePage.class);
                startActivity(i);
            }
        });
    }
}
