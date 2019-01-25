package com.example.user.suratbpkad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class DisposisiKabid extends AppCompatActivity {
    private FirebaseDatabase mdata;
    ArrayList<Surat> surats = new ArrayList<>();
    DatabaseReference mdb;
    SharedPreferences users;
    String mUser;
    SharedPreferences peran;
    String mPeran;
    String sPeran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disposisi_kabid);

        Intent intent = getIntent();
        final String Konteks = intent.getStringExtra("Konteks");

        if (Konteks.equals("Baru")){
            Tampilan_Baru();
        } else if (Konteks.equals("Diproses")){
            Tampilan_Diproses();
        } else if (Konteks.equals("Selesai")){
            Tampilan_Selesai();
        } else if (Konteks.equals("Verifikasi")){
            Tampilan_Verifikasi();
        } else{
            Toast toast = Toast.makeText(getApplicationContext(), "Gagal Dimuat", Toast.LENGTH_SHORT);
            toast.show();
        }

        Button refresh = (Button) findViewById(R.id.refresh_button);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Konteks.equals("Baru")){
                    Tampilan_Baru();
                    Toast toast = Toast.makeText(getApplicationContext(), "Berhasil Direfresh", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (Konteks.equals("Diproses")){
                    Tampilan_Diproses();
                    Toast toast = Toast.makeText(getApplicationContext(), "Berhasil Direfresh", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (Konteks.equals("Selesai")){
                    Tampilan_Selesai();
                    Toast toast = Toast.makeText(getApplicationContext(), "Berhasil Direfresh", Toast.LENGTH_SHORT);
                    toast.show();
                } else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Gagal Direfresh", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    public void Tampilan_Baru(){
        surats.clear();
        mdata = FirebaseDatabase.getInstance();
        mdb = mdata.getReference("Surat");
        users= getSharedPreferences("user",0);
        mUser = users.getString("user1","Kosong");
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
        } else {
            sPeran = "Uploader";
        }
        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int i =0;
                        final String kunci[] = new String[9999];
                        ArrayList<String> name = new ArrayList<String>();
                        String nama;
                        String nama2;
                        surats.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Dikembalikan")) {
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()) {
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Belum Ada")) {
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()) {
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i] = key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i = i + 1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Map<String, Object> map = (Map<String, Object>) ds.getValue();
                            String key = ds.getKey();
                            String sifat = (String) map.get("Sifat");
                            String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                            if (status.equals("Baru Diupload")) {
                                for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()) {
                                    String key2 = ab.getKey();
                                    nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                    if (nama.equals(mUser) && sifat.equals("Belum Ada")) {
                                        name.clear();
                                        for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()) {
                                            String key3 = bc.getKey();
                                            nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                            name.add(nama2);
                                        }
                                        kunci[i] = key;
                                        String perihal = (String) map.get("Perihal");
                                        String nomor_surat = (String) map.get("Nomor Surat");
                                        String pengirim = (String) map.get("Pengirim");
                                        String tanggal_surat = (String) map.get("Tanggal Surat");
                                        String tanggal_terima = (String) map.get("Tanggal Terima");

                                        Surat surat = new Surat();
                                        i = i + 1;
                                        surat.setKey(key);
                                        surat.setPenomoran(Integer.toString(i));
                                        surat.setPerihal_surat(perihal);
                                        surat.setNomor_surat(nomor_surat);
                                        surat.setPengirim_surat(pengirim);
                                        surat.setTanggal_surat(tanggal_surat);
                                        surat.setTanggal_terima(tanggal_terima);
                                        surat.setStatus_surat(status);
                                        surat.setSifat_surat(sifat);
                                        surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                        surats.add(surat);
                                    }
                                }
                            }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Baru Diupload")) {
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()) {
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Sangat Penting")) {
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()) {
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i] = key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i = i + 1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Baru Diupload")) {
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()) {
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Penting")) {
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()) {
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i] = key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i = i + 1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Baru Diupload")) {
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()) {
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Normal")) {
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()) {
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i] = key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i = i + 1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    if (i==0){
                        Surat surat = new Surat();
                        surat.setPenomoran("1");
                        surat.setPerihal_surat("Belum Ada Surat");
                        surat.setNomor_surat("Belum Ada Surat");
                        surat.setPengirim_surat("Belum Ada Surat");
                        surat.setTanggal_surat("Belum Ada Surat");
                        surat.setTanggal_terima("Belum Ada Surat");
                        surat.setStatus_surat("Belum Ada Surat");
                        surat.setSifat_surat("Belum Ada Surat");
                        surat.setYang_ditugaskan("Belum Ada Surat");
                        surats.add(surat);
                    }
                    ListView listView1 = (ListView) findViewById(R.id.view_surat);
                    SuratAdapter adapter = new SuratAdapter(DisposisiKabid.this, surats);
                    listView1.setAdapter(adapter);

                    if (i==0){
                        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Belum Ada Data", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    } else {
                        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Intent i = new Intent(DisposisiKabid.this, KeteranganBaru.class);
                                i.putExtra("Kunci",kunci[position]);
                                startActivity(i);
                            }
                        });
                    }
                } else {
                    surats.clear();
                    Surat surat = new Surat();
                    surat.setPenomoran("1");
                    surat.setPerihal_surat("Belum Ada Surat");
                    surat.setNomor_surat("Belum Ada Surat");
                    surat.setPengirim_surat("Belum Ada Surat");
                    surat.setTanggal_surat("Belum Ada Surat");
                    surat.setTanggal_terima("Belum Ada Surat");
                    surat.setStatus_surat("Belum Ada Surat");
                    surat.setSifat_surat("Belum Ada Surat");
                    surat.setYang_ditugaskan("Belum Ada Surat");
                    surats.add(surat);

                    SuratAdapter adapter = new SuratAdapter(DisposisiKabid.this, surats);
                    ListView listView1 = (ListView) findViewById(R.id.view_surat);
                    listView1.setAdapter(adapter);

                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Belum Ada Data", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void Tampilan_Diproses(){
        mdata = FirebaseDatabase.getInstance();
        mdb = mdata.getReference("Surat");
        users= getSharedPreferences("user",0);
        mUser = users.getString("user1","Kosong");
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
        } else {
            sPeran = "Uploader";
        }
        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int i =0;
                    final String kunci[] = new String[9999];
                    ArrayList<String> name = new ArrayList<String>();
                    String nama;
                    String nama2;
                    surats.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Sedang Direview")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Sangat Penting")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Sedang Direview")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Penting")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Sedang Direview")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Normal")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String sifat = (String) map.get("Sifat");
                        String key = ds.getKey();
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Sedang Diproses")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Sangat Penting")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String sifat = (String) map.get("Sifat");
                        String key = ds.getKey();
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Sedang Diproses")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Penting")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String sifat = (String) map.get("Sifat");
                        String key = ds.getKey();
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Sedang Diproses")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Normal")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    if (i==0){
                        Surat surat = new Surat();
                        surat.setPenomoran("1");
                        surat.setPerihal_surat("Belum Ada Surat");
                        surat.setNomor_surat("Belum Ada Surat");
                        surat.setPengirim_surat("Belum Ada Surat");
                        surat.setTanggal_surat("Belum Ada Surat");
                        surat.setTanggal_terima("Belum Ada Surat");
                        surat.setStatus_surat("Belum Ada Surat");
                        surat.setSifat_surat("Belum Ada Surat");
                        surat.setYang_ditugaskan("Belum Ada Surat");
                        surats.add(surat);
                    }
                    SuratAdapter adapter = new SuratAdapter(DisposisiKabid.this, surats);
                    ListView listView1 = (ListView) findViewById(R.id.view_surat);
                    listView1.setAdapter(adapter);

                    if (i==0){
                        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Belum Ada Data", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    } else {
                        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Intent i = new Intent(DisposisiKabid.this, KeteranganSedang.class);
                                i.putExtra("Kunci",kunci[position]);
                                startActivity(i);
                            }
                        });
                    }
                }else {
                    surats.clear();
                    Surat surat = new Surat();
                    surat.setPenomoran("1");
                    surat.setPerihal_surat("Belum Ada Surat");
                    surat.setNomor_surat("Belum Ada Surat");
                    surat.setPengirim_surat("Belum Ada Surat");
                    surat.setTanggal_surat("Belum Ada Surat");
                    surat.setTanggal_terima("Belum Ada Surat");
                    surat.setStatus_surat("Belum Ada Surat");
                    surat.setSifat_surat("Belum Ada Surat");
                    surat.setYang_ditugaskan("Belum Ada Surat");
                    surats.add(surat);

                    SuratAdapter adapter = new SuratAdapter(DisposisiKabid.this, surats);
                    ListView listView1 = (ListView) findViewById(R.id.view_surat);
                    listView1.setAdapter(adapter);

                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Belum Ada Data", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void Tampilan_Selesai(){
        mdata = FirebaseDatabase.getInstance();
        mdb = mdata.getReference("Surat");
        users= getSharedPreferences("user",0);
        mUser = users.getString("user1","Kosong");
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
        } else {
            sPeran = "Uploader";
        }
        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int i =0;
                    final String kunci[] = new String[9999];
                    ArrayList<String> name = new ArrayList<String>();
                    String nama;
                    String nama2;
                    surats.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Sedang Diverifikasi")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Sangat Penting")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Sedang Diverifikasi")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Penting")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Sedang Diverifikasi")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Normal")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Ditolak")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Sangat Penting")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Ditolak")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Penting")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Ditolak")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Normal")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Selesai")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Sangat Penting")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Selesai")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Penting")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Selesai")){
                            for (DataSnapshot ab : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                String key2 = ab.getKey();
                                nama = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                                if (nama.equals(mUser) && sifat.equals("Normal")){
                                    name.clear();
                                    for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                        String key3 = bc.getKey();
                                        nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                        name.add(nama2);
                                    }
                                    kunci[i]=key;
                                    String perihal = (String) map.get("Perihal");
                                    String nomor_surat = (String) map.get("Nomor Surat");
                                    String pengirim = (String) map.get("Pengirim");
                                    String tanggal_surat = (String) map.get("Tanggal Surat");
                                    String tanggal_terima = (String) map.get("Tanggal Terima");

                                    Surat surat = new Surat();
                                    i=i+1;
                                    surat.setKey(key);
                                    surat.setPenomoran(Integer.toString(i));
                                    surat.setPerihal_surat(perihal);
                                    surat.setNomor_surat(nomor_surat);
                                    surat.setPengirim_surat(pengirim);
                                    surat.setTanggal_surat(tanggal_surat);
                                    surat.setTanggal_terima(tanggal_terima);
                                    surat.setStatus_surat(status);
                                    surat.setSifat_surat(sifat);
                                    surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                    surats.add(surat);
                                }
                            }
                        }
                    }
                    if (i==0){
                        Surat surat = new Surat();
                        surat.setPenomoran("1");
                        surat.setPerihal_surat("Belum Ada Surat");
                        surat.setNomor_surat("Belum Ada Surat");
                        surat.setPengirim_surat("Belum Ada Surat");
                        surat.setTanggal_surat("Belum Ada Surat");
                        surat.setTanggal_terima("Belum Ada Surat");
                        surat.setStatus_surat("Belum Ada Surat");
                        surat.setSifat_surat("Belum Ada Surat");
                        surat.setYang_ditugaskan("Belum Ada Surat");
                        surats.add(surat);
                    }
                    SuratAdapter adapter = new SuratAdapter(DisposisiKabid.this, surats);
                    ListView listView1 = (ListView) findViewById(R.id.view_surat);
                    listView1.setAdapter(adapter);

                    if (i==0){
                        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Belum Ada Data", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    } else {
                        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Intent i = new Intent(DisposisiKabid.this, KeteranganSudah.class);
                                i.putExtra("Kunci",kunci[position]);
                                startActivity(i);
                            }
                        });
                    }
                }else {
                    surats.clear();
                    Surat surat = new Surat();
                    surat.setPenomoran("1");
                    surat.setPerihal_surat("Belum Ada Surat");
                    surat.setNomor_surat("Belum Ada Surat");
                    surat.setPengirim_surat("Belum Ada Surat");
                    surat.setTanggal_surat("Belum Ada Surat");
                    surat.setTanggal_terima("Belum Ada Surat");
                    surat.setStatus_surat("Belum Ada Surat");
                    surat.setSifat_surat("Belum Ada Surat");
                    surat.setYang_ditugaskan("Belum Ada Surat");
                    surats.add(surat);

                    SuratAdapter adapter = new SuratAdapter(DisposisiKabid.this, surats);
                    ListView listView1 = (ListView) findViewById(R.id.view_surat);
                    listView1.setAdapter(adapter);

                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Belum Ada Data", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void Tampilan_Verifikasi(){
        mdata = FirebaseDatabase.getInstance();
        mdb = mdata.getReference("Surat");
        users= getSharedPreferences("user",0);
        mUser = users.getString("user1","Kosong");
        peran = getSharedPreferences("peran", 0);
        mPeran = peran.getString("peran1","Kosong");

        if (mPeran.equals("Kabid")){
            sPeran = "Kabid";
        } else if (mPeran.equals("Kasubbid 1")){
            sPeran = "Subbid 1";
        } else if (mPeran.equals("Kasubbid 2")) {
            sPeran = "Subbid 2";
        } else if (mPeran.equals("Kasubbid 3")) {
            sPeran = "Subbid 3";
        }
        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int i =0;
                    final String kunci[] = new String[9999];
                    ArrayList<String> name = new ArrayList<String>();
                    String nama;
                    String nama2;
                    surats.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Sedang Diverifikasi") && sifat.equals("Sangat Penting")){
                                name.clear();
                                for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                    String key3 = bc.getKey();
                                    nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                    name.add(nama2);
                                }
                                kunci[i]=key;
                                String perihal = (String) map.get("Perihal");
                                String nomor_surat = (String) map.get("Nomor Surat");
                                String pengirim = (String) map.get("Pengirim");
                                String tanggal_surat = (String) map.get("Tanggal Surat");
                                String tanggal_terima = (String) map.get("Tanggal Terima");

                                Surat surat = new Surat();
                                i=i+1;
                                surat.setKey(key);
                                surat.setPenomoran(Integer.toString(i));
                                surat.setPerihal_surat(perihal);
                                surat.setNomor_surat(nomor_surat);
                                surat.setPengirim_surat(pengirim);
                                surat.setTanggal_surat(tanggal_surat);
                                surat.setTanggal_terima(tanggal_terima);
                                surat.setStatus_surat(status);
                                surat.setSifat_surat(sifat);
                                surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                surats.add(surat);
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Sedang Diverifikasi") && sifat.equals("Penting")){
                                name.clear();
                                for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                    String key3 = bc.getKey();
                                    nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                    name.add(nama2);
                                }
                                kunci[i]=key;
                                String perihal = (String) map.get("Perihal");
                                String nomor_surat = (String) map.get("Nomor Surat");
                                String pengirim = (String) map.get("Pengirim");
                                String tanggal_surat = (String) map.get("Tanggal Surat");
                                String tanggal_terima = (String) map.get("Tanggal Terima");

                                Surat surat = new Surat();
                                i=i+1;
                                surat.setKey(key);
                                surat.setPenomoran(Integer.toString(i));
                                surat.setPerihal_surat(perihal);
                                surat.setNomor_surat(nomor_surat);
                                surat.setPengirim_surat(pengirim);
                                surat.setTanggal_surat(tanggal_surat);
                                surat.setTanggal_terima(tanggal_terima);
                                surat.setStatus_surat(status);
                                surat.setSifat_surat(sifat);
                                surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                surats.add(surat);
                        }
                    }
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String sifat = (String) map.get("Sifat");
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        if (status.equals("Sedang Diverifikasi") && sifat.equals("Normal")){
                                name.clear();
                                for (DataSnapshot bc : dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                                    String key3 = bc.getKey();
                                    nama2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key3).getValue();
                                    name.add(nama2);
                                }
                                kunci[i]=key;
                                String perihal = (String) map.get("Perihal");
                                String nomor_surat = (String) map.get("Nomor Surat");
                                String pengirim = (String) map.get("Pengirim");
                                String tanggal_surat = (String) map.get("Tanggal Surat");
                                String tanggal_terima = (String) map.get("Tanggal Terima");

                                Surat surat = new Surat();
                                i=i+1;
                                surat.setKey(key);
                                surat.setPenomoran(Integer.toString(i));
                                surat.setPerihal_surat(perihal);
                                surat.setNomor_surat(nomor_surat);
                                surat.setPengirim_surat(pengirim);
                                surat.setTanggal_surat(tanggal_surat);
                                surat.setTanggal_terima(tanggal_terima);
                                surat.setStatus_surat(status);
                                surat.setSifat_surat(sifat);
                                surat.setYang_ditugaskan(Arrays.toString(new ArrayList[]{name}).replaceAll("\\[|\\]", ""));
                                surats.add(surat);

                        }
                    }
                    if (i==0){
                        Surat surat = new Surat();
                        surat.setPenomoran("1");
                        surat.setPerihal_surat("Belum Ada Surat");
                        surat.setNomor_surat("Belum Ada Surat");
                        surat.setPengirim_surat("Belum Ada Surat");
                        surat.setTanggal_surat("Belum Ada Surat");
                        surat.setTanggal_terima("Belum Ada Surat");
                        surat.setStatus_surat("Belum Ada Surat");
                        surat.setSifat_surat("Belum Ada Surat");
                        surat.setYang_ditugaskan("Belum Ada Surat");
                        surats.add(surat);
                    }
                    SuratAdapter adapter = new SuratAdapter(DisposisiKabid.this, surats);
                    ListView listView1 = (ListView) findViewById(R.id.view_surat);
                    listView1.setAdapter(adapter);

                    if (i==0){
                        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Belum Ada Data", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    } else {
                        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Intent i = new Intent(DisposisiKabid.this, KeteranganVerifikasi.class);
                                i.putExtra("Kunci",kunci[position]);
                                startActivity(i);
                            }
                        });
                    }
                }else {
                    surats.clear();
                    Surat surat = new Surat();
                    surat.setPenomoran("1");
                    surat.setPerihal_surat("Belum Ada Surat");
                    surat.setNomor_surat("Belum Ada Surat");
                    surat.setPengirim_surat("Belum Ada Surat");
                    surat.setTanggal_surat("Belum Ada Surat");
                    surat.setTanggal_terima("Belum Ada Surat");
                    surat.setStatus_surat("Belum Ada Surat");
                    surat.setSifat_surat("Belum Ada Surat");
                    surat.setYang_ditugaskan("Belum Ada Surat");
                    surats.add(surat);

                    SuratAdapter adapter = new SuratAdapter(DisposisiKabid.this, surats);
                    ListView listView1 = (ListView) findViewById(R.id.view_surat);
                    listView1.setAdapter(adapter);

                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Belum Ada Data", Toast.LENGTH_SHORT);
                            toast.show();
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
