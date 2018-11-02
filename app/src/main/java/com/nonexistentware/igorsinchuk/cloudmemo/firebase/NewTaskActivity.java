package com.nonexistentware.igorsinchuk.cloudmemo.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.nonexistentware.igorsinchuk.cloudmemo.R;

import java.util.HashMap;
import java.util.Map;

public class NewTaskActivity extends AppCompatActivity {

    private EditText titleEdit, descriptionEdit;
    private Button addBtn, deleteBtn;

    private FirebaseAuth fAuth;
    private DatabaseReference fTaskDatabase;

    private String taskId = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        deleteBtn.setVisibility(View.INVISIBLE);

        try {
            taskId = getIntent().getStringExtra("taskId");

            if (taskId.equals("no")) {
                deleteBtn.setVisibility(View.INVISIBLE);
            } else if (!taskId.equals("no")){
                deleteBtn.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        titleEdit = (EditText) findViewById(R.id.titleEdit);
        descriptionEdit = (EditText) findViewById(R.id.descriptionEdit);
        addBtn = (Button) findViewById(R.id.addBtn);


//        mToolbar = (Toolbar) findViewById(R.id.new_note_toolbar);

        fAuth = FirebaseAuth.getInstance();
        fTaskDatabase = FirebaseDatabase.getInstance().getReference().child("Tasks").child(fAuth.getCurrentUser().getUid());

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = titleEdit.getText().toString().trim();
                String description = descriptionEdit.getText().toString().trim();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
                    createTask(title, description);
                } else {
                    Snackbar.make(view, "Fill empty fields", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask();
            }
        });
    }

    public void createTask(String title, String description) {
        if (fAuth.getCurrentUser() !=null) {
            final DatabaseReference newTaskRef = fTaskDatabase.push();

            final Map taskMap = new HashMap();
            taskMap.put("title", title);
            taskMap.put("description", description);
            taskMap.put("timestamp", ServerValue.TIMESTAMP);

            Thread mainThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    newTaskRef.setValue(taskMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                startActivity(new Intent(NewTaskActivity.this, ItemList.class));
                                Toast.makeText(NewTaskActivity.this, "Memo added to database", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(NewTaskActivity.this, "ERROR" + task.getException(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            });

            mainThread.start();

        } else {
            Toast.makeText(NewTaskActivity.this, "User is not signed in", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteTask() {
        fTaskDatabase.child(taskId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(NewTaskActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("NewTaskActivity", task.getException().toString());
                    Toast.makeText(NewTaskActivity.this, "Error: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
