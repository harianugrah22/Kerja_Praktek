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
            Toast toast = Toast.makeText(getApplicationContext(), "Gagal Dimuat", Toast.LENGTH_SHORT);
            toast.show();
        }

        ListView listView1 = (ListView) findViewById(R.id.view_surat);

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
                } else if (Konteks.equals("Verifikasi")){
                    Tampilan_Verifikasi();
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
        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int i =0;
                    final String kunci[] = new String[9999];
                    surats.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String perihal = (String) map.get("Perihal");
                        String nomor_surat = (String) map.get("Nomor Surat");
                        String pengirim = (String) map.get("Pengirim");
                        String tanggal_surat = (String) map.get("Tanggal Surat");
                        String tanggal_terima = (String) map.get("Tanggal Terima");
                        String status = (String) map.get("Status");
                        String sifat = (String) map.get("Sifat");
                        String yang_ditugaskan = (String) map.get("Yang Ditugaskan");

                        if (status.equals("Baru Diupload")){
                            kunci[i]=key;
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
                    ListView listView1 = (ListView) findViewById(R.id.view_surat);
                    SuratAdapter adapter = new SuratAdapter(DisposisiKabid.this, surats);
                    listView1.setAdapter(adapter);

                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent i = new Intent(DisposisiKabid.this, KeteranganBaru.class);
                            i.putExtra("Kunci",kunci[position]);
                            startActivity(i);
                        }
                    });
                } else {
                    surats.clear();
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
        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int i =0;
                    final String kunci[] = new String[9999];
                    surats.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String perihal = (String) map.get("Perihal");
                        String nomor_surat = (String) map.get("Nomor Surat");
                        String pengirim = (String) map.get("Pengirim");
                        String tanggal_surat = (String) map.get("Tanggal Surat");
                        String tanggal_terima = (String) map.get("Tanggal Terima");
                        String status = (String) map.get("Status");
                        String sifat = (String) map.get("Sifat");
                        String yang_ditugaskan = (String) map.get("Yang Ditugaskan");

                        if (status.equals("Sedang Diproses")){
                            kunci[i]=key;
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

                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent i = new Intent(DisposisiKabid.this, KeteranganSedang.class);
                            i.putExtra("Kunci",kunci[position]);
                            startActivity(i);
                        }
                    });
                }else {
                    surats.clear();
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
        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int i =0;
                    final String kunci[] = new String[9999];
                    surats.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        String key = ds.getKey();
                        String perihal = (String) map.get("Perihal");
                        String nomor_surat = (String) map.get("Nomor Surat");
                        String pengirim = (String) map.get("Pengirim");
                        String tanggal_surat = (String) map.get("Tanggal Surat");
                        String tanggal_terima = (String) map.get("Tanggal Terima");
                        String status = (String) map.get("Status");
                        String sifat = (String) map.get("Sifat");
                        String yang_ditugaskan = (String) map.get("Yang Ditugaskan");

                        if (status.equals("Sedang Diverifikasi")){
                            kunci[i]=key;
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

                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent i = new Intent(DisposisiKabid.this, KeteranganVerifikasi.class);
                            i.putExtra("Kunci",kunci[position]);
                            startActivity(i);
                        }
                    });
                }else {
                    surats.clear();
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
