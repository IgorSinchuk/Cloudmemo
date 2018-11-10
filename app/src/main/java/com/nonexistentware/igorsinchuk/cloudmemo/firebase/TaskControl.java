package com.nonexistentware.igorsinchuk.cloudmemo.firebase;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.nonexistentware.igorsinchuk.cloudmemo.R;
import com.nonexistentware.igorsinchuk.cloudmemo.model.TaskModel;

import java.util.HashMap;
import java.util.Map;

public class TaskControl extends AppCompatActivity {

    private Button submitBtn;
    private EditText titleEdit, descriptionEdit;
    private DatabaseReference reference;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_control);

        submitBtn = (Button) findViewById(R.id.submitBtn);
        titleEdit = (EditText) findViewById(R.id.titleEdit);
        descriptionEdit = (EditText) findViewById(R.id.descriptionEdit);

        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Tasks").child(fAuth.getCurrentUser().getUid());


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleEdit.getText().toString().trim();
                String description = descriptionEdit.getText().toString().trim();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
                    newTask(title, description);
                    Toast.makeText(TaskControl.this, "Successfully ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TaskControl.this, "You should fill empty gaps", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void newTask(String title, String description) {

        if (fAuth.getCurrentUser() != null) {
            final DatabaseReference newTaskRef = reference.push();

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
//                                startActivity(new Intent(Task));
                                Toast.makeText(TaskControl.this, "Successfully created", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(TaskControl.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

            mainThread.start();
        } else {
            Toast.makeText(TaskControl.this, "-----", Toast.LENGTH_SHORT).show();
        }

    }
}
