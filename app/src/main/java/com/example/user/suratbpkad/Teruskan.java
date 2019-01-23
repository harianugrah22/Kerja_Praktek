package com.example.user.suratbpkad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.chrono.ThaiBuddhistEra;
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
    String Kunci;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teruskan);

        peran = getSharedPreferences("peran", 0);
        mPeran = peran.getString("peran1","Kosong");

        Intent intent = getIntent();
        Kunci = intent.getStringExtra("Kunci");

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
                            String user = (String) map.get("Username");

                            Akun akun = new Akun();
                            akun.setNamaAd(nama);
                            akun.setPeranAd(peran);
                            akun.setUserAd(user);
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

                    Button Teruskan = (Button) findViewById(R.id.teruskan);
                    Teruskan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int b = 0;
                            for (int a = 0; a < AkunAdapter.peranArrayList.size(); a++){
                                if(AkunAdapter.peranArrayList.get(a).getSelected()) {
                                    myRef = database.getReference("Surat").child(Kunci).child("Yang Ditugaskan");
                                    myRef.child(Integer.toString(b)).setValue(AkunAdapter.peranArrayList.get(a).getNamaAd());
                                    b=b+1;
                                }
                            }
                            if (b==0){
                                Toast toa = Toast.makeText(getApplicationContext(), "Belum Ada Akun Yang Dipilih", Toast.LENGTH_SHORT);
                                toa.show();
                            } else{
                                Toast toa = Toast.makeText(getApplicationContext(), "Berhasil Diteruskan", Toast.LENGTH_SHORT);
                                toa.show();
                                Intent home = new Intent(com.example.user.suratbpkad.Teruskan.this, HomePage.class);
                                startActivity(home);
                            }
                        }
                    });

                } else {
                    akunArrayList.clear();
                    Akun akun = new Akun();
                    akun.setNamaAd("Belum Ada Akun");
                    akun.setPeranAd("Belum Ada Akun");
                    akunArrayList.add(akun);

                    AkunAdapter adapter = new AkunAdapter(Teruskan.this, akunArrayList);
                    lv = (ListView) findViewById(R.id.akun);
                    lv.setAdapter(adapter);

                    Button Teruskan = (Button) findViewById(R.id.teruskan);
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
