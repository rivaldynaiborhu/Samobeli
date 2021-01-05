package com.example.samobeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samobeli.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class RegisterActivity extends AppCompatActivity {

    EditText edtFullname, edtUsername, edtPhoneNumber, edtPassword, edtRepeatPassword;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtFullname = (EditText) findViewById(R.id.edtFullname);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtRepeatPassword = (EditText) findViewById(R.id.edtRepeatPassword);
        btn_register = (Button) findViewById(R.id.btn_register);

        //Inisiasi Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user = database.getReference("User");

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.setMessage("Tolong tunggu..");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //mengecek jika nomor hp sudah terdaftar

                        if (!edtPassword.getText().toString().equals(edtRepeatPassword.getText().toString()) )
                        {
                            mDialog.dismiss();
                            edtRepeatPassword.setError("Password Harus sama");
                        }

                        else if (dataSnapshot.child(edtUsername.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            edtUsername.setError("Username sudah terfaftar didatabase");
                            Toast.makeText(RegisterActivity.this, "Username sudah terdaftar di Database", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mDialog.dismiss();
                            User user = new User (edtFullname.getText().toString(), edtPhoneNumber.getText().toString(), edtPassword.getText().toString());

                            table_user.child(edtUsername.getText().toString()).setValue(user);
                            Toast.makeText(RegisterActivity.this, "Pendaftaran Berhasil!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}