package com.example.samobeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samobeli.Model.User;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtEmail, edtPassword;
    Button btn_login;

    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

    }
    public void goToforgotPassword(View view) {
        Intent intent = new Intent(LoginActivity.this, forgotpassword.class);
        startActivity(intent);
    }

    public void goToRegisterActivity(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btn_login:
                    userLogin();
                    break;
            }
    }

    private void userLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty())
        {
            edtEmail.setError("Email dibutuhkan!");
            edtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            edtEmail.setError("Email harus valid");
            edtEmail.requestFocus();
            return;
        }

        if (password.isEmpty())
        {
            edtPassword.setError("Password dibutuhkan");
            edtPassword.requestFocus();
            return;
        }

        if (password.length() < 6)
        {
            edtPassword.setError("Password harus 6 karakter!");
            edtPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.GONE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    //ke direct ke user profil/ home activity
                    startActivity(new Intent(LoginActivity.this, activity_home.class));
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Login gagal, cek kembali data anda!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}