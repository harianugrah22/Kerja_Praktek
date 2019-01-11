package com.example.user.suratbpkad;

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
        ListView listView1 = (ListView) findViewById(R.id.view_surat);

        mdata = FirebaseDatabase.getInstance();
        mdb = mdata.getReference("Surat");

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            SuratAdapter adapter = new SuratAdapter(this, retrieve());
            listView1.setAdapter(adapter);

        } else{
            Context context = getApplicationContext();
            CharSequence text = "Tidak Ada Koneksi";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
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
                    Context context = getApplicationContext();
                    CharSequence text = "Data Tidak Dapat Ditampilkan";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
        }
    }

    public void FetchData(DataSnapshot dataSnapshot){
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

    public ArrayList<Surat> retrieve(){
        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Context context = getApplicationContext();
                CharSequence text = "Data Tidak Dapat Diterima";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        return surats;
    }
}
