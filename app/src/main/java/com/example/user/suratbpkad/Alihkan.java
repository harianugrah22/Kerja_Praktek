package com.example.user.suratbpkad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Alihkan extends AppCompatActivity {
    SharedPreferences peran;
    private FirebaseDatabase mdata;
    DatabaseReference mdb;
    private ArrayList<Akun> akunArrayList = new ArrayList<>();
    String Kunci;
    String Alasan;
    String mPeran;
    String sPeran;
    private ListView lv;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alihkan);

        peran = getSharedPreferences("peran", 0);
        mPeran = peran.getString("peran1","Kosong");

        Intent intent = getIntent();
        Kunci = intent.getStringExtra("Kunci");
        Alasan = intent.getStringExtra("Alasan");

        switch (mPeran) {
            case "Kasubbid 1":
                sPeran = "Subbid 1";
                break;
            case "Kasubbid 2":
                sPeran = "Subbid 2";
                break;
            case "Kasubbid 3":
                sPeran = "Subbid 3";
                break;
        }

        akunArrayList.clear();
        mdata = FirebaseDatabase.getInstance();
        mdb = mdata.getReference("Users");
        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int i =0;
                    akunArrayList.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String peran = (String) map.get("Peran");
                        if (peran.equals("Kasubbid 1") || peran.equals("Kasubbid 2") || peran.equals("Kasubbid 3")){
                            if (mPeran.equals(peran)){
                            } else {
                                i = i+1;
                                String nama = (String) map.get("Nama");
                                String user = (String) map.get("Username");

                                Akun akun = new Akun();
                                akun.setNamaAd(nama);
                                akun.setPeranAd(peran);
                                akun.setUserAd(user);
                                akunArrayList.add(akun);
                            }
                        }
                    }
                    if (i==0){
                        Akun akun = new Akun();
                        akun.setNamaAd("Belum Ada Akun");
                        akun.setPeranAd("Belum Ada Akun");
                        akun.setUserAd("Belum Ada Akun");
                        akunArrayList.add(akun);
                    }
                    AkunAdapter adapter = new AkunAdapter(Alihkan.this, akunArrayList);
                    lv = (ListView) findViewById(R.id.akun);
                    lv.setAdapter(adapter);

                    Button Teruskan = (Button) findViewById(R.id.alihkan);
                    Teruskan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int b = 0;
                            int c = 0;
                            int d = 0;
                            int e = 0;
                            for (int a = 0; a < AkunAdapter.peranArrayList.size(); a++){
                                if(AkunAdapter.peranArrayList.get(a).getSelected()) {
                                    if (AkunAdapter.peranArrayList.get(a).getUserAd().equals("Belum Ada Akun")){
                                        e = e+1;
                                    } else{
                                        myRef = database.getReference("Surat").child(Kunci);
                                        if (AkunAdapter.peranArrayList.get(a).getPeranAd().equals("Kasubbid 1")){
                                            myRef.child("Yang Ditugaskan").child("Subbid 1").child("Pelaksana").child(Integer.toString(b)).setValue(AkunAdapter.peranArrayList.get(a).getUserAd());
                                            myRef.child("Yang Ditugaskan").child("Subbid 1").child("Status").setValue("Baru Diupload");
                                            b=b+1;
                                        } else if(AkunAdapter.peranArrayList.get(a).getPeranAd().equals("Kasubbid 2")){
                                            myRef.child("Yang Ditugaskan").child("Subbid 2").child("Pelaksana").child(Integer.toString(c)).setValue(AkunAdapter.peranArrayList.get(a).getUserAd());
                                            myRef.child("Yang Ditugaskan").child("Subbid 2").child("Status").setValue("Baru Diupload");
                                            c=c+1;
                                        } else if(AkunAdapter.peranArrayList.get(a).getPeranAd().equals("Kasubbid 3")){
                                            myRef.child("Yang Ditugaskan").child("Subbid 3").child("Pelaksana").child(Integer.toString(d)).setValue(AkunAdapter.peranArrayList.get(a).getUserAd());
                                            myRef.child("Yang Ditugaskan").child("Subbid 3").child("Status").setValue("Baru Diupload");
                                            d=d+1;
                                        }
                                        myRef.child("Yang Ditugaskan").child(sPeran).child("Status").setValue("Dialihkan");
                                    }
                                }
                            }
                            if (b==0 && c==0 && d==0 && e==0){
                                Toast toa = Toast.makeText(getApplicationContext(), "Belum Ada Akun Yang Dipilih", Toast.LENGTH_SHORT);
                                toa.show();
                            }  else if (e!=0){
                                Toast toa = Toast.makeText(getApplicationContext(), "Belum Ada Akun Dalam Database", Toast.LENGTH_SHORT);
                                toa.show();
                            } else{
                                Toast toa = Toast.makeText(getApplicationContext(), "Berhasil Dialihkan", Toast.LENGTH_SHORT);
                                toa.show();
                                Intent home = new Intent(Alihkan.this, HomePage.class);
                                startActivity(home);
                            }
                        }
                    });

                } else {
                    akunArrayList.clear();
                    Akun akun = new Akun();
                    akun.setNamaAd("Belum Ada Akun");
                    akun.setPeranAd("Belum Ada Akun");
                    akun.setUserAd("Belum Ada Akun");
                    akunArrayList.add(akun);

                    AkunAdapter adapter = new AkunAdapter(Alihkan.this, akunArrayList);
                    lv = (ListView) findViewById(R.id.akun);
                    lv.setAdapter(adapter);

                    Button Teruskan = (Button) findViewById(R.id.alihkan);
                    Teruskan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast toa = Toast.makeText(getApplicationContext(), "Tidak Ada Akun Dalam database", Toast.LENGTH_SHORT);
                            toa.show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
