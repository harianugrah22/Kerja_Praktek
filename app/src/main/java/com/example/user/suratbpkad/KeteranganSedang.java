package com.example.user.suratbpkad;

import android.content.Intent;
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

public class KeteranganSedang extends AppCompatActivity {
    private FirebaseDatabase mdata;
    DatabaseReference mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keterangan_sedang);
        Intent intent = getIntent();
        final String Kunci = intent.getStringExtra("Kunci");

        mdata = FirebaseDatabase.getInstance();
        mdb = mdata.getReference("Surat").child(Kunci);
        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String no_surat = (String) dataSnapshot.child("Nomor Surat").getValue();
                String tanggal_surat = (String) dataSnapshot.child("Tanggal Surat").getValue();
                String tanggal_terima = (String) dataSnapshot.child("Tanggal Terima").getValue();
                String pengirim_surat = (String) dataSnapshot.child("Pengirim").getValue();
                String perihal_surat = (String) dataSnapshot.child("Perihal").getValue();
                String pelaksana = (String) dataSnapshot.child("Yang Ditugaskan").getValue();
                String status = (String) dataSnapshot.child("Status").getValue();
                String memo = (String) dataSnapshot.child("Memo").getValue();

                TextView no_suratTxt = (TextView) findViewById(R.id.nomor_surat);
                TextView tanggal_suratTxt = (TextView) findViewById(R.id.tanggal_surat);
                TextView tanggal_terimaTxt = (TextView) findViewById(R.id.tanggal_terima);
                TextView pengirim_suratTxt = (TextView) findViewById(R.id.pengirim_surat);
                TextView perihal_suratTxt = (TextView) findViewById(R.id.perihal);
                TextView pelaksanaTxt = (TextView) findViewById(R.id.nama_pelaksana);
                TextView statusTxt = (TextView) findViewById(R.id.status_surat);
                TextView memoTxt = (TextView) findViewById(R.id.memo);

                no_suratTxt.setText(no_surat);
                tanggal_suratTxt.setText(tanggal_surat);
                tanggal_terimaTxt.setText(tanggal_terima);
                pengirim_suratTxt.setText(pengirim_surat);
                perihal_suratTxt.setText(perihal_surat);
                pelaksanaTxt.setText(pelaksana);
                statusTxt.setText(status);
                memoTxt.setText(memo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button Review = (Button) findViewById(R.id.sedang_review);
        Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String status = (String) dataSnapshot.child("Status").getValue();
                        if (status.equals("Sedang Direview")){
                            Toast toast = Toast.makeText(getApplicationContext(),"Status Surat = Sedang Direview", Toast.LENGTH_SHORT);
                            toast.show();
                        } else{
                            mdb.child("Status").setValue("Sedang Direview");
                            Toast toast = Toast.makeText(getApplicationContext(),"Status Surat Berhasil Diubah", Toast.LENGTH_SHORT);
                            toast.show();
                            mdb.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String status = (String) dataSnapshot.child("Status").getValue();
                                    TextView statusTxt = (TextView) findViewById(R.id.status_surat);
                                    statusTxt.setText(status);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        Button Upload = (Button) findViewById(R.id.upload_laporan);
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdb.child("Status").setValue("Sedang Diverifikasi");
                Toast toast = Toast.makeText(getApplicationContext(),"Laporan Berhasil Diupload", Toast.LENGTH_SHORT);
                toast.show();
                Intent i = new Intent(KeteranganSedang.this,HomePage.class);
                startActivity(i);
            }
        });
    }
}
