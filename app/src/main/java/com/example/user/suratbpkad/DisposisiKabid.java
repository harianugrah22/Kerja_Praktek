package com.example.user.suratbpkad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class DisposisiKabid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disposisi_kabid);
        LinearLayout KeteranganKabidBaru = (LinearLayout) findViewById(R.id.ket_kabid_baru);
        KeteranganKabidBaru.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisposisiKabid.this, KeteranganBaru.class);
                startActivity(i);
            }
        });
    }
}
