package com.nonexistentware.igorsinchuk.cloudmemo.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.nonexistentware.igorsinchuk.cloudmemo.R;
import com.nonexistentware.igorsinchuk.cloudmemo.model.TaskModel;

import java.util.HashMap;
import java.util.Map;

public class TaskControl extends AppCompatActivity {

    private Button submitBtn;
    private EditText titleEdit, descriptionEdit;
    private DatabaseReference reference;
    private FirebaseAuth fAuth;
    private RecyclerView recyclerView;

    private FirebaseRecyclerOptions<TaskModel> options;
    private FirebaseRecyclerAdapter<TaskModel, RecyclerViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_control);

        submitBtn = (Button) findViewById(R.id.submitBtn);
        titleEdit = (EditText) findViewById(R.id.titleEdit);
        descriptionEdit = (EditText) findViewById(R.id.descriptionEdit);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager manger = new LinearLayoutManager(this);
        manger.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manger);
        recyclerView.setAdapter(adapter);

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Tasks").child(fAuth.getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showTask();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

        showTask();
    }

    @Override
    protected void onStop() {
        if (adapter != null) {
            adapter.stopListening();
        }
        super.onStop();
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

    public void showTask() {
        options = new FirebaseRecyclerOptions.Builder<TaskModel>()
                .setQuery(reference, TaskModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<TaskModel, RecyclerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, @NonNull TaskModel model) {
                holder.txtTitle.setText(model.getTitle());
                holder.txtDescription.setText(model.getDescription());
            }

            @NonNull
            @Override
            public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.task_list, viewGroup, false);
                return new RecyclerViewHolder(itemView);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
