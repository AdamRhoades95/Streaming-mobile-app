package com.akgames.kimsstreamer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class manageUpload extends AppCompatActivity implements listAdapter.OnItemClickListener{
    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mIcon = new ArrayList<>();
    Button home;
    RecyclerView movielist;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private List<newUpload> mUploads;
    private listAdapter mAdapter;
    private ValueEventListener mDBListener;
    private SharedPreferences sharedPreferences;
    private  String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_upload);
        //getImages();
        movielist = findViewById(R.id.myUploads);
        movielist.setHasFixedSize(true);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Movie");

        movielist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));

        //progressDialog = new ProgressDialog(this);

        mUploads = new ArrayList<>();
        mAdapter = new listAdapter(manageUpload.this, mUploads);
        movielist.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(manageUpload.this);

        sharedPreferences = getSharedPreferences("kimsStreamerProfileSaved",MODE_PRIVATE);
        //author = sharedPreferences.getString("publicname","");
        user = sharedPreferences.getString("publicname","");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    newUpload upload = postSnapshot.getValue(newUpload.class);
                    upload.setKey(postSnapshot.getKey());
                    if(upload.getAuthor().equals(user)) {
                        mUploads.add(upload);
                    }
                }
                mAdapter.notifyDataSetChanged();
                //movielist.setAdapter(mAdapter);
                //mAdapter.setOnItemClickListener(ImagesActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(manageUpload.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Music");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //mUploads.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    newUpload upload = postSnapshot.getValue(newUpload.class);
                    upload.setKey(postSnapshot.getKey());
                    if(upload.getAuthor().equals(user)) {
                        mUploads.add(upload);
                    }
                }
                mAdapter.notifyDataSetChanged();
                //movielist.setAdapter(mAdapter);
                //mAdapter.setOnItemClickListener(ImagesActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(manageUpload.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Podcast");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //mUploads.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    newUpload upload = postSnapshot.getValue(newUpload.class);
                    upload.setKey(postSnapshot.getKey());
                    if(upload.getAuthor().equals(user)) {
                        mUploads.add(upload);
                    }
                }
                mAdapter.notifyDataSetChanged();
                //movielist.setAdapter(mAdapter);
                //mAdapter.setOnItemClickListener(ImagesActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(manageUpload.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        home = findViewById(R.id.homepage);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onItemClicked(int position, newUpload uploadCurrent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(manageUpload.this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                newUpload selectedItem = mUploads.get(position);

                final String selectedKey = selectedItem.getKey();
                try {
                    FirebaseDatabase.getInstance().getReference().child("uploads").child(selectedItem.getFileType()).child(selectedKey).removeValue();
                }catch (Exception e){}
                try{
                StorageReference imageRef2 = mStorage.getReferenceFromUrl(selectedItem.getFileUrl());
                imageRef2.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
                        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(manageUpload.this, "Item deleted", Toast.LENGTH_SHORT).show();
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                });
                }catch (Exception e){}
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }

    @Override
    public void onWhatClicked(int position) {
        //Toast.makeText(manageUpload.this, "Item deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClicked(int position) {
        //Toast.makeText(manageUpload.this, "Item deleted", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}