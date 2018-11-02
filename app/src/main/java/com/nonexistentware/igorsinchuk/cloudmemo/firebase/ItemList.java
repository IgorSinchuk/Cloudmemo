package com.nonexistentware.igorsinchuk.cloudmemo.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nonexistentware.igorsinchuk.cloudmemo.R;
import com.nonexistentware.igorsinchuk.cloudmemo.model.TaskModel;

public class ItemList extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private Button createTaskBtn;
    public  RecyclerView mTaskList;
    private GridLayoutManager gridLayoutManager;
    private DatabaseReference fTaskDatabase;
    private FirebaseRecyclerAdapter<TaskModel, TaskViewHolder> firebaseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        mTaskList = (RecyclerView) findViewById(R.id.taskList);

        createTaskBtn = (Button) findViewById(R.id.createTaskBtn);

        gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);

        mTaskList.setHasFixedSize(true);
        mTaskList.setLayoutManager(gridLayoutManager);

        createTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemList.this, NewTaskActivity.class));
            }
        });

        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() !=null ) {
            fTaskDatabase = FirebaseDatabase.getInstance().getReference().child("Tasks").child(fAuth.getCurrentUser().getUid());
        }

        updateUI();

    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<TaskModel, TaskViewHolder>(
                        TaskModel.class,
                R.layout.single_task_layout,
                TaskViewHolder.class,
                        fTaskDatabase
                ) {
            protected void populateViewHolder(final TaskViewHolder viewHolder, TaskModel model, int position) {
                final String taskId = getRef(position).getKey();

                fTaskDatabase.child(taskId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String title = dataSnapshot.child("title").getValue().toString();
                        String timestamp = dataSnapshot.child("timestamp").getValue().toString();

                        viewHolder.setTaskTitle(title);
                        viewHolder.setTaskTime(timestamp);

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ItemList.this, NewTaskActivity.class);
                                intent.putExtra("taskId", taskId);
                                startActivity(intent);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        };

        mTaskList.setAdapter(firebaseRecyclerAdapter);
    }

    private void updateUI() {
        if (fAuth.getCurrentUser() != null) {
            Log.i("ItemList", "fAuth != null");
        } else {
            startActivity(new Intent(ItemList.this, NewTaskActivity.class));
            finish();
            Log.i("ItemList", "fAuth == null");
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//         super.onCreateOptionsMenu(menu);
//
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//
//        switch (item.getItemId()) {
//            case R.id.main_new_note_btn:
//                startActivity(new Intent(ItemList.this, NewTaskActivity.class));
//                break;
//        }
//
//        return true;
//    }
}
