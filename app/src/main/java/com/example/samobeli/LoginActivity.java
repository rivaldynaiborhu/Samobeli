package com.example.samobeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samobeli.Model.User;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        btn_login = (Button) findViewById(R.id.btn_login);

        //Inisiasi Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user = database.getReference("User");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Edittext fills  requirements
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username)){
                    edtUsername.setError("Username tidak boleh kosong");
                    return;
                }
                else if (TextUtils.isEmpty(password)){
                    edtPassword.setError("Password tidak boleh kosong");
                    return;
                }


                final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Tolong tunggu..");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {

                        //Mengecek apakah user tersedia di database
                        if (dataSnapshot.child(edtUsername.getText().toString()).exists()) {


                            //Mengambil informasi user
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtUsername.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                Toast.makeText(LoginActivity.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Password yang anda masukkan salah", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Pengguna tidak tersedia pada Database", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}