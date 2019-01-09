package com.example.user.suratbpkad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Kembalikan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kembalikan);

        Button mOpenDialogAlihkan=(Button) findViewById(R.id.alihkan);
        mOpenDialogAlihkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KembalikanFragment pilihFile = new KembalikanFragment();
                pilihFile.show(getFragmentManager(),"Pilih File");
            }
        });
    }
}
