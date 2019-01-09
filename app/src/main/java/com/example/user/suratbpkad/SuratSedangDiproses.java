package com.example.user.suratbpkad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SuratSedangDiproses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surat_sedang_diproses);
        LinearLayout KeteranganSedang = (LinearLayout) findViewById(R.id.ket_sedang);
        KeteranganSedang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuratSedangDiproses.this, KeteranganSedang.class);
                startActivity(i);
            }
        });
    }
}
