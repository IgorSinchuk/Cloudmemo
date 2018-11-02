package com.nonexistentware.igorsinchuk.cloudmemo.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nonexistentware.igorsinchuk.cloudmemo.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoActivity extends AppCompatActivity {

    private ImageView facebook, github, cv;
    private TextView backBtn;
    private CircleImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
//
//        facebook = (ImageView) findViewById(R.id.facebook);
//        github = (ImageView) findViewById(R.id.github);
//        cv = (ImageView) findViewById(R.id.cv);
//        backBtn = (TextView) findViewById(R.id.backBtn);
//        photo = (CircleImageView) findViewById(R.id.photo);
//
//        photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
//                builder.setTitle("Redirect to website")
//                        .setMessage("Do you want to proceed?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int i) {
//                                Intent website = new Intent();
//                                website.setAction(Intent.ACTION_VIEW);
//                                website.addCategory(Intent.CATEGORY_BROWSABLE);
//                                website.setData(Uri.parse("https://igorsinchuk.github.io"));
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int i) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                builder.create().show();
//            }
//        });
//
//        facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
//                builder.setTitle("Redirect to facebook")
//                        .setMessage("Do you want to proceed?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int i) {
//                                Intent website = new Intent();
//                                website.setAction(Intent.ACTION_VIEW);
//                                website.addCategory(Intent.CATEGORY_BROWSABLE);
//                                website.setData(Uri.parse("https://www.facebook.com/profile.php?id=100003140149327&ref=bookmarks"));
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int i) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                builder.create().show();
//            }
//        });
//
//        cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
//                builder.setTitle("Redirect to CV preview")
//                        .setMessage("Do you want to proceed?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int i) {
//                                Intent website = new Intent();
//                                website.setAction(Intent.ACTION_VIEW);
//                                website.addCategory(Intent.CATEGORY_BROWSABLE);
//                                website.setData(Uri.parse("https://drive.google.com/file/d/175uAcfGT7kDNQPfbDmV99vKwjcLRGNwL/view"));
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int i) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                builder.create().show();
//            }
//        });
//
//        github.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
//                builder.setTitle("Redirect to GitHub")
//                        .setMessage("Do you want to proceed?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int i) {
//                                Intent website = new Intent();
//                                website.setAction(Intent.ACTION_VIEW);
//                                website.addCategory(Intent.CATEGORY_BROWSABLE);
//                                website.setData(Uri.parse("https://github.com"));
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int i) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                builder.create().show();
//            }
//        });
//
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(InfoActivity.this, MainActivity.class));
//            }
//        });

    }
}
