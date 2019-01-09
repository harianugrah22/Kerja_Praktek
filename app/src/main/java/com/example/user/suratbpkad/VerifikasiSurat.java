package com.example.user.suratbpkad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class VerifikasiSurat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_surat);
        LinearLayout KeteranganSudah = (LinearLayout) findViewById(R.id.ket_verifikasi);
        KeteranganSudah.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VerifikasiSurat.this, KeteranganVerifikasi.class);
                startActivity(i);
            }
        });
    }
}
