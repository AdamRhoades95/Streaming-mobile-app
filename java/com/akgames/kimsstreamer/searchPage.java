package com.akgames.kimsstreamer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class searchPage extends AppCompatActivity implements listAdapter.OnItemClickListener{
    private Button home, popupMenubtn, search;
    private EditText searchBar;
    private listAdapter mAdapter;
    private FirebaseStorage mStorage;
    private Query mDatabaseRef;
    private List<newUpload> mUploads;
    private ValueEventListener mDBListener;
    //-----------------------------------
    private listAdapter muAdapter;
    private FirebaseStorage muStorage;
    private Query muDatabaseRef;
    private List<newUpload> muUploads;
    private ValueEventListener muDBListener;
    //-----------------------------------
    private listAdapter pAdapter;
    private FirebaseStorage pStorage;
    private Query pDatabaseRef;
    private List<newUpload> pUploads;
    private ValueEventListener pDBListener;

    private RecyclerView movielist, podcastlist, musiclist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        home = findViewById(R.id.homepage);
        search = findViewById(R.id.search);
        searchBar = findViewById(R.id.searchbar);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchBar.getText().toString().equals("")) {
                    try {
                        String string = searchBar.getText().toString().toLowerCase();
                        movielist = findViewById(R.id.movielist);
                        movielist.setHasFixedSize(true);
                        mStorage = FirebaseStorage.getInstance();
                        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Movie");
                        movielist.setLayoutManager(new LinearLayoutManager(searchPage.this, LinearLayoutManager.HORIZONTAL, false));
                        mUploads = new ArrayList<>();
                        mAdapter = new listAdapter(searchPage.this, mUploads);
                        movielist.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(searchPage.this);
                        mDBListener = mDatabaseRef.orderByChild("name").startAt(string).endAt(string + "\uf8ff").addValueEventListener(new ValueEventListener() {


                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                mUploads.clear();
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    newUpload upload = postSnapshot.getValue(newUpload.class);
                                    upload.setKey(postSnapshot.getKey());
                                    if (upload.getFileType().equals("Movie")) {
                                        if(!checkMoviein(upload)) {
                                            mUploads.add(upload);
                                        }
                                    }
                                }
                                mAdapter.notifyDataSetChanged();
                                mDatabaseRef.orderByChild("author").startAt(string).endAt(string + "\uf8ff").addValueEventListener(new ValueEventListener() {


                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                            newUpload upload = postSnapshot.getValue(newUpload.class);
                                            //upload.setKey(postSnapshot.getKey());
                                            if (upload.getFileType().equals("Movie")) {
                                                if(!checkMoviein(upload)) {
                                                    mUploads.add(upload);
                                                }
                                            }
                                        }

                                        mAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(searchPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                                mDatabaseRef.orderByChild("tags").startAt(string).endAt(string + "\uf8ff").addValueEventListener(new ValueEventListener() {


                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                            newUpload upload = postSnapshot.getValue(newUpload.class);
                                            //upload.setKey(postSnapshot.getKey());
                                            if (upload.getFileType().equals("Movie")) {
                                                if(!checkMoviein(upload)) {
                                                    mUploads.add(upload);
                                                }
                                            }
                                        }

                                        mAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(searchPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(searchPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                        musiclist = findViewById(R.id.musiclist);
                        musiclist.setHasFixedSize(true);
                        musiclist.setLayoutManager(new LinearLayoutManager(searchPage.this, LinearLayoutManager.HORIZONTAL, false));
                        muUploads = new ArrayList<>();
                        muAdapter = new listAdapter(searchPage.this, muUploads);
                        musiclist.setAdapter(muAdapter);
                        muAdapter.setOnItemClickListener(searchPage.this);

                        muStorage = FirebaseStorage.getInstance();
                        muDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Music");
                        muDBListener = muDatabaseRef.orderByChild("name").startAt(string).endAt(string + "\uf8ff").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                muUploads.clear();
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    newUpload muupload = postSnapshot.getValue(newUpload.class);
                                    muupload.setKey(postSnapshot.getKey());
                                    if (muupload.getFileType().equals("Music")) {
                                        if(!checkMusicin(muupload))
                                            muUploads.add(muupload);
                                    }
                                }
                                muAdapter.notifyDataSetChanged();
                                muDatabaseRef.orderByChild("author").startAt(string).endAt(string + "\uf8ff").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        muUploads.clear();
                                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                            newUpload muupload = postSnapshot.getValue(newUpload.class);
                                            muupload.setKey(postSnapshot.getKey());
                                            if (muupload.getFileType().equals("Music")) {
                                                if(!checkMusicin(muupload))
                                                    muUploads.add(muupload);
                                            }
                                        }
                                        muAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(searchPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                                muDatabaseRef.orderByChild("tags").startAt(string).endAt(string + "\uf8ff").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        muUploads.clear();
                                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                            newUpload muupload = postSnapshot.getValue(newUpload.class);
                                            muupload.setKey(postSnapshot.getKey());
                                            if (muupload.getFileType().equals("Music")) {
                                                if(!checkMusicin(muupload))
                                                    muUploads.add(muupload);
                                            }
                                        }
                                        muAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(searchPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(searchPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        podcastlist = findViewById(R.id.podcastlist);
                        podcastlist.setHasFixedSize(true);
                        podcastlist.setLayoutManager(new LinearLayoutManager(searchPage.this, LinearLayoutManager.HORIZONTAL, false));
                        pUploads = new ArrayList<>();
                        pAdapter = new listAdapter(searchPage.this, pUploads);
                        podcastlist.setAdapter(pAdapter);
                        pAdapter.setOnItemClickListener(searchPage.this);

                        pStorage = FirebaseStorage.getInstance();
                        pDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Podcast");
                        pDBListener = pDatabaseRef.orderByChild("name").startAt(string).endAt(string + "\uf8ff").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                pUploads.clear();
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    newUpload pupload = postSnapshot.getValue(newUpload.class);
                                    pupload.setKey(postSnapshot.getKey());
                                    if (pupload.getFileType().equals("Podcast")) {
                                        if(!checkPodcastin(pupload)) {
                                            pUploads.add(pupload);
                                        }
                                    }
                                }
                                pAdapter.notifyDataSetChanged();
                                pDatabaseRef.orderByChild("author").startAt(string).endAt(string + "\uf8ff").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        pUploads.clear();
                                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                            newUpload pupload = postSnapshot.getValue(newUpload.class);
                                            pupload.setKey(postSnapshot.getKey());
                                            if (pupload.getFileType().equals("Podcast")) {
                                                if(!checkPodcastin(pupload)) {
                                                    pUploads.add(pupload);
                                                }
                                            }
                                        }
                                        pAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(searchPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                                pDatabaseRef.orderByChild("tags").startAt(string).endAt(string + "\uf8ff").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        pUploads.clear();
                                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                            newUpload pupload = postSnapshot.getValue(newUpload.class);
                                            pupload.setKey(postSnapshot.getKey());
                                            if (pupload.getFileType().equals("Podcast")) {
                                                if(!checkPodcastin(pupload)) {
                                                    pUploads.add(pupload);
                                                }
                                            }
                                        }
                                        pAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(searchPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(searchPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch (Exception e){

                    }
                }


            }
        });

        popupMenubtn = findViewById(R.id.popdownbar);
        popupMenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(searchPage.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.popHome){

                            Intent intent = new Intent(getApplicationContext(), home.class);
                            startActivity(intent);
                            finish();
                            return true;
                        }
                        else if(item.getItemId() == R.id.popMovies){
                            Intent intent = new Intent(getApplicationContext(), movies.class);
                            startActivity(intent);
                            finish();
                            return true;
                        }
                        else if(item.getItemId() == R.id.poppodcasts){
                            Intent intent = new Intent(getApplicationContext(), podcasts.class);
                            startActivity(intent);
                            finish();
                            return true;
                        }
                        else if(item.getItemId() == R.id.popMusic){
                            Intent intent = new Intent(getApplicationContext(), music.class);
                            startActivity(intent);
                            finish();
                            return true;
                        }
                        else if(item.getItemId() == R.id.popSearch){
                            Intent intent = new Intent(getApplicationContext(), searchPage.class);
                            startActivity(intent);
                            finish();
                            return true;
                        }
                        else if(item.getItemId() == R.id.popProfile){
                            Intent intent = new Intent(getApplicationContext(), profile.class);
                            startActivity(intent);
                            finish();
                            return true;
                        }
                        else if(item.getItemId() == R.id.popMethodPay){
                            Intent intent = new Intent(getApplicationContext(), changePayment.class);
                            startActivity(intent);
                            finish();
                            return true;
                        }
                        else if(item.getItemId() == R.id.popmngUpload){
                            Intent intent = new Intent(getApplicationContext(), manageUpload.class);
                            startActivity(intent);
                            finish();
                            return true;
                        }
                        else if(item.getItemId() == R.id.popUpload){
                            Intent intent = new Intent(getApplicationContext(), upload.class);
                            startActivity(intent);
                            finish();
                            return true;
                        }
                        else if(item.getItemId() == R.id.popSignOut){
                            AlertDialog.Builder builder = new AlertDialog.Builder(searchPage.this);

                            builder.setTitle("Confirm");
                            builder.setMessage("Are you sure?");

                            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("kimsStreamerProfileSaved", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("check", "f");
                                    editor.apply();

                                    Intent intent = new Intent(getApplicationContext(), loginScreen.class);
                                    startActivity(intent);
                                    finish();
                                    //return true;
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
                            return true;
                        }
                        else{
                            return false;
                        }


                    }
                });
                popupMenu.show();
            }
        });

    }

    public boolean checkMoviein(newUpload upload){

        for (newUpload NextUpload:mUploads){
            if (NextUpload.getAuthor().equals(upload.getAuthor())&&NextUpload.getFileUrl().equals(upload.getFileUrl())){
                return true;
            }
        }

        return false;
    }

    public boolean checkMusicin(newUpload upload){

        for (newUpload NextUpload:muUploads){
            if (NextUpload.getAuthor().equals(upload.getAuthor())&&NextUpload.getFileUrl().equals(upload.getFileUrl())){
                return true;
            }
        }

        return false;
    }

    public boolean checkPodcastin(newUpload upload){

        for (newUpload NextUpload:pUploads){
            if (NextUpload.getAuthor().equals(upload.getAuthor())&&NextUpload.getFileUrl().equals(upload.getFileUrl())){
                return true;
            }
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(searchPage.this,home.class);

        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClicked(int position, newUpload uploadCurrent) {
        Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
        newUpload selectedItem = uploadCurrent;
        Intent intent;
        String vid = selectedItem.getFileUrl();
        if(selectedItem.getFileType().equals("Movie")){
            intent = new Intent(getApplicationContext(), movieChoice.class);
        }
        else{
            intent = new Intent(getApplicationContext(), musicChoice.class);
        }
        intent.putExtra("vid", vid);
        intent.putExtra("author", selectedItem.getAuthor());
        intent.putExtra("description", selectedItem.getDescript());
        intent.putExtra("icon", selectedItem.getImageUrl());
        intent.putExtra("title", selectedItem.getName());
        intent.putExtra("views", selectedItem.getCount());
        intent.putExtra("type", selectedItem.getFileType());
        intent.putExtra("key", selectedItem.getKey());
        intent.putExtra("tags", selectedItem.getTags());
        startActivity(intent);
        finish();
    }

    @Override
    public void onWhatClicked(int position) {

    }

    @Override
    public void onDeleteClicked(int position) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
        muDatabaseRef.removeEventListener(muDBListener);
        pDatabaseRef.removeEventListener(pDBListener);
    }
}