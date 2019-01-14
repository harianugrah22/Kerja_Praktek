package com.example.user.suratbpkad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseDatabase mdata;
    DatabaseReference mdb;
    String perihal;
    String nomor_surat;
    String pengirim;
    String tanggal_terima;
    String status;
    String perihal_diproses;
    String nomor_diproses;
    String pengirim_diproses;
    String tanggal_terima_diproses;
    String perihal_verif;
    String nomor_verif;
    String pengirim_verif;
    String tanggal_terima_verif;
    int a=0;
    int b=0;
    int c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Toast toast = Toast.makeText(getApplicationContext(), "Sedang Memperoleh Data Terbaru", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        mdata = FirebaseDatabase.getInstance();
        mdb = mdata.getReference("Surat");
        setSupportActionBar(toolbar);

        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        status = (String) map.get("Status");
                        if (status.equals("Baru Diupload")){
                            a=a+1;
                            perihal = (String) map.get("Perihal");
                            nomor_surat = (String) map.get("Nomor Surat");
                            pengirim = (String) map.get("Pengirim");
                            tanggal_terima = (String) map.get("Tanggal Terima");
                        }
                        if (status.equals("Sedang Diproses")){
                            b=b+1;
                            perihal_diproses = (String) map.get("Perihal");
                            nomor_diproses = (String) map.get("Nomor Surat");
                            pengirim_diproses = (String) map.get("Pengirim");
                            tanggal_terima_diproses = (String) map.get("Tanggal Terima");
                        }
                        if (status.equals("Sedang Diproses")){
                            c=c+1;
                            perihal_verif = (String) map.get("Perihal");
                            nomor_verif = (String) map.get("Nomor Surat");
                            pengirim_verif = (String) map.get("Pengirim");
                            tanggal_terima_verif = (String) map.get("Tanggal Terima");
                        }
                    }

                    TextView perihalTxt = (TextView) findViewById(R.id.perihal_baru);
                    TextView nomor_suratTxt = (TextView) findViewById(R.id.nomor_baru);
                    TextView pengirimTxt = (TextView) findViewById(R.id.pengirim_baru);
                    TextView tanggal_terimaTxt = (TextView) findViewById(R.id.tanggal_baru);

                    TextView perihal_diprosesTxt = (TextView) findViewById(R.id.perihal_sedang);
                    TextView nomor_diprosesTxt = (TextView) findViewById(R.id.nomor_sedang);
                    TextView pengirim_diprosesTxt = (TextView) findViewById(R.id.pengirim_sedang);
                    TextView tanggal_terima_diprosesTxt = (TextView) findViewById(R.id.tanggal_sedang);

                    TextView perihal_verifTxt = (TextView) findViewById(R.id.perihal_verif);
                    TextView nomor_verifTxt = (TextView) findViewById(R.id.nomor_verif);
                    TextView pengirim_verifTxt = (TextView) findViewById(R.id.pengirim_verif);
                    TextView tanggal_terima_verifTxt = (TextView) findViewById(R.id.tanggal_terima_verif);

                    if (a!=0){
                        perihalTxt.setText(perihal);
                        nomor_suratTxt.setText(nomor_surat);
                        pengirimTxt.setText(pengirim);
                        tanggal_terimaTxt.setText(tanggal_terima);
                    } else {
                        perihalTxt.setText("Belum Ada Surat");
                        nomor_suratTxt.setText("Belum Ada Surat");
                        pengirimTxt.setText("Belum Ada Surat");
                        tanggal_terimaTxt.setText("Belum Ada Surat");
                    }

                    if(b!=0){
                        perihal_diprosesTxt.setText(perihal_diproses);
                        nomor_diprosesTxt.setText(nomor_diproses);
                        pengirim_diprosesTxt.setText(pengirim_diproses);
                        tanggal_terima_diprosesTxt.setText(tanggal_terima_diproses);
                    } else{
                        perihal_diprosesTxt.setText("Belum Ada Surat");
                        nomor_diprosesTxt.setText("Belum Ada Surat");
                        pengirim_diprosesTxt.setText("Belum Ada Surat");
                        tanggal_terima_diprosesTxt.setText("Belum Ada Surat");
                    }

                    if(c!=0){
                        perihal_verifTxt.setText(perihal_verif);
                        nomor_verifTxt.setText(nomor_verif);
                        pengirim_verifTxt.setText(pengirim_verif);
                        tanggal_terima_verifTxt.setText(tanggal_terima_verif);
                    } else {
                        perihal_verifTxt.setText("Belum Ada Surat");
                        nomor_verifTxt.setText("Belum Ada Surat");
                        pengirim_verifTxt.setText("Belum Ada Surat");
                        tanggal_terima_verifTxt.setText("Belum Ada Surat");
                    }
                } else {
                    TextView perihalTxt = (TextView) findViewById(R.id.perihal_baru);
                    TextView nomor_suratTxt = (TextView) findViewById(R.id.nomor_baru);
                    TextView pengirimTxt = (TextView) findViewById(R.id.pengirim_baru);
                    TextView tanggal_terimaTxt = (TextView) findViewById(R.id.tanggal_baru);

                    perihalTxt.setText("Belum Ada Surat");
                    nomor_suratTxt.setText("Belum Ada Surat");
                    pengirimTxt.setText("Belum Ada Surat");
                    tanggal_terimaTxt.setText("Belum Ada Surat");

                    TextView perihal_diprosesTxt = (TextView) findViewById(R.id.perihal_sedang);
                    TextView nomor_diprosesTxt = (TextView) findViewById(R.id.nomor_sedang);
                    TextView pengirim_diprosesTxt = (TextView) findViewById(R.id.pengirim_sedang);
                    TextView tanggal_terima_diprosesTxt = (TextView) findViewById(R.id.tanggal_sedang);

                    perihal_diprosesTxt.setText("Belum Ada Surat");
                    nomor_diprosesTxt.setText("Belum Ada Surat");
                    pengirim_diprosesTxt.setText("Belum Ada Surat");
                    tanggal_terima_diprosesTxt.setText("Belum Ada Surat");

                    TextView perihal_verifTxt = (TextView) findViewById(R.id.perihal_verif);
                    TextView nomor_verifTxt = (TextView) findViewById(R.id.nomor_verif);
                    TextView pengirim_verifTxt = (TextView) findViewById(R.id.pengirim_verif);
                    TextView tanggal_terima_verifTxt = (TextView) findViewById(R.id.tanggal_terima_verif);

                    perihal_verifTxt.setText("Belum Ada Surat");
                    nomor_verifTxt.setText("Belum Ada Surat");
                    pengirim_verifTxt.setText("Belum Ada Surat");
                    tanggal_terima_verifTxt.setText("Belum Ada Surat");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this,UploadSuratBaru.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayout SuratBaru = (LinearLayout) findViewById(R.id.home_baru);
        SuratBaru.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, DisposisiKabid.class);
                i.putExtra("Konteks", "Baru");
                startActivity(i);
            }
        });
        LinearLayout SuratBelumDiproses = (LinearLayout) findViewById(R.id.home_sedang_diprosses);
        SuratBelumDiproses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, DisposisiKabid.class);
                i.putExtra("Konteks", "Diproses");
                startActivity(i);
            }
        });
        LinearLayout SuratSedangDiproses = (LinearLayout) findViewById(R.id.home_verifikasi);
        SuratSedangDiproses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, DisposisiKabid.class);
                i.putExtra("Konteks", "Verifikasi");
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuSearch) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.tambah) {
            Intent i = new Intent(HomePage.this,UploadSuratBaru.class);
            startActivity(i);
        } else if (id == R.id.disposisi_kabid) {
            Intent i = new Intent(HomePage.this,DisposisiKabid.class);
            i.putExtra("Konteks", "Baru");
            startActivity(i);
        } else if (id == R.id.disposisi_subbid) {
            Intent i = new Intent(HomePage.this,DisposisiSubbid.class);
            startActivity(i);
        }  else if (id == R.id.surat_sedang_diproses) {
            Intent i = new Intent(HomePage.this,SuratSedangDiproses.class);
            startActivity(i);
        } else if (id == R.id.surat_selesai) {
            Intent i = new Intent(HomePage.this,SuratSudahDiproses.class);
            startActivity(i);
        } else if (id == R.id.surat_kelompok_sedang_diproses) {
            Intent i = new Intent(HomePage.this,SuratSedangDiproses.class);
            startActivity(i);
        } else if (id == R.id.verifikasi_surat) {
            Intent i = new Intent(HomePage.this,VerifikasiSurat.class);
            startActivity(i);
        } else if (id == R.id.surat_kelompok_sedang_diproses_selesai) {
            Intent i = new Intent(HomePage.this,SuratSudahDiproses.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
