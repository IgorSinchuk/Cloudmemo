package com.nonexistentware.igorsinchuk.cloudmemo.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.nonexistentware.igorsinchuk.cloudmemo.R;
import com.nonexistentware.igorsinchuk.cloudmemo.firebase.TaskControl;

public class MainActivity extends AppCompatActivity {

    private EditText emailInput, passInput;
    private Button loginBtn, signInBtn, forgotPassBtn;
    private TextView infoBtn;
    private FirebaseAuth fAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    loginBtn = (Button) findViewById(R.id.loginBtn);
    signInBtn = (Button) findViewById(R.id.signInBtn);
    forgotPassBtn = (Button) findViewById(R.id.forgotPassBtn);

    emailInput = (EditText) findViewById(R.id.emailInput);
    passInput = (EditText) findViewById(R.id.passInput);

    infoBtn = (TextView) findViewById(R.id.infoBtn);

    fAuth = FirebaseAuth.getInstance();
    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    loginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = emailInput.getText().toString().trim();
            String pass = passInput.getText().toString().trim();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Login in progress, please waite...");
            progressDialog.show();

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
                fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            checkUserExistence();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Couldn't login, user not found", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            } else {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Complete all fields", Toast.LENGTH_SHORT).show();
            }
        }
    });

    signInBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, RegisterScreen.class));
        }
    });

    forgotPassBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, PassResetActivity.class));
        }
    });

    infoBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            startActivity(new Intent(MainActivity.this, InfoActivity.class));
        }
    });

    }

    private void checkUserExistence() {
        final String userId = fAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(userId)) {
                    startActivity(new Intent(MainActivity.this, TaskControl.class)); //add new activity for database
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "User not registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
