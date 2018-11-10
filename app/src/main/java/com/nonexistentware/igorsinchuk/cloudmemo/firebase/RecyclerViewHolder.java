package com.nonexistentware.igorsinchuk.cloudmemo.firebase;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nonexistentware.igorsinchuk.cloudmemo.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView txtTitle, txtDescription;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
    }

    public void setTxtTitle(TextView txtTitle) {
        this.txtTitle = txtTitle;
    }

    public void setTxtDescription(TextView txtDescription) {
        this.txtDescription = txtDescription;
    }
}
