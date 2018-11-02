package com.nonexistentware.igorsinchuk.cloudmemo.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nonexistentware.igorsinchuk.cloudmemo.R;

public class RegisterScreen extends AppCompatActivity {

    private EditText emailInput, passInput, nameInput;
    private Button registerButton, backToLogin;
    private FirebaseAuth fAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        fAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        emailInput = (EditText) findViewById(R.id.emailInput);
        passInput = (EditText) findViewById(R.id.passInput);
        nameInput = (EditText) findViewById(R.id.nameInput);

        registerButton = (Button) findViewById(R.id.registerButton);
        backToLogin = (Button) findViewById(R.id.backToLogin);

       registerButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String uName = nameInput.getText().toString().trim();
               String uEmail = emailInput.getText().toString().trim();
               String uPass = passInput.getText().toString().trim();

               registerUser(uName, uEmail, uPass);
           }
       });

       backToLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(RegisterScreen.this, MainActivity.class));
           }
       });
    }


    private void registerUser(final String name, final String email, final String pass) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Register in progress, please waite...");
        progressDialog.show();

        fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mDatabase.child(fAuth.getCurrentUser().getUid())
                            .child("basic").child("name").setValue(name)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();

                                        //startActivity(new Intent(RegisterScreen.this, CrudScreen.class));
                                        finish();
                                        Toast.makeText(RegisterScreen.this, "User created!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterScreen.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterScreen.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
//        }
//
//        return true;
//    }
}
