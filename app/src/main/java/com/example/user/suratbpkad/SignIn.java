package com.example.user.suratbpkad;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class SignIn extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String[] peran={"- Pilih Peran -","Uploader","Kabid","Kasubbid 1","Kasubbid 2","Kasubbid 3", "Staff Subbid 1","Staff Subbid 2","Staff Subbid 3"};
    EditText mNama;
    EditText mUserName;
    EditText mPassword;
    EditText mConfirmPassword;
    FirebaseDatabase database;
    DatabaseReference mData;
    String kesediaan;
    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mNama = (EditText) findViewById(R.id.nama);
        mUserName = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mConfirmPassword = (EditText) findViewById(R.id.confirm_password);

        spin = (Spinner) findViewById(R.id.peran);
        spin.setOnItemSelectedListener(this);

        database = FirebaseDatabase.getInstance();

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,peran);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNama.getText().length() == 0 || mUserName.getText().length() == 0 || mPassword.getText().length() == 0
                        || mConfirmPassword.getText().length() == 0)
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Terdapat Bagian yang Belum Diisi";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (spin.getSelectedItem().toString().equals("- Pilih Peran -")){
                    Context context = getApplicationContext();
                    CharSequence text = "Peran Belum Dipilih";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else{
                    if (mPassword.getText().toString().equals(mConfirmPassword.getText().toString())){
                        mData = database.getReference("Users");
                        mData.addListenerForSingleValueEvent(new ValueEventListener() {
                            String username;
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists()){
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                                        username = (String) map.get("Username");
                                        if (username.equals(mUserName.getText().toString())){
                                            kesediaan = "No";
                                            break;
                                        } else{
                                            kesediaan = "Yes";
                                        }
                                    }
                                    if (kesediaan.equals("Yes")){
                                        mData = database.getReference("Users").push();
                                        mData.child("Nama").setValue(mNama.getText().toString());
                                        mData.child("Username").setValue(mUserName.getText().toString());
                                        mData.child("Password").setValue(mPassword.getText().toString());
                                        mData.child("Peran").setValue(spin.getSelectedItem().toString());

                                        Context context = getApplicationContext();
                                        CharSequence text = "Akun Berhasil Ditambahkan";
                                        int duration = Toast.LENGTH_LONG;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();

                                    } else {
                                        Context context = getApplicationContext();
                                        CharSequence text = "Akun Telah Ada Dalam Database";
                                        int duration = Toast.LENGTH_LONG;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                } else{
                                    mData = database.getReference("Users").push();
                                    mData.child("Nama").setValue(mNama.getText().toString());
                                    mData.child("Username").setValue(mUserName.getText().toString());
                                    mData.child("Password").setValue(mPassword.getText().toString());
                                    mData.child("Peran").setValue(spin.getSelectedItem().toString());

                                    Context context = getApplicationContext();
                                    CharSequence text = "Akun Berhasil Ditambahkan";
                                    int duration = Toast.LENGTH_LONG;

                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    } else{
                        Context context = getApplicationContext();
                        CharSequence text = "Password dan Confirm Password Tidak Sama";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

