package com.example.user.suratbpkad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class RingkasanAct extends AppCompatActivity {
    private FirebaseDatabase mData;
    DatabaseReference mRef;
    DatabaseReference mAkun;
    ArrayList<Ringkasan> ringkasans = new ArrayList<>();
    SharedPreferences peran;
    String mPeran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringkasan);

        peran = getSharedPreferences("peran", 0);
        mPeran = peran.getString("peran1","Kosong");
        if (mPeran.equals("Kabid")){
            Tampilan_Ringkasan();
        } else if (mPeran.equals("Kasubbid 1")){
            Tampilan_Subbid1();
        } else if (mPeran.equals("Kasubbid 2")){
            Tampilan_Subbid2();
        } else if (mPeran.equals("Kasubbid 3")){
            Tampilan_Subbid3();
        }
    }

    public void Tampilan_Ringkasan(){
        ringkasans.clear();
        mData = FirebaseDatabase.getInstance();
        mRef = mData.getReference("Surat");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int baru1=0;
                    int diproses1=0;
                    int direview1=0;
                    int diverifikasi1=0;
                    int selesai1=0;
                    int nomor=0;
                    int baru2=0;
                    int diproses2=0;
                    int direview2=0;
                    int diverifikasi2=0;
                    int selesai2=0;
                    int baru3=0;
                    int diproses3=0;
                    int direview3=0;
                    int diverifikasi3=0;
                    int selesai3=0;
                    int baru4=0;
                    int diproses4=0;
                    int direview4=0;
                    int diverifikasi4=0;
                    int selesai4=0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String key = ds.getKey();
                        String status1 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child("Kabid").child("Status").getValue();
                        String status2 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child("Subbid 1").child("Status").getValue();
                        String status3 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child("Subbid 2").child("Status").getValue();
                        String status4 = (String) dataSnapshot.child(key).child("Yang Ditugaskan").child("Subbid 3").child("Status").getValue();
                        if (status1.equals("Baru Diupload")){
                            baru1 = baru1 + 1;
                        } else if (status1.equals("Sedang Diproses")){
                            diproses1 = diproses1 + 1;
                        } else if (status1.equals("Sedang Direview")){
                            direview1 = direview1 + 1;
                        } else if (status1.equals("Sedang Diverifikasi")){
                            diverifikasi1 = diverifikasi1+1;
                        } else if (status1.equals("Selesai")){
                            selesai1 = selesai1+1;
                        }
                        if (status2.equals("Baru Diupload")){
                            baru2 = baru2 + 1;
                        } else if (status2.equals("Sedang Diproses")){
                            diproses2 = diproses2 + 1;
                        } else if (status2.equals("Sedang Direview")){
                            direview2 = direview2 + 1;
                        } else if (status2.equals("Sedang Diverifikasi")){
                            diverifikasi2 = diverifikasi2+1;
                        } else if (status2.equals("Selesai")){
                            selesai2 = selesai2+1;
                        }
                        if (status3.equals("Baru Diupload")){
                            baru3 = baru3 + 1;
                        } else if (status3.equals("Sedang Diproses")){
                            diproses3 = diproses3 + 1;
                        } else if (status3.equals("Sedang Direview")){
                            direview3 = direview3 + 1;
                        } else if (status3.equals("Sedang Diverifikasi")){
                            diverifikasi3 = diverifikasi3+1;
                        } else if (status3.equals("Selesai")){
                            selesai3 = selesai3+1;
                        }
                        if (status4.equals("Baru Diupload")){
                            baru4 = baru4 + 1;
                        } else if (status4.equals("Sedang Diproses")){
                            diproses4 = diproses4 + 1;
                        } else if (status4.equals("Sedang Direview")){
                            direview4 = direview4 + 1;
                        } else if (status4.equals("Sedang Diverifikasi")){
                            diverifikasi4 = diverifikasi4+1;
                        } else if (status4.equals("Selesai")){
                            selesai4 = selesai4+1;
                        }
                    }
                    Ringkasan ringkasan = new Ringkasan();
                    nomor=nomor+1;
                    ringkasan.setNama_ring("Kabid");
                    ringkasan.setPenomoran_ring(Integer.toString(nomor));
                    ringkasan.setBaru_Diupload(Integer.toString(baru1));
                    ringkasan.setSedang_Diproses(Integer.toString(diproses1));
                    ringkasan.setSedang_Direview(Integer.toString(direview1));
                    ringkasan.setMenunggu_Verifikasi(Integer.toString(diverifikasi1));
                    ringkasan.setSelesai(Integer.toString(selesai1));
                    ringkasans.add(ringkasan);

                    Ringkasan ringkasan1 = new Ringkasan();
                    nomor=nomor+1;
                    ringkasan1.setNama_ring("Subbid 1");
                    ringkasan1.setPenomoran_ring(Integer.toString(nomor));
                    ringkasan1.setBaru_Diupload(Integer.toString(baru2));
                    ringkasan1.setSedang_Diproses(Integer.toString(diproses2));
                    ringkasan1.setSedang_Direview(Integer.toString(direview2));
                    ringkasan1.setMenunggu_Verifikasi(Integer.toString(diverifikasi2));
                    ringkasan1.setSelesai(Integer.toString(selesai2));
                    ringkasans.add(ringkasan1);

                    Ringkasan ringkasan2 = new Ringkasan();
                    nomor=nomor+1;
                    ringkasan2.setNama_ring("Subbid 2");
                    ringkasan2.setPenomoran_ring(Integer.toString(nomor));
                    ringkasan2.setBaru_Diupload(Integer.toString(baru3));
                    ringkasan2.setSedang_Diproses(Integer.toString(diproses3));
                    ringkasan2.setSedang_Direview(Integer.toString(direview3));
                    ringkasan2.setMenunggu_Verifikasi(Integer.toString(diverifikasi3));
                    ringkasan2.setSelesai(Integer.toString(selesai3));
                    ringkasans.add(ringkasan2);

                    Ringkasan ringkasan3 = new Ringkasan();
                    nomor=nomor+1;
                    ringkasan3.setNama_ring("Subbid 3");
                    ringkasan3.setPenomoran_ring(Integer.toString(nomor));
                    ringkasan3.setBaru_Diupload(Integer.toString(baru4));
                    ringkasan3.setSedang_Diproses(Integer.toString(diproses4));
                    ringkasan3.setSedang_Direview(Integer.toString(direview4));
                    ringkasan3.setMenunggu_Verifikasi(Integer.toString(diverifikasi4));
                    ringkasan3.setSelesai(Integer.toString(selesai4));
                    ringkasans.add(ringkasan3);

                    ListView listView1 = (ListView) findViewById(R.id.view_ringkasan);
                    RingkasanAdapter adapter = new RingkasanAdapter(RingkasanAct.this, ringkasans);
                    listView1.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void Tampilan_Subbid1(){
        ringkasans.clear();
        mData = FirebaseDatabase.getInstance();
        mRef = mData.getReference("Surat");
        mAkun = mData.getReference("Users");
        mAkun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot1) {
                        if (dataSnapshot.exists()){
                            int nomor=0;
                            int baru2=0;
                            int diproses2=0;
                            int direview2=0;
                            int diverifikasi2=0;
                            int selesai2=0;
                            for (DataSnapshot ds : dataSnapshot.getChildren()){
                                String key = ds.getKey();
                                String bagian = (String) dataSnapshot.child(key).child("Peran").getValue();
                                if (bagian.equals("Kasubbid 1")){
                                    String user = (String) dataSnapshot.child(key).child("Username").getValue();
                                    String nama = (String) dataSnapshot.child(key).child("Nama").getValue();
                                    int baru=0;
                                    int diproses=0;
                                    int direview=0;
                                    int diverifikasi=0;
                                    int selesai=0;
                                    for (DataSnapshot ab : dataSnapshot1.getChildren()){
                                        for (DataSnapshot bc : ab.child("Yang Ditugaskan").child("Subbid 1").child("Pelaksana").getChildren()){
                                            String key2 = bc.getKey();
                                            String user2 = (String) bc.getValue();
                                            if (user2.equals(user)){
                                                String status = (String) ab.child("Yang Ditugaskan").child("Subbid 1").child("Status").getValue();
                                                if (status.equals("Baru Diupload")){
                                                    baru = baru + 1;
                                                } else if (status.equals("Sedang Diproses")){
                                                    diproses = diproses + 1;
                                                } else if (status.equals("Sedang Direview")){
                                                    direview = direview + 1;
                                                } else if (status.equals("Sedang Diverifikasi")){
                                                    diverifikasi = diverifikasi+1;
                                                } else if (status.equals("Selesai")){
                                                    selesai = selesai+1;
                                                }
                                            }
                                        }
                                    }
                                    Ringkasan ringkasan = new Ringkasan();
                                    nomor=nomor+1;
                                    ringkasan.setNama_ring(nama);
                                    ringkasan.setPenomoran_ring(Integer.toString(nomor));
                                    ringkasan.setBaru_Diupload(Integer.toString(baru));
                                    ringkasan.setSedang_Diproses(Integer.toString(diproses));
                                    ringkasan.setSedang_Direview(Integer.toString(direview));
                                    ringkasan.setMenunggu_Verifikasi(Integer.toString(diverifikasi));
                                    ringkasan.setSelesai(Integer.toString(selesai));
                                    ringkasans.add(ringkasan);
                                }
                                if (bagian.equals("Staff Subbid 1")){
                                    String user = (String) dataSnapshot.child(key).child("Username").getValue();
                                    String nama = (String) dataSnapshot.child(key).child("Nama").getValue();
                                    int baru=0;
                                    int diproses=0;
                                    int direview=0;
                                    int diverifikasi=0;
                                    int selesai=0;
                                    for (DataSnapshot ab : dataSnapshot1.getChildren()){
                                        for (DataSnapshot bc : ab.child("Yang Ditugaskan").child("Subbid 1").child("Pelaksana").getChildren()){
                                            String key2 = bc.getKey();
                                            String user2 = (String) bc.getValue();
                                            if (user2.equals(user)){
                                                String status = (String) ab.child("Yang Ditugaskan").child("Subbid 1").child("Status").getValue();
                                                if (status.equals("Baru Diupload")){
                                                    baru = baru + 1;
                                                } else if (status.equals("Sedang Diproses")){
                                                    diproses = diproses + 1;
                                                } else if (status.equals("Sedang Direview")){
                                                    direview = direview + 1;
                                                } else if (status.equals("Sedang Diverifikasi")){
                                                    diverifikasi = diverifikasi+1;
                                                } else if (status.equals("Selesai")){
                                                    selesai = selesai+1;
                                                }
                                            }
                                        }
                                    }
                                    Ringkasan ringkasan = new Ringkasan();
                                    nomor=nomor+1;
                                    ringkasan.setNama_ring(nama);
                                    ringkasan.setPenomoran_ring(Integer.toString(nomor));
                                    ringkasan.setBaru_Diupload(Integer.toString(baru));
                                    ringkasan.setSedang_Diproses(Integer.toString(diproses));
                                    ringkasan.setSedang_Direview(Integer.toString(direview));
                                    ringkasan.setMenunggu_Verifikasi(Integer.toString(diverifikasi));
                                    ringkasan.setSelesai(Integer.toString(selesai));
                                    ringkasans.add(ringkasan);
                                }
                            }
                            for (DataSnapshot a : dataSnapshot1.getChildren()){
                                String key3 = a.getKey();
                                String status2 = (String) dataSnapshot1.child(key3).child("Yang Ditugaskan").child("Subbid 1").child("Status").getValue();
                                if (status2.equals("Baru Diupload")){
                                    baru2 = baru2 + 1;
                                } else if (status2.equals("Sedang Diproses")){
                                    diproses2 = diproses2 + 1;
                                } else if (status2.equals("Sedang Direview")){
                                    direview2 = direview2 + 1;
                                } else if (status2.equals("Sedang Diverifikasi")){
                                    diverifikasi2 = diverifikasi2+1;
                                } else if (status2.equals("Selesai")){
                                    selesai2 = selesai2+1;
                                }
                            }

                            Ringkasan ringkasan1 = new Ringkasan();
                            nomor=nomor+1;
                            ringkasan1.setNama_ring("Total");
                            ringkasan1.setPenomoran_ring(Integer.toString(nomor));
                            ringkasan1.setBaru_Diupload(Integer.toString(baru2));
                            ringkasan1.setSedang_Diproses(Integer.toString(diproses2));
                            ringkasan1.setSedang_Direview(Integer.toString(direview2));
                            ringkasan1.setMenunggu_Verifikasi(Integer.toString(diverifikasi2));
                            ringkasan1.setSelesai(Integer.toString(selesai2));
                            ringkasans.add(ringkasan1);

                            ListView listView1 = (ListView) findViewById(R.id.view_ringkasan);
                            RingkasanAdapter adapter = new RingkasanAdapter(RingkasanAct.this, ringkasans);
                            listView1.setAdapter(adapter);
                        } else{

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void Tampilan_Subbid2(){
        ringkasans.clear();
        mData = FirebaseDatabase.getInstance();
        mRef = mData.getReference("Surat");
        mAkun = mData.getReference("Users");
        mAkun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot1) {
                        if (dataSnapshot.exists()){
                            int nomor=0;
                            int baru2=0;
                            int diproses2=0;
                            int direview2=0;
                            int diverifikasi2=0;
                            int selesai2=0;
                            for (DataSnapshot ds : dataSnapshot.getChildren()){
                                String key = ds.getKey();
                                String bagian = (String) dataSnapshot.child(key).child("Peran").getValue();
                                if (bagian.equals("Kasubbid 2")){
                                    String user = (String) dataSnapshot.child(key).child("Username").getValue();
                                    String nama = (String) dataSnapshot.child(key).child("Nama").getValue();
                                    int baru=0;
                                    int diproses=0;
                                    int direview=0;
                                    int diverifikasi=0;
                                    int selesai=0;
                                    for (DataSnapshot ab : dataSnapshot1.getChildren()){
                                        for (DataSnapshot bc : ab.child("Yang Ditugaskan").child("Subbid 2").child("Pelaksana").getChildren()){
                                            String key2 = bc.getKey();
                                            String user2 = (String) bc.getValue();
                                            if (user2.equals(user)){
                                                String status = (String) ab.child("Yang Ditugaskan").child("Subbid 2").child("Status").getValue();
                                                if (status.equals("Baru Diupload")){
                                                    baru = baru + 1;
                                                } else if (status.equals("Sedang Diproses")){
                                                    diproses = diproses + 1;
                                                } else if (status.equals("Sedang Direview")){
                                                    direview = direview + 1;
                                                } else if (status.equals("Sedang Diverifikasi")){
                                                    diverifikasi = diverifikasi+1;
                                                } else if (status.equals("Selesai")){
                                                    selesai = selesai+1;
                                                }
                                            }
                                        }
                                    }
                                    Ringkasan ringkasan = new Ringkasan();
                                    nomor=nomor+1;
                                    ringkasan.setNama_ring(nama);
                                    ringkasan.setPenomoran_ring(Integer.toString(nomor));
                                    ringkasan.setBaru_Diupload(Integer.toString(baru));
                                    ringkasan.setSedang_Diproses(Integer.toString(diproses));
                                    ringkasan.setSedang_Direview(Integer.toString(direview));
                                    ringkasan.setMenunggu_Verifikasi(Integer.toString(diverifikasi));
                                    ringkasan.setSelesai(Integer.toString(selesai));
                                    ringkasans.add(ringkasan);
                                }
                                if (bagian.equals("Staff Subbid 2")){
                                    String user = (String) dataSnapshot.child(key).child("Username").getValue();
                                    String nama = (String) dataSnapshot.child(key).child("Nama").getValue();
                                    int baru=0;
                                    int diproses=0;
                                    int direview=0;
                                    int diverifikasi=0;
                                    int selesai=0;
                                    for (DataSnapshot ab : dataSnapshot1.getChildren()){
                                        for (DataSnapshot bc : ab.child("Yang Ditugaskan").child("Subbid 2").child("Pelaksana").getChildren()){
                                            String key2 = bc.getKey();
                                            String user2 = (String) bc.getValue();
                                            if (user2.equals(user)){
                                                String status = (String) ab.child("Yang Ditugaskan").child("Subbid 2").child("Status").getValue();
                                                if (status.equals("Baru Diupload")){
                                                    baru = baru + 1;
                                                } else if (status.equals("Sedang Diproses")){
                                                    diproses = diproses + 1;
                                                } else if (status.equals("Sedang Direview")){
                                                    direview = direview + 1;
                                                } else if (status.equals("Sedang Diverifikasi")){
                                                    diverifikasi = diverifikasi+1;
                                                } else if (status.equals("Selesai")){
                                                    selesai = selesai+1;
                                                }
                                            }
                                        }
                                    }
                                    Ringkasan ringkasan = new Ringkasan();
                                    nomor=nomor+1;
                                    ringkasan.setNama_ring(nama);
                                    ringkasan.setPenomoran_ring(Integer.toString(nomor));
                                    ringkasan.setBaru_Diupload(Integer.toString(baru));
                                    ringkasan.setSedang_Diproses(Integer.toString(diproses));
                                    ringkasan.setSedang_Direview(Integer.toString(direview));
                                    ringkasan.setMenunggu_Verifikasi(Integer.toString(diverifikasi));
                                    ringkasan.setSelesai(Integer.toString(selesai));
                                    ringkasans.add(ringkasan);
                                }
                            }
                            for (DataSnapshot a : dataSnapshot1.getChildren()){
                                String key3 = a.getKey();
                                String status2 = (String) dataSnapshot1.child(key3).child("Yang Ditugaskan").child("Subbid 2").child("Status").getValue();
                                if (status2.equals("Baru Diupload")){
                                    baru2 = baru2 + 1;
                                } else if (status2.equals("Sedang Diproses")){
                                    diproses2 = diproses2 + 1;
                                } else if (status2.equals("Sedang Direview")){
                                    direview2 = direview2 + 1;
                                } else if (status2.equals("Sedang Diverifikasi")){
                                    diverifikasi2 = diverifikasi2+1;
                                } else if (status2.equals("Selesai")){
                                    selesai2 = selesai2+1;
                                }
                            }

                            Ringkasan ringkasan1 = new Ringkasan();
                            nomor=nomor+1;
                            ringkasan1.setNama_ring("Total");
                            ringkasan1.setPenomoran_ring(Integer.toString(nomor));
                            ringkasan1.setBaru_Diupload(Integer.toString(baru2));
                            ringkasan1.setSedang_Diproses(Integer.toString(diproses2));
                            ringkasan1.setSedang_Direview(Integer.toString(direview2));
                            ringkasan1.setMenunggu_Verifikasi(Integer.toString(diverifikasi2));
                            ringkasan1.setSelesai(Integer.toString(selesai2));
                            ringkasans.add(ringkasan1);

                            ListView listView1 = (ListView) findViewById(R.id.view_ringkasan);
                            RingkasanAdapter adapter = new RingkasanAdapter(RingkasanAct.this, ringkasans);
                            listView1.setAdapter(adapter);
                        } else{

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void Tampilan_Subbid3(){
        ringkasans.clear();
        mData = FirebaseDatabase.getInstance();
        mRef = mData.getReference("Surat");
        mAkun = mData.getReference("Users");
        mAkun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot1) {
                        if (dataSnapshot.exists()){
                            int nomor=0;
                            int baru2=0;
                            int diproses2=0;
                            int direview2=0;
                            int diverifikasi2=0;
                            int selesai2=0;
                            for (DataSnapshot ds : dataSnapshot.getChildren()){
                                String key = ds.getKey();
                                String bagian = (String) dataSnapshot.child(key).child("Peran").getValue();
                                if (bagian.equals("Kasubbid 3")){
                                    String user = (String) dataSnapshot.child(key).child("Username").getValue();
                                    String nama = (String) dataSnapshot.child(key).child("Nama").getValue();
                                    int baru=0;
                                    int diproses=0;
                                    int direview=0;
                                    int diverifikasi=0;
                                    int selesai=0;
                                    for (DataSnapshot ab : dataSnapshot1.getChildren()){
                                        for (DataSnapshot bc : ab.child("Yang Ditugaskan").child("Subbid 3").child("Pelaksana").getChildren()){
                                            String key2 = bc.getKey();
                                            String user2 = (String) bc.getValue();
                                            if (user2.equals(user)){
                                                String status = (String) ab.child("Yang Ditugaskan").child("Subbid 3").child("Status").getValue();
                                                if (status.equals("Baru Diupload")){
                                                    baru = baru + 1;
                                                } else if (status.equals("Sedang Diproses")){
                                                    diproses = diproses + 1;
                                                } else if (status.equals("Sedang Direview")){
                                                    direview = direview + 1;
                                                } else if (status.equals("Sedang Diverifikasi")){
                                                    diverifikasi = diverifikasi+1;
                                                } else if (status.equals("Selesai")){
                                                    selesai = selesai+1;
                                                }
                                            }
                                        }
                                    }
                                    Ringkasan ringkasan = new Ringkasan();
                                    nomor=nomor+1;
                                    ringkasan.setNama_ring(nama);
                                    ringkasan.setPenomoran_ring(Integer.toString(nomor));
                                    ringkasan.setBaru_Diupload(Integer.toString(baru));
                                    ringkasan.setSedang_Diproses(Integer.toString(diproses));
                                    ringkasan.setSedang_Direview(Integer.toString(direview));
                                    ringkasan.setMenunggu_Verifikasi(Integer.toString(diverifikasi));
                                    ringkasan.setSelesai(Integer.toString(selesai));
                                    ringkasans.add(ringkasan);
                                }
                                if (bagian.equals("Staff Subbid 3")){
                                    String user = (String) dataSnapshot.child(key).child("Username").getValue();
                                    String nama = (String) dataSnapshot.child(key).child("Nama").getValue();
                                    int baru=0;
                                    int diproses=0;
                                    int direview=0;
                                    int diverifikasi=0;
                                    int selesai=0;
                                    for (DataSnapshot ab : dataSnapshot1.getChildren()){
                                        for (DataSnapshot bc : ab.child("Yang Ditugaskan").child("Subbid 3").child("Pelaksana").getChildren()){
                                            String key2 = bc.getKey();
                                            String user2 = (String) bc.getValue();
                                            if (user2.equals(user)){
                                                String status = (String) ab.child("Yang Ditugaskan").child("Subbid 3").child("Status").getValue();
                                                if (status.equals("Baru Diupload")){
                                                    baru = baru + 1;
                                                } else if (status.equals("Sedang Diproses")){
                                                    diproses = diproses + 1;
                                                } else if (status.equals("Sedang Direview")){
                                                    direview = direview + 1;
                                                } else if (status.equals("Sedang Diverifikasi")){
                                                    diverifikasi = diverifikasi+1;
                                                } else if (status.equals("Selesai")){
                                                    selesai = selesai+1;
                                                }
                                            }
                                        }
                                    }
                                    Ringkasan ringkasan = new Ringkasan();
                                    nomor=nomor+1;
                                    ringkasan.setNama_ring(nama);
                                    ringkasan.setPenomoran_ring(Integer.toString(nomor));
                                    ringkasan.setBaru_Diupload(Integer.toString(baru));
                                    ringkasan.setSedang_Diproses(Integer.toString(diproses));
                                    ringkasan.setSedang_Direview(Integer.toString(direview));
                                    ringkasan.setMenunggu_Verifikasi(Integer.toString(diverifikasi));
                                    ringkasan.setSelesai(Integer.toString(selesai));
                                    ringkasans.add(ringkasan);
                                }
                            }
                            for (DataSnapshot a : dataSnapshot1.getChildren()){
                                String key3 = a.getKey();
                                String status2 = (String) dataSnapshot1.child(key3).child("Yang Ditugaskan").child("Subbid 3").child("Status").getValue();
                                if (status2.equals("Baru Diupload")){
                                    baru2 = baru2 + 1;
                                } else if (status2.equals("Sedang Diproses")){
                                    diproses2 = diproses2 + 1;
                                } else if (status2.equals("Sedang Direview")){
                                    direview2 = direview2 + 1;
                                } else if (status2.equals("Sedang Diverifikasi")){
                                    diverifikasi2 = diverifikasi2+1;
                                } else if (status2.equals("Selesai")){
                                    selesai2 = selesai2+1;
                                }
                            }

                            Ringkasan ringkasan1 = new Ringkasan();
                            nomor=nomor+1;
                            ringkasan1.setNama_ring("Total");
                            ringkasan1.setPenomoran_ring(Integer.toString(nomor));
                            ringkasan1.setBaru_Diupload(Integer.toString(baru2));
                            ringkasan1.setSedang_Diproses(Integer.toString(diproses2));
                            ringkasan1.setSedang_Direview(Integer.toString(direview2));
                            ringkasan1.setMenunggu_Verifikasi(Integer.toString(diverifikasi2));
                            ringkasan1.setSelesai(Integer.toString(selesai2));
                            ringkasans.add(ringkasan1);

                            ListView listView1 = (ListView) findViewById(R.id.view_ringkasan);
                            RingkasanAdapter adapter = new RingkasanAdapter(RingkasanAct.this, ringkasans);
                            listView1.setAdapter(adapter);
                        } else{

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
