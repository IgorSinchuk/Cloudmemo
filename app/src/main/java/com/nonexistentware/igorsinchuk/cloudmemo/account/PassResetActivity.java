package com.nonexistentware.igorsinchuk.cloudmemo.account;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.nonexistentware.igorsinchuk.cloudmemo.R;

public class PassResetActivity extends AppCompatActivity {

    private EditText emailInput;
    private Button resetPass, btnBack;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_reset);

        emailInput = (EditText) findViewById(R.id.emailInput);
        resetPass = (Button) findViewById(R.id.resetPass);
        btnBack = (Button) findViewById(R.id.btnBack);

        mAuth = FirebaseAuth.getInstance();

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();

                if (!TextUtils.isEmpty(email)) {
                    Toast.makeText(PassResetActivity.this, "Enter your registered email", Toast.LENGTH_SHORT).show();
                }

                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(PassResetActivity.this, "Reset instruction was sent to your email", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(PassResetActivity.this, "Failed to send you reset instruction", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PassResetActivity.this, MainActivity.class));
            }
        });

    }
}
