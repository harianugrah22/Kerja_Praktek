package com.example.user.suratbpkad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KeteranganBaru extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseDatabase mdata;
    DatabaseReference mdb;
    Button mOpenDialogKembalikan;
    SharedPreferences nama;
    String mNama;
    SharedPreferences peran;
    String mPeran;
    String sPeran;
    String vPeran;
    String[] sifat={"- Pilih Sifat -","Sangat Penting","Penting","Normal"};
    Spinner spin;

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
        peran = getSharedPreferences("peran", 0);
        mPeran = peran.getString("peran1","Kosong");

        spin = (Spinner) findViewById(R.id.sif_surat);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,sifat);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        if (mPeran.equals("Kabid")){
            sPeran = "Kabid";
        } else if (mPeran.equals("Kasubbid 1") || mPeran.equals("Staff Subbid 1")){
            sPeran = "Subbid 1";
        } else if (mPeran.equals("Kasubbid 2") || mPeran.equals("Staff Subbid 2")) {
            sPeran = "Subbid 2";
        } else if (mPeran.equals("Kasubbid 3") || mPeran.equals("Staff Subbid 3")) {
            sPeran = "Subbid 3";
        } else{
            sPeran = "Uploader";
        }

        if (mPeran.equals("Kabid")){
            vPeran = "Kabid";
        } else if (mPeran.equals("Kasubbid 1") || mPeran.equals("Kasubbid 2") || mPeran.equals("Kasubbid 3")){
            vPeran= "Kasubbid";
        } else if (mPeran.equals("Staff Subbid 1") || mPeran.equals("Staff Subbid 2") || mPeran.equals("Staff Subbid 3")) {
            vPeran = "Subbid";
        } else{
            vPeran = "Uploader";
        }

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

        if (vPeran.equals("Kabid")){
            Button Teruskan = (Button) findViewById(R.id.teruskan);
            Teruskan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (spin.getSelectedItem().toString().equals("- Pilih Sifat -")){
                        Toast toast = Toast.makeText(getApplicationContext(),"Pilih Sifat Surat Terlebih Dulu", Toast.LENGTH_SHORT);
                        toast.show();
                    } else{
                        String sft = spin.getSelectedItem().toString();
                        Toast toast = Toast.makeText(getApplicationContext(),"Loading ...", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent i = new Intent(KeteranganBaru.this, Teruskan.class);
                        i.putExtra("Kunci",Kunci);
                        i.putExtra("Sifat",sft);
                        startActivity(i);
                    }
                }
            });
            Button Terima = (Button) findViewById(R.id.terima);
            Terima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mdb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (spin.getSelectedItem().toString().equals("- Pilih Sifat -")){
                                Toast toast = Toast.makeText(getApplicationContext(),"Pilih Sifat Surat Terlebih Dulu", Toast.LENGTH_SHORT);
                                toast.show();
                            } else{
                                mdb.child("Sifat").setValue(spin.getSelectedItem().toString());
                                mdb.child("Yang Ditugaskan").child(sPeran).child("Status").setValue("Sedang Diproses");
                                Toast toast = Toast.makeText(getApplicationContext(),"Status Surat Berhasil Diubah", Toast.LENGTH_SHORT);
                                toast.show();
                                Intent i = new Intent(KeteranganBaru.this,HomePage.class);
                                startActivity(i);
                            }
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
        } else if (vPeran.equals("Kasubbid")){
            View a = findViewById(R.id.sifat);
            a.setVisibility(View.GONE);
            Button Teruskan = (Button) findViewById(R.id.teruskan);
            Teruskan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(getApplicationContext(),"Loading ...", Toast.LENGTH_SHORT);
                    toast.show();
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
                            mdb.child("Yang Ditugaskan").child(sPeran).child("Status").setValue("Sedang Diproses");
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
        } else if (vPeran.equals("Subbid")){
            View a = findViewById(R.id.sifat);
            a.setVisibility(View.GONE);
            View b = findViewById(R.id.teruskan);
            b.setVisibility(View.GONE);
            View c = findViewById(R.id.kembalikan);
            c.setVisibility(View.GONE);
            Button Terima = (Button) findViewById(R.id.terima);
            Terima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mdb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            mdb.child("Yang Ditugaskan").child(sPeran).child("Status").setValue("Sedang Diproses");
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
        } else{
            View a = findViewById(R.id.sifat);
            a.setVisibility(View.GONE);
            View b = findViewById(R.id.teruskan);
            b.setVisibility(View.GONE);
            View c = findViewById(R.id.kembalikan);
            c.setVisibility(View.GONE);
            View d = findViewById(R.id.terima);
            d.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
