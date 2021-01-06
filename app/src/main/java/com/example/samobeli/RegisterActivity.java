package com.example.samobeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samobeli.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtFullname, edtEmail, edtPassword, edtRepeatPassword, edtPhoneNumber;
    Button btn_register;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        edtFullname = (EditText) findViewById(R.id.edtFullname);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtRepeatPassword = (EditText) findViewById(R.id.edtRepeatPassword);
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                registerUser();
                break;
        }
    }

    private void registerUser() {


        String email = edtEmail.getText().toString().trim();
        String fullName = edtFullname.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (fullName.isEmpty()){
            edtFullname.setError("Nama tidak boleh kosong");
            edtFullname.requestFocus();
            return;
        }
        if (email.isEmpty()){
            edtEmail.setError("Email tidak boleh kosong");
            edtEmail.requestFocus();
            return;
        }
        if (phoneNumber.isEmpty()){
            edtPhoneNumber.setError("Nomor telepon tidak boleh kosong");
            edtPhoneNumber.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Tolong berikan email yang valid");
            edtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            edtPassword.setError("Password tidak boleh kosong");
            edtPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            edtPassword.setError("Minimal Password adalah 6 karakter!");
            edtPassword.requestFocus();
            return;
        }
        if (!edtPassword.getText().toString().equals(edtRepeatPassword.getText().toString())){
            edtRepeatPassword.setError("Password harus sama!");
            edtRepeatPassword.requestFocus();
            return;
        }


        //mengambil semua nilai

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            User user = new User(fullName, email, phoneNumber, password);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                   .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "Pendaftaran user berhasil!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        Toast.makeText(RegisterActivity.this, "Gagal untuk mendaftar, Silahkan coba lagi!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Gagal untuk mendaftar", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }

    public void goToforgotPassword(View view) {
        Intent intent = new Intent(RegisterActivity.this, forgotpassword.class);
        startActivity(intent);
    }
}