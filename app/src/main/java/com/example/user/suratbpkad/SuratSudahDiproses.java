package com.example.user.suratbpkad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SuratSudahDiproses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surat_sudah_diproses);
        LinearLayout KeteranganSudah = (LinearLayout) findViewById(R.id.ket_sudah);
        KeteranganSudah.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuratSudahDiproses.this, KeteranganSudah.class);
                startActivity(i);
            }
        });
    }
}
