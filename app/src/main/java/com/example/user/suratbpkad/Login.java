package com.example.user.suratbpkad;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity {

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    String username;
    String password;
    String mperan;
    String mnama;
    String cek;
    SharedPreferences users;
    String mUser;
    SharedPreferences pass;
    String mPass;
    SharedPreferences peran;
    String mPeran;
    SharedPreferences nama;
    String mNama;
    SharedPreferences.Editor mUEdit;
    SharedPreferences.Editor mPEdit;
    SharedPreferences.Editor mPerEdit;
    SharedPreferences.Editor mNEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        users = getSharedPreferences("user",0);
        mUser = users.getString("user1","Kosong");
        pass = getSharedPreferences("password", 0);
        mPass = pass.getString("pass1","Kosong");
        peran = getSharedPreferences("peran", 0);
        mPeran = peran.getString("peran1","Kosong");
        nama = getSharedPreferences("nama", 0);
        mNama = nama.getString("nama1","Kosong");

        new InternetCheck(new InternetCheck.Consumer() {
            @Override
            public void accept(Boolean internet) {
                if (internet) {
                    FirebaseDatabase mdata = FirebaseDatabase.getInstance();
                    DatabaseReference mdb = mdata.getReference("Users");
                    mdb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                                    username = (String) map.get("Username");
                                    password = (String) map.get("Password");
                                    if (username.equals(mUser) && password.equals(mPass)) {
                                        Intent i = new Intent(Login.this, HomePage.class);
                                        startActivity(i);

                                        Context context = getApplicationContext();
                                        CharSequence text = "Selamat datang kembali " + mUser;
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();

                                        break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
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

        Button mLogInButton = (Button) findViewById(R.id.login_button);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InternetCheck(new InternetCheck.Consumer() {
                    @Override
                    public void accept(Boolean internet) {
                        if (internet) {
                            if (mUsernameView.getText().toString().equals("Admin") && mPasswordView.getText().toString().equals("Admin123")){
                                Intent  i = new Intent(Login.this,SignIn.class);
                                startActivity(i);
                            } else{
                                FirebaseDatabase mdata = FirebaseDatabase.getInstance();
                                DatabaseReference mdb = mdata.getReference("Users");
                                mdb.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()){
                                            for (DataSnapshot ds : dataSnapshot.getChildren()){
                                                Map<String, Object> map = (Map<String, Object>) ds.getValue();
                                                username = (String) map.get("Username");
                                                password = (String) map.get("Password");
                                                mperan = (String) map.get("Peran");
                                                mnama = (String) map.get("Nama");
                                                if (username.equals(mUsernameView.getText().toString())){
                                                    if (password.equals(mPasswordView.getText().toString())){
                                                        mPerEdit = peran.edit();
                                                        mPerEdit.putString("peran1", mperan).commit();
                                                        mNEdit = nama.edit();
                                                        mNEdit.putString("nama1", mnama).commit();
                                                        mUEdit = users.edit();
                                                        mUEdit.putString("user1", mUsernameView.getText().toString()).commit();
                                                        mPEdit = pass.edit();
                                                        mPEdit.putString("pass1", mPasswordView.getText().toString()).commit();
                                                        Context context = getApplicationContext();
                                                        CharSequence text = "Berhasil Masuk";
                                                        int duration = Toast.LENGTH_SHORT;

                                                        Toast toast = Toast.makeText(context, text, duration);
                                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                                        toast.show();
                                                        Intent  i = new Intent(Login.this,HomePage.class);
                                                        startActivity(i);
                                                        cek = "Yes";
                                                        break;
                                                    } else{
                                                        Context context = getApplicationContext();
                                                        CharSequence text = "Password Salah";
                                                        int duration = Toast.LENGTH_SHORT;

                                                        Toast toast = Toast.makeText(context, text, duration);
                                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                                        toast.show();
                                                        cek = "Yes";
                                                        break;
                                                    }
                                                } else{
                                                    cek = "No";
                                                }
                                            }
                                            if (cek.equals("Yes")){
                                            } else{
                                                Context context = getApplicationContext();
                                                CharSequence text = "Akun Tidak Ada Dalam Database";
                                                int duration = Toast.LENGTH_SHORT;

                                                Toast toast = Toast.makeText(context, text, duration);
                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                toast.show();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }
                        } else {
                            Context context = getApplicationContext();
                            CharSequence text = "Tidak Ada Koneksi";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                });
            }
        });
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}