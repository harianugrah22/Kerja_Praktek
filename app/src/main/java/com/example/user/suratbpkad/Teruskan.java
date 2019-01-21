package com.example.user.suratbpkad;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Teruskan extends AppCompatActivity {
    SharedPreferences peran;
    String mPeran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teruskan);

        peran = getSharedPreferences("peran", 0);
        mPeran = peran.getString("peran1","Kosong");

        Toast toast = Toast.makeText(getApplicationContext(),mPeran, Toast.LENGTH_SHORT);
        toast.show();
    }
}
