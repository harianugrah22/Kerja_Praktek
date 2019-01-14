package com.example.user.suratbpkad;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class DisposisiKabid extends AppCompatActivity {
    private FirebaseDatabase mdata;
    ArrayList<Surat> surats = new ArrayList<>();
    DatabaseReference mdb;

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
        } else if (Konteks.equals("Verifikasi")){
            Tampilan_Verifikasi();
        } else{
            Tampilan_Baru();
        }
    }

    public void Tampilan_Baru(){
        mdata = FirebaseDatabase.getInstance();
        mdb = mdata.getReference("Surat");
        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int i =0;
                    surats.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String perihal = (String) map.get("Perihal");
                        String nomor_surat = (String) map.get("Nomor Surat");
                        String pengirim = (String) map.get("Pengirim");
                        String tanggal_surat = (String) map.get("Tanggal Surat");
                        String tanggal_terima = (String) map.get("Tanggal Terima");
                        String status = (String) map.get("Status");
                        String sifat = (String) map.get("Sifat");
                        String yang_ditugaskan = (String) map.get("Yang Ditugaskan");

                        if (status.equals("Baru Diupload")){
                            Surat surat = new Surat();
                            i=i+1;
                            surat.setPenomoran(Integer.toString(i));
                            surat.setPerihal_surat(perihal);
                            surat.setNomor_surat(nomor_surat);
                            surat.setPengirim_surat(pengirim);
                            surat.setTanggal_surat(tanggal_surat);
                            surat.setTanggal_terima(tanggal_terima);
                            surat.setStatus_surat(status);
                            surat.setSifat_surat(sifat);
                            surat.setYang_ditugaskan(yang_ditugaskan);
                            surats.add(surat);
                        }
                    }
                    if (i==0){
                        Surat surat = new Surat();
                        i=i+1;
                        surat.setPenomoran(Integer.toString(i));
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
                } else {
                    Surat surat = new Surat();
                    surat.setPenomoran(Integer.toString(1));
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
        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int i =0;
                    surats.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String perihal = (String) map.get("Perihal");
                        String nomor_surat = (String) map.get("Nomor Surat");
                        String pengirim = (String) map.get("Pengirim");
                        String tanggal_surat = (String) map.get("Tanggal Surat");
                        String tanggal_terima = (String) map.get("Tanggal Terima");
                        String status = (String) map.get("Status");
                        String sifat = (String) map.get("Sifat");
                        String yang_ditugaskan = (String) map.get("Yang Ditugaskan");

                        if (status.equals("Sedang Diproses")){
                            Surat surat = new Surat();
                            i=i+1;
                            surat.setPenomoran(Integer.toString(i));
                            surat.setPerihal_surat(perihal);
                            surat.setNomor_surat(nomor_surat);
                            surat.setPengirim_surat(pengirim);
                            surat.setTanggal_surat(tanggal_surat);
                            surat.setTanggal_terima(tanggal_terima);
                            surat.setStatus_surat(status);
                            surat.setSifat_surat(sifat);
                            surat.setYang_ditugaskan(yang_ditugaskan);
                            surats.add(surat);
                        }
                    }
                    if (i==0){
                        Surat surat = new Surat();
                        i=i+1;
                        surat.setPenomoran(Integer.toString(i));
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
                }else {
                    Surat surat = new Surat();
                    surat.setPenomoran(Integer.toString(1));
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
        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int i =0;
                    surats.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String perihal = (String) map.get("Perihal");
                        String nomor_surat = (String) map.get("Nomor Surat");
                        String pengirim = (String) map.get("Pengirim");
                        String tanggal_surat = (String) map.get("Tanggal Surat");
                        String tanggal_terima = (String) map.get("Tanggal Terima");
                        String status = (String) map.get("Status");
                        String sifat = (String) map.get("Sifat");
                        String yang_ditugaskan = (String) map.get("Yang Ditugaskan");

                        if (status.equals("Sedang Diverifikasi")){
                            Surat surat = new Surat();
                            i=i+1;
                            surat.setPenomoran(Integer.toString(i));
                            surat.setPerihal_surat(perihal);
                            surat.setNomor_surat(nomor_surat);
                            surat.setPengirim_surat(pengirim);
                            surat.setTanggal_surat(tanggal_surat);
                            surat.setTanggal_terima(tanggal_terima);
                            surat.setStatus_surat(status);
                            surat.setSifat_surat(sifat);
                            surat.setYang_ditugaskan(yang_ditugaskan);
                            surats.add(surat);
                        }
                    }
                    if (i==0){
                        Surat surat = new Surat();
                        i=i+1;
                        surat.setPenomoran(Integer.toString(i));
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
                }else {
                    Surat surat = new Surat();
                    surat.setPenomoran(Integer.toString(1));
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
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public class Ket_Surat implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            mdb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }
}
