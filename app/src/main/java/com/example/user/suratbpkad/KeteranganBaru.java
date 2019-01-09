package com.example.user.suratbpkad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class KeteranganBaru extends AppCompatActivity {

    Button mOpenDialogKembalikan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keterangan_baru);
        Button Teruskan = (Button) findViewById(R.id.teruskan);
        Teruskan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KeteranganBaru.this, Teruskan.class);
                startActivity(i);
            }
        });
        mOpenDialogKembalikan=(Button) findViewById(R.id.kembalikan);
        mOpenDialogKembalikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KeteranganBaru.this, Kembalikan.class);
                startActivity(i);
            }
        });

    }
}
