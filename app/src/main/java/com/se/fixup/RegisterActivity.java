package com.se.fixup;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView emailView;
    private TextView passView;
    private TextView passConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        Button register = findViewById(R.id.register_btn);
        emailView = findViewById(R.id.email_txt);
        passView = findViewById(R.id.pass_txt);
        passConfirm = findViewById(R.id.pass_confirm_txt);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailView.getText().toString();
                String pass = passView.getText().toString();
                String confirm = passConfirm.getText().toString();
                if(!check()) return;
                register(email,pass);
            }
        });

    }

    private void register(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            updateUI();
                        }
                        else
                        {
                            String msg = "Auth Failed: " + task.getException().getMessage().toString();
                            Toast.makeText(RegisterActivity.this,msg, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private boolean check()
    {
        String email = emailView.getText().toString();
        String pass = passView.getText().toString();
        String confirm = passConfirm.getText().toString();
        Boolean valid = true;
        if(email.equals(""))
        {
            emailView.setHint("Please fill email");
            valid = false;
        }
        if(pass.equals(""))
        {
            passView.setHint("Please fill in password");
            valid = false;
        }
        if(confirm.equals("") || (!confirm.equals(pass)))
        {
            passConfirm.setHint("Passwords do not match");
            valid = false;
        }
        return valid;

    }
    private void updateUI() {
        startActivity(new Intent(this,MainActivity.class));
    }
}
