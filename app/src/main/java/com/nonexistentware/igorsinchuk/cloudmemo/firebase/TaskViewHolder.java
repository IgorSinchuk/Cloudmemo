package com.nonexistentware.igorsinchuk.cloudmemo.firebase;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nonexistentware.igorsinchuk.cloudmemo.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    View mView;
    TextView taskTitle, taskTime;
    CardView cardView;

    public TaskViewHolder( View itemView) {
        super(itemView);
        mView = itemView;

        taskTime = (TextView) mView.findViewById(R.id.taskTime);
        taskTitle = (TextView) mView.findViewById(R.id.taskTitle);
        cardView = (CardView) mView.findViewById(R.id.cardView);
    }



    public void setTaskTitle(String title) {
        taskTitle.setText(title);
    }

    public void setTaskTime(String time) {
        taskTime.setText(time);
    }
}
