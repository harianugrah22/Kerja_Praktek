package com.example.user.suratbpkad;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by user on 28/01/2019.
 */

public class KetRingkasanAct extends AppCompatActivity {
    private FirebaseDatabase mdata;
    ArrayList<KetRingkasan> surats = new ArrayList<>();
    final String kunci[] = new String[9999];
    DatabaseReference mdb;
    SharedPreferences peran;
    String mPeran;
    String sPeran;
    String Nama;
    String Keterangan;
    String Query;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ketringkasan);

        Intent intent = getIntent();
        Nama = intent.getStringExtra("Nama");
        Keterangan = intent.getStringExtra("Keterangan");
        Query = intent.getStringExtra("Query");

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
        if (Keterangan.equals("Ringkasan")){
            Tampilan_Ringkasan();
        } else if (Keterangan.equals("Search")){
            Tampilan_Search(Query);
        }
        Button refresh = (Button) findViewById(R.id.refresh_button);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Keterangan.equals("Ringkasan")){
                    Tampilan_Ringkasan();
                } else if (Keterangan.equals("Search")){
                    Tampilan_Search(Query);
                }
            }
        });
    }

    public void Tampilan_Search(final String queryText){
        surats.clear();
        mdata = FirebaseDatabase.getInstance();
        mdb = mdata.getReference("Surat");

        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int i =0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String key = ds.getKey();
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
                        String sifat = (String) dataSnapshot.child(key).child("Sifat").getValue();
                        String perihal = (String) map.get("Perihal");
                        String nomor_surat = (String) map.get("Nomor Surat");
                        String pengirim = (String) map.get("Pengirim");
                        String tanggal_surat = (String) map.get("Tanggal Surat");
                        String tanggal_terima = (String) map.get("Tanggal Terima");

                        if (queryText.toLowerCase().toString().replaceAll(" ", "").equals(nomor_surat.toLowerCase().toString().replaceAll(" ", ""))){
                            KetRingkasan surat = new KetRingkasan();
                            kunci[i] = key;
                            i=i+1;
                            surat.setPenomoran(Integer.toString(i));
                            surat.setPerihal_surat(perihal);
                            surat.setNomor_surat(nomor_surat);
                            surat.setPengirim_surat(pengirim);
                            surat.setTanggal_surat(tanggal_surat);
                            surat.setStatus_surat(status);
                            surat.setSifat_surat(sifat);
                            surats.add(surat);
                        } else if (queryText.toLowerCase().toString().replaceAll(" ", "").equals(pengirim.toLowerCase().toString().replaceAll(" ", ""))){
                            KetRingkasan surat = new KetRingkasan();
                            kunci[i] = key;
                            i=i+1;
                            surat.setPenomoran(Integer.toString(i));
                            surat.setPerihal_surat(perihal);
                            surat.setNomor_surat(nomor_surat);
                            surat.setPengirim_surat(pengirim);
                            surat.setTanggal_surat(tanggal_surat);
                            surat.setStatus_surat(status);
                            surat.setSifat_surat(sifat);
                            surats.add(surat);
                        } else if (queryText.toLowerCase().toString().replaceAll(" ", "").equals(tanggal_surat.toLowerCase().toString().replaceAll(" ", ""))){
                            KetRingkasan surat = new KetRingkasan();
                            kunci[i] = key;
                            i=i+1;
                            surat.setPenomoran(Integer.toString(i));
                            surat.setPerihal_surat(perihal);
                            surat.setNomor_surat(nomor_surat);
                            surat.setPengirim_surat(pengirim);
                            surat.setTanggal_surat(tanggal_surat);
                            surat.setStatus_surat(status);
                            surat.setSifat_surat(sifat);
                            surats.add(surat);
                        } else if (queryText.toLowerCase().toString().replaceAll(" ", "").equals(perihal.toLowerCase().toString().replaceAll(" ", ""))){
                            KetRingkasan surat = new KetRingkasan();
                            kunci[i] = key;
                            i=i+1;
                            surat.setPenomoran(Integer.toString(i));
                            surat.setPerihal_surat(perihal);
                            surat.setNomor_surat(nomor_surat);
                            surat.setPengirim_surat(pengirim);
                            surat.setTanggal_surat(tanggal_surat);
                            surat.setStatus_surat(status);
                            surat.setSifat_surat(sifat);
                            surats.add(surat);
                        } else if (queryText.toLowerCase().toString().replaceAll(" ", "").equals(status.toLowerCase().toString().replaceAll(" ", ""))){
                            KetRingkasan surat = new KetRingkasan();
                            kunci[i] = key;
                            i=i+1;
                            surat.setPenomoran(Integer.toString(i));
                            surat.setPerihal_surat(perihal);
                            surat.setNomor_surat(nomor_surat);
                            surat.setPengirim_surat(pengirim);
                            surat.setTanggal_surat(tanggal_surat);
                            surat.setStatus_surat(status);
                            surat.setSifat_surat(sifat);
                            surats.add(surat);
                        } else if (queryText.toLowerCase().toString().replaceAll(" ", "").equals(sifat.toLowerCase().toString().replaceAll(" ", ""))){
                            KetRingkasan surat = new KetRingkasan();
                            kunci[i] = key;
                            i=i+1;
                            surat.setPenomoran(Integer.toString(i));
                            surat.setPerihal_surat(perihal);
                            surat.setNomor_surat(nomor_surat);
                            surat.setPengirim_surat(pengirim);
                            surat.setTanggal_surat(tanggal_surat);
                            surat.setStatus_surat(status);
                            surat.setSifat_surat(sifat);
                            surats.add(surat);
                        } else if (queryText.toLowerCase().toString().replaceAll(" ", "").equals(tanggal_terima.toLowerCase().toString().replaceAll(" ", ""))){
                            KetRingkasan surat = new KetRingkasan();
                            kunci[i] = key;
                            i=i+1;
                            surat.setPenomoran(Integer.toString(i));
                            surat.setPerihal_surat(perihal);
                            surat.setNomor_surat(nomor_surat);
                            surat.setPengirim_surat(pengirim);
                            surat.setTanggal_surat(tanggal_surat);
                            surat.setStatus_surat(status);
                            surat.setSifat_surat(sifat);
                            surats.add(surat);
                        }
                    }
                    if (i==0){
                        KetRingkasan surat = new KetRingkasan();
                        i=i+1;
                        surat.setPenomoran(Integer.toString(i));
                        surat.setPerihal_surat("Tidak Ditemukan");
                        surat.setNomor_surat("Tidak Ditemukan");
                        surat.setPengirim_surat("Tidak Ditemukan");
                        surat.setTanggal_surat("Tidak Ditemukan");
                        surat.setStatus_surat("Tidak Ditemukan");
                        surat.setSifat_surat("Tidak Ditemukan");
                        surats.add(surat);
                    }

                    KetRingkasanAdapter adapter = new KetRingkasanAdapter(KetRingkasanAct.this, surats);
                    ListView listView1 = (ListView) findViewById(R.id.view_ketringkasan);
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
                                Intent i = new Intent(KetRingkasanAct.this, KeteranganSudah.class);
                                i.putExtra("Kunci",kunci[position]);
                                startActivity(i);
                            }
                        });
                    }
                } else{
                    KetRingkasan surat = new KetRingkasan();
                    surat.setPenomoran(Integer.toString(1));
                    surat.setPerihal_surat("Tidak Ditemukan");
                    surat.setNomor_surat("Tidak Ditemukan");
                    surat.setPengirim_surat("Tidak Ditemukan");
                    surat.setTanggal_surat("Tidak Ditemukan");
                    surat.setStatus_surat("Tidak Ditemukan");
                    surat.setSifat_surat("Tidak Ditemukan");
                    surats.add(surat);

                    KetRingkasanAdapter adapter = new KetRingkasanAdapter(KetRingkasanAct.this, surats);
                    ListView listView1 = (ListView) findViewById(R.id.view_ketringkasan);
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

    public void Tampilan_Ringkasan(){
        surats.clear();
        mdata = FirebaseDatabase.getInstance();
        mdb = mdata.getReference("Surat");
        peran = getSharedPreferences("peran", 0);
        mPeran = peran.getString("peran1","Kosong");

        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int i =0;
                    surats.clear();
                    if (mPeran.equals("Kabid")){
                        i=Periksa_Status("Baru Diupload", "Belum Ada", i, dataSnapshot);
                        i=Periksa_Status("Baru Diupload", "Sangat Penting", i, dataSnapshot);
                        i=Periksa_Status("Baru Diupload", "Penting", i, dataSnapshot);
                        i=Periksa_Status("Baru Diupload", "Normal", i, dataSnapshot);
                        i=Periksa_Status("Sedang Diproses", "Sangat Penting",i, dataSnapshot);
                        i=Periksa_Status("Sedang Diproses", "Penting",i,dataSnapshot);
                        i=Periksa_Status("Sedang Diproses", "Normal",i,dataSnapshot);
                        i=Periksa_Status("Sedang Direview", "Sangat Penting",i, dataSnapshot);
                        i=Periksa_Status("Sedang Direview", "Penting",i, dataSnapshot);
                        i=Periksa_Status("Sedang Direview", "Normal",i, dataSnapshot);
                        i=Periksa_Status("Sedang Diverifikasi", "Sangat Penting",i, dataSnapshot);
                        i=Periksa_Status("Sedang Diverifikasi", "Penting",i, dataSnapshot);
                        i=Periksa_Status("Sedang Diverifikasi", "Normal",i, dataSnapshot);
                        i=Periksa_Status("Selesai", "Sangat Penting",i, dataSnapshot);
                        i=Periksa_Status("Selesai", "Penting",i, dataSnapshot);
                        i=Periksa_Status("Selesai", "Normal",i, dataSnapshot);
                    } else{
                        i=Periksa_Status_Subbid("Baru Diupload", "Belum Ada", i, dataSnapshot);
                        i=Periksa_Status_Subbid("Baru Diupload", "Sangat Penting", i, dataSnapshot);
                        i=Periksa_Status_Subbid("Baru Diupload", "Penting", i, dataSnapshot);
                        i=Periksa_Status_Subbid("Baru Diupload", "Normal", i, dataSnapshot);
                        i=Periksa_Status_Subbid("Sedang Diproses", "Sangat Penting",i, dataSnapshot);
                        i=Periksa_Status_Subbid("Sedang Diproses", "Penting",i,dataSnapshot);
                        i=Periksa_Status_Subbid("Sedang Diproses", "Normal",i,dataSnapshot);
                        i=Periksa_Status_Subbid("Sedang Direview", "Sangat Penting",i, dataSnapshot);
                        i=Periksa_Status_Subbid("Sedang Direview", "Penting",i, dataSnapshot);
                        i=Periksa_Status_Subbid("Sedang Direview", "Normal",i, dataSnapshot);
                        i=Periksa_Status_Subbid("Sedang Diverifikasi", "Sangat Penting",i, dataSnapshot);
                        i=Periksa_Status_Subbid("Sedang Diverifikasi", "Penting",i, dataSnapshot);
                        i=Periksa_Status_Subbid("Sedang Diverifikasi", "Normal",i, dataSnapshot);
                        i=Periksa_Status_Subbid("Selesai", "Sangat Penting",i, dataSnapshot);
                        i=Periksa_Status_Subbid("Selesai", "Penting",i, dataSnapshot);
                        i=Periksa_Status_Subbid("Selesai", "Normal",i, dataSnapshot);
                    }
                    if (i==0){
                        KetRingkasan surat = new KetRingkasan();
                        i=i+1;
                        surat.setPenomoran(Integer.toString(i));
                        surat.setPerihal_surat("Belum Ada");
                        surat.setNomor_surat("Belum Ada");
                        surat.setPengirim_surat("Belum Ada");
                        surat.setTanggal_surat("Belum Ada");
                        surat.setStatus_surat("Belum Ada");
                        surat.setSifat_surat("Belum Ada");
                        surats.add(surat);
                    }
                    KetRingkasanAdapter adapter = new KetRingkasanAdapter(KetRingkasanAct.this, surats);
                    ListView listView1 = (ListView) findViewById(R.id.view_ketringkasan);
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
                                Intent i = new Intent(KetRingkasanAct.this, KeteranganSudah.class);
                                i.putExtra("Kunci",kunci[position]);
                                startActivity(i);
                            }
                        });
                    }

                } else {
                    int i =0;
                    KetRingkasan surat = new KetRingkasan();
                    i=i+1;
                    surat.setPenomoran(Integer.toString(i));
                    surat.setPerihal_surat("Belum Ada");
                    surat.setNomor_surat("Belum Ada");
                    surat.setPengirim_surat("Belum Ada");
                    surat.setTanggal_surat("Belum Ada");
                    surat.setStatus_surat("Belum Ada");
                    surat.setSifat_surat("Belum Ada");
                    surats.add(surat);

                    KetRingkasanAdapter adapter = new KetRingkasanAdapter(KetRingkasanAct.this, surats);
                    ListView listView1 = (ListView) findViewById(R.id.view_ketringkasan);
                    listView1.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public Integer Periksa_Status(String status1, String sifat1, Integer i, DataSnapshot dataSnapshot){
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Map<String, Object> map = (Map<String, Object>) ds.getValue();
            String key = ds.getKey();
            String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(Nama).child("Status").getValue();
            String sifat = (String) dataSnapshot.child(key).child("Sifat").getValue();
            if (status.equals(status1)) {
                if (sifat.equals(sifat1)) {
                    String perihal = (String) map.get("Perihal");
                    String nomor_surat = (String) map.get("Nomor Surat");
                    String pengirim = (String) map.get("Pengirim");
                    String tanggal_surat = (String) map.get("Tanggal Surat");

                    KetRingkasan surat = new KetRingkasan();
                    kunci[i] = key;
                    i=i+1;
                    surat.setPenomoran(Integer.toString(i));
                    surat.setPerihal_surat(perihal);
                    surat.setNomor_surat(nomor_surat);
                    surat.setPengirim_surat(pengirim);
                    surat.setTanggal_surat(tanggal_surat);
                    surat.setStatus_surat(status);
                    surat.setSifat_surat(sifat);
                    surats.add(surat);
                }
            }
        }
        return i;
    }

    public Integer Periksa_Status_Subbid(String status1, String sifat1, Integer i, DataSnapshot dataSnapshot){
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Map<String, Object> map = (Map<String, Object>) ds.getValue();
            String key = ds.getKey();
            String status = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child(sPeran).child("Status").getValue();
            String sifat = (String) dataSnapshot.child(key).child("Sifat").getValue();
            if (status.equals(status1)) {
                if (sifat.equals(sifat1)) {
                    for (DataSnapshot ab: ds.child("Yang Ditugaskan").child(sPeran).child("Pelaksana").getChildren()){
                        String key2 =ab.getKey();
                        String namas = (String) ds.child("Yang Ditugaskan").child(sPeran).child("Pelaksana").child(key2).getValue();
                        if(namas.equals(Nama)){
                            String perihal = (String) map.get("Perihal");
                            String nomor_surat = (String) map.get("Nomor Surat");
                            String pengirim = (String) map.get("Pengirim");
                            String tanggal_surat = (String) map.get("Tanggal Surat");

                            KetRingkasan surat = new KetRingkasan();
                            kunci[i] = key;
                            i=i+1;
                            surat.setPenomoran(Integer.toString(i));
                            surat.setPerihal_surat(perihal);
                            surat.setNomor_surat(nomor_surat);
                            surat.setPengirim_surat(pengirim);
                            surat.setTanggal_surat(tanggal_surat);
                            surat.setStatus_surat(status);
                            surat.setSifat_surat(sifat);
                            surats.add(surat);
                        }
                    }
                }
            }
        }
        return i;
    }
}
