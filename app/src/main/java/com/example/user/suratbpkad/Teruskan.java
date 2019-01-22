package com.example.user.suratbpkad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Teruskan extends AppCompatActivity {
    SharedPreferences peran;
    String mPeran;
    private FirebaseDatabase mdata;
    DatabaseReference mdb;
    private ListView lv;
    private ArrayList<Akun> akunArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teruskan);

        peran = getSharedPreferences("peran", 0);
        mPeran = peran.getString("peran1","Kosong");

        Tampilan_Kabid();
    }

    public void Tampilan_Kabid(){
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
                            i = i+1;
                            String key = ds.getKey();
                            String nama = (String) map.get("Nama");

                            Akun akun = new Akun();
                            akun.setNamaAd(nama);
                            akun.setPeranAd(peran);
                            akunArrayList.add(akun);
                        }
                    }
                    if (i==0){
                        Akun akun = new Akun();
                        akun.setNamaAd("Belum Ada Akun");
                        akun.setPeranAd("Belum Ada Akun");
                        akunArrayList.add(akun);
                    }
                    AkunAdapter adapter = new AkunAdapter(Teruskan.this, akunArrayList);
                    lv = (ListView) findViewById(R.id.akun);
                    lv.setAdapter(adapter);

                } else {
                    akunArrayList.clear();
                    Akun akun = new Akun();
                    akun.setNamaAd("Belum Ada Akun");
                    akun.setPeranAd("Belum Ada Akun");
                    akunArrayList.add(akun);

                    AkunAdapter adapter = new AkunAdapter(Teruskan.this, akunArrayList);
                    lv = (ListView) findViewById(R.id.akun);
                    lv.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
