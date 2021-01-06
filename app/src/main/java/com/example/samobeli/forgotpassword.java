package com.example.samobeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samobeli.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class forgotpassword extends AppCompatActivity {

    private EditText edtEmail;
    private Button btn_next;
    private ProgressBar progressBar;
    FirebaseAuth  auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgotpassword);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btn_next = (Button) findViewById(R.id.btn_next);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    private void resetPassword() {
        String email = edtEmail.getText().toString().trim();

        if (email.isEmpty())
        {
            edtEmail.setError("Email dibutuhkan");
            edtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            edtEmail.setError("Masukkan valid email!");
            edtEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.GONE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(forgotpassword.this, "Cek email anda untuk mereset password!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(forgotpassword.this, ForgotPasswordSuccess.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(forgotpassword.this, "Coba lagi, Beberapa kesalahan terjadi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




}