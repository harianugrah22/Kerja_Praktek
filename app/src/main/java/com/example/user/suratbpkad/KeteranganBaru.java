package com.example.user.suratbpkad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KeteranganBaru extends AppCompatActivity {

    private FirebaseDatabase mdata;
    DatabaseReference mdb;
    Button mOpenDialogKembalikan;
    SharedPreferences nama;
    String mNama;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keterangan_baru);
        Intent intent = getIntent();
        final String Kunci = intent.getStringExtra("Kunci");

        mdata = FirebaseDatabase.getInstance();
        mdb = mdata.getReference("Surat").child(Kunci);
        nama = getSharedPreferences("nama", 0);
        mNama = nama.getString("nama1","Kosong");

        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String no_surat = (String) dataSnapshot.child("Nomor Surat").getValue();
                String tanggal_surat = (String) dataSnapshot.child("Tanggal Surat").getValue();
                String tanggal_terima = (String) dataSnapshot.child("Tanggal Terima").getValue();
                String pengirim_surat = (String) dataSnapshot.child("Pengirim").getValue();
                String perihal_surat = (String) dataSnapshot.child("Perihal").getValue();

                TextView no_suratTxt = (TextView) findViewById(R.id.nomor_surat);
                TextView tanggal_suratTxt = (TextView) findViewById(R.id.tanggal_surat);
                TextView tanggal_terimaTxt = (TextView) findViewById(R.id.tanggal_terima);
                TextView pengirim_suratTxt = (TextView) findViewById(R.id.pengirim_surat);
                TextView perihal_suratTxt = (TextView) findViewById(R.id.perihal);

                no_suratTxt.setText(no_surat);
                tanggal_suratTxt.setText(tanggal_surat);
                tanggal_terimaTxt.setText(tanggal_terima);
                pengirim_suratTxt.setText(pengirim_surat);
                perihal_suratTxt.setText(perihal_surat);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button Teruskan = (Button) findViewById(R.id.teruskan);
        Teruskan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KeteranganBaru.this, Teruskan.class);
                i.putExtra("Kunci",Kunci);
                startActivity(i);
            }
        });
        Button Terima = (Button) findViewById(R.id.terima);
        Terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mdb.child("Status").setValue("Sedang Diproses");
                        Toast toast = Toast.makeText(getApplicationContext(),"Status Surat Berhasil Diubah", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent i = new Intent(KeteranganBaru.this,HomePage.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        mOpenDialogKembalikan=(Button) findViewById(R.id.kembalikan);
        mOpenDialogKembalikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KeteranganBaru.this, Kembalikan.class);
                i.putExtra("Kunci",Kunci);
                startActivity(i);
            }
        });

    }
}
