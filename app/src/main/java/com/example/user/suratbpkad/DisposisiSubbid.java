package com.example.user.suratbpkad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class DisposisiSubbid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disposisi_subbid);
        LinearLayout KeteranganSubbidBaru = (LinearLayout) findViewById(R.id.ket_subbid_baru);
        KeteranganSubbidBaru.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisposisiSubbid.this, KeteranganBaru.class);
                startActivity(i);
            }
        });
    }
}
