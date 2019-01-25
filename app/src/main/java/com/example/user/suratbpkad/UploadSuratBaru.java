package com.example.user.suratbpkad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class UploadSuratBaru extends AppCompatActivity {

    Button mOpenDialog;
    EditText mNomorSurat;
    EditText mTanggalSurat;
    EditText mTanggalTerima;
    EditText mPengirim;
    EditText mPerihal;
    EditText mMemo;
    Button mUpload;
    Button mHome;
    Button mClear;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    DatabaseReference mData;
    String kesediaan;
    DatabaseReference mAkun;
    String kabid;
    String muploader;
    String peran;
    String nomor_surat;
    String pengirim;
    String tanggal_surat;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_surat_baru);
        mOpenDialog=(Button) findViewById(R.id.upload_file);
        mNomorSurat=(EditText) findViewById(R.id.nomor_surat);
        mTanggalSurat=(EditText) findViewById(R.id.tanggal_surat);
        mTanggalTerima=(EditText) findViewById(R.id.tanggal_terima);
        mPengirim=(EditText) findViewById(R.id.pengirim_surat);
        mPerihal=(EditText) findViewById(R.id.perihal);
        mMemo=(EditText) findViewById(R.id.memo);
        mUpload=(Button) findViewById(R.id.upload);
        mHome= (Button) findViewById(R.id.homepage);
        mClear = (Button) findViewById(R.id.clear);

        mOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PilihFile pilihFile = new PilihFile();
                pilihFile.show(getFragmentManager(),"Pilih File");
            }
        });

        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new InternetCheck(new InternetCheck.Consumer() {
                    @Override
                    public void accept(Boolean internet) {
                        if (internet) {
                            if (mNomorSurat.getText().length() == 0 || mTanggalSurat.getText().length() == 0 || mTanggalTerima.getText().length() == 0
                                    || mPengirim.getText().length() == 0 || mPerihal.getText().length() == 0)
                            {
                                Context context = getApplicationContext();
                                CharSequence text = "Terdapat Bagian yang Belum Diisi";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                            else
                            {
                                CekKesediaan();
                            }
                        } else {
                            Context context = getApplicationContext();
                            CharSequence text = "Tidak Ada Koneksi";
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                });
            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNomorSurat.setText("");
                mTanggalSurat.setText("");
                mTanggalTerima.setText("");
                mPengirim.setText("");
                mPerihal.setText("");
                mMemo.setText("");
            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UploadSuratBaru.this, HomePage.class);
                startActivity(i);
            }
        });
    }

    public void CekKesediaan(){
        mData = database.getReference("Surat");
        mAkun = database.getReference("Users");
        mAkun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot1) {
                if (dataSnapshot1.exists()){
                   mData.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           if (dataSnapshot.exists()){
                               for (DataSnapshot ds1 : dataSnapshot1.getChildren()) {
                                   Map<String, Object> map1 = (Map<String, Object>) ds1.getValue();
                                   peran = (String) map1.get("Peran");
                                   if (peran.equals("Kabid")){
                                       kabid = (String) map1.get("Username");
                                   } else if (peran.equals("Uploader")){
                                       muploader = (String) map1.get("Username");
                                   }
                               }
                               for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                   Map<String, Object> map = (Map<String, Object>) ds.getValue();
                                   nomor_surat = (String) map.get("Nomor Surat");
                                   pengirim = (String) map.get("Pengirim");
                                   tanggal_surat = (String) map.get("Tanggal Surat");
                                   if (nomor_surat.equals(mNomorSurat.getText().toString()) && pengirim.equals(mPengirim.getText().toString())
                                           && tanggal_surat.equals(mTanggalSurat.getText().toString())){
                                       kesediaan = "No";
                                       break;
                                   } else{
                                       kesediaan = "Yes";
                                   }
                               }

                               if (kesediaan.equals("Yes")){
                                   myRef = database.getReference("Surat").push();
                                   myRef.child("Nomor Surat").setValue(mNomorSurat.getText().toString());
                                   myRef.child("Tanggal Surat").setValue(mTanggalSurat.getText().toString());
                                   myRef.child("Tanggal Terima").setValue(mTanggalTerima.getText().toString());
                                   myRef.child("Pengirim").setValue(mPengirim.getText().toString());
                                   myRef.child("Perihal").setValue(mPerihal.getText().toString());
                                   myRef.child("Sifat").setValue("Belum Ada");
                                   myRef.child("Yang Ditugaskan").child("Kabid").child("Status").setValue("Baru Diupload");
                                   myRef.child("Yang Ditugaskan").child("Subbid 1").child("Status").setValue("Belum Ada");
                                   myRef.child("Yang Ditugaskan").child("Subbid 2").child("Status").setValue("Belum Ada");
                                   myRef.child("Yang Ditugaskan").child("Subbid 3").child("Status").setValue("Belum Ada");
                                   myRef.child("Yang Ditugaskan").child("Uploader").child("Status").setValue("Baru Diupload");
                                   HashMap<String, String> names = new HashMap <String, String>();
                                   names.put("0",kabid);
                                   HashMap<String, String> subbid1 = new HashMap <String, String>();
                                   subbid1.put("0","Belum Ada");
                                   HashMap<String, String> subbid2 = new HashMap <String, String>();
                                   subbid2.put("0","Belum Ada");
                                   HashMap<String, String> subbid3 = new HashMap <String, String>();
                                   subbid3.put("0","Belum Ada");
                                   HashMap<String, String> uploader = new HashMap <String, String>();
                                   uploader.put("0",muploader);
                                   myRef.child("Yang Ditugaskan").child("Kabid").child("Pelaksana").setValue(names);
                                   myRef.child("Yang Ditugaskan").child("Subbid 1").child("Pelaksana").setValue(subbid1);
                                   myRef.child("Yang Ditugaskan").child("Subbid 2").child("Pelaksana").setValue(subbid2);
                                   myRef.child("Yang Ditugaskan").child("Subbid 3").child("Pelaksana").setValue(subbid3);
                                   myRef.child("Yang Ditugaskan").child("Uploader").child("Pelaksana").setValue(uploader);

                                   if (mMemo.getText().length() == 0)
                                   {
                                       myRef.child("Memo").setValue("Kosong");
                                   } else{
                                       myRef.child("Memo").setValue(mMemo.getText().toString());
                                   }

                                   Context context = getApplicationContext();
                                   CharSequence text = "Surat Berhasil Diupload";
                                   int duration = Toast.LENGTH_LONG;

                                   Toast toast = Toast.makeText(context, text, duration);
                                   toast.setGravity(Gravity.CENTER, 0, 0);
                                   toast.show();

                               } else {
                                   Context context = getApplicationContext();
                                   CharSequence text = "Surat Telah Ada Dalam Database";
                                   int duration = Toast.LENGTH_LONG;

                                   Toast toast = Toast.makeText(context, text, duration);
                                   toast.setGravity(Gravity.CENTER, 0, 0);
                                   toast.show();
                               }
                           } else {
                               for (DataSnapshot ds1 : dataSnapshot1.getChildren()) {
                                   Map<String, Object> map1 = (Map<String, Object>) ds1.getValue();
                                   peran = (String) map1.get("Peran");
                                   if (peran.equals("Kabid")){
                                       kabid = (String) map1.get("Username");
                                   } else if (peran.equals("Uploader")){
                                       muploader = (String) map1.get("Username");
                                   }
                               }

                               myRef = database.getReference("Surat").push();
                               myRef.child("Nomor Surat").setValue(mNomorSurat.getText().toString());
                               myRef.child("Tanggal Surat").setValue(mTanggalSurat.getText().toString());
                               myRef.child("Tanggal Terima").setValue(mTanggalTerima.getText().toString());
                               myRef.child("Pengirim").setValue(mPengirim.getText().toString());
                               myRef.child("Perihal").setValue(mPerihal.getText().toString());
                               myRef.child("Sifat").setValue("Belum Ada");
                               myRef.child("Yang Ditugaskan").child("Kabid").child("Status").setValue("Baru Diupload");
                               myRef.child("Yang Ditugaskan").child("Subbid 1").child("Status").setValue("Belum Ada");
                               myRef.child("Yang Ditugaskan").child("Subbid 2").child("Status").setValue("Belum Ada");
                               myRef.child("Yang Ditugaskan").child("Subbid 3").child("Status").setValue("Belum Ada");
                               myRef.child("Yang Ditugaskan").child("Uploader").child("Status").setValue("Baru Diupload");
                               HashMap<String, String> names = new HashMap <String, String>();
                               names.put("0",kabid);
                               HashMap<String, String> subbid1 = new HashMap <String, String>();
                               subbid1.put("0","Belum Ada");
                               HashMap<String, String> subbid2 = new HashMap <String, String>();
                               subbid2.put("0","Belum Ada");
                               HashMap<String, String> subbid3 = new HashMap <String, String>();
                               subbid3.put("0","Belum Ada");
                               HashMap<String, String> uploader = new HashMap <String, String>();
                               uploader.put("0",muploader);
                               myRef.child("Yang Ditugaskan").child("Kabid").child("Pelaksana").setValue(names);
                               myRef.child("Yang Ditugaskan").child("Subbid 1").child("Pelaksana").setValue(subbid1);
                               myRef.child("Yang Ditugaskan").child("Subbid 2").child("Pelaksana").setValue(subbid2);
                               myRef.child("Yang Ditugaskan").child("Subbid 3").child("Pelaksana").setValue(subbid3);
                               myRef.child("Yang Ditugaskan").child("Uploader").child("Pelaksana").setValue(uploader);

                               if (mMemo.getText().length() == 0)
                               {
                                   myRef.child("Memo").setValue("Kosong");
                               } else{
                                   myRef.child("Memo").setValue(mMemo.getText().toString());
                               }

                               Context context = getApplicationContext();
                               CharSequence text = "Surat Berhasil Diupload";
                               int duration = Toast.LENGTH_LONG;

                               Toast toast = Toast.makeText(context, text, duration);
                               toast.setGravity(Gravity.CENTER, 0, 0);
                               toast.show();
                           }
                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {
                       }
                   });
                } else{
                    Context context = getApplicationContext();
                    CharSequence text = "Akun Kabid Belum Ada";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
