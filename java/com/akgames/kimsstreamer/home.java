package com.akgames.kimsstreamer;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class home extends AppCompatActivity implements listAdapter.OnItemClickListener{
    private Button home, podcast, movies, music, popupMenubtn, search;
    private EditText searchBar;
    private ArrayList<String> moTitle = new ArrayList<>();
    private ArrayList<String> moIcon = new ArrayList<>();
    private ArrayList<String> pTitle = new ArrayList<>();
    private ArrayList<String> pIcon = new ArrayList<>();
    private ArrayList<String> muTitle = new ArrayList<>();
    private ArrayList<String> muIcon = new ArrayList<>();
    private int q;
    private ArrayList<String> tmoTitle = new ArrayList<>();
    private ArrayList<String> tmoIcon = new ArrayList<>();
    private ArrayList<String> tpTitle = new ArrayList<>();
    private ArrayList<String> tpIcon = new ArrayList<>();
    private ArrayList<String> tmuTitle = new ArrayList<>();
    private ArrayList<String> tmuIcon = new ArrayList<>();
    private RecyclerView movielist, podcastlist, musiclist, tmovielist, tpodcastlist, tmusiclist;

    private String user;

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
    //==============================
    //==============================
    private listAdapter tpAdapter;
    private FirebaseStorage tpStorage;
    private Query tpDatabaseRef;
    private List<newUpload> tpUploads;
    private ValueEventListener tpDBListener;
    //---------------------
    private listAdapter tmuAdapter;
    private FirebaseStorage tmuStorage;
    private Query tmuDatabaseRef;
    private List<newUpload> tmuUploads;
    private ValueEventListener tmuDBListener;
    //----------------------
    private listAdapter tmAdapter;
    private FirebaseStorage tmStorage;
    private Query tmDatabaseRef;
    private List<newUpload> tmUploads;
    private ValueEventListener tmDBListener;
    private SharedPreferences sharedPreferences;

    private TextView movieT, musicT, podcastT, TmovieT, TmusicT, TpodcastT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //getImages();

        movieT = findViewById(R.id.movietitle);
        musicT = findViewById(R.id.musictitle);
        podcastT = findViewById(R.id.podcasttitle);
        TmovieT = findViewById(R.id.topmovietitle);
        TmusicT = findViewById(R.id.topmusictitle);
        TpodcastT = findViewById(R.id.toppodcasttitle);

        podcast = findViewById(R.id.podcast);
        movies = findViewById(R.id.movies);
        music = findViewById(R.id.music);
        home = findViewById(R.id.homepage);
        search = findViewById(R.id.search);
        searchBar = findViewById(R.id.searchbar);

        sharedPreferences = getSharedPreferences("kimsStreamerProfileSaved",MODE_PRIVATE);
        user = "_"+sharedPreferences.getString("publicname", "")+"_";

        movielist = findViewById(R.id.movielist);
        movielist.setHasFixedSize(true);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Movie");
        movielist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

        mUploads = new ArrayList<>();
        mAdapter = new listAdapter(home.this, mUploads);

        movielist.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(home.this);

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    newUpload upload = postSnapshot.getValue(newUpload.class);
                    upload.setKey(postSnapshot.getKey());
                    if(upload.getFileType().equals("Movie")&& upload.getViewers().contains(user)) {
                        mUploads.add(upload);
                    }
                }

                if(mUploads.size()==0){
                    movielist.setVisibility(View.GONE);
                    movieT.setVisibility(View.GONE);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(home.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        musiclist = findViewById(R.id.musiclist);
        musiclist.setHasFixedSize(true);
        musiclist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        muUploads = new ArrayList<>();
        muAdapter = new listAdapter(home.this, muUploads);
        musiclist.setAdapter(muAdapter);
        muAdapter.setOnItemClickListener(home.this);

        muStorage = FirebaseStorage.getInstance();
        muDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Music").orderByChild("count");
        muDBListener = muDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                muUploads.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    newUpload muupload = postSnapshot.getValue(newUpload.class);
                    muupload.setKey(postSnapshot.getKey());
                    if(muupload.getFileType().equals("Music")&& muupload.getViewers().contains(user)) {
                        muUploads.add(muupload);
                    }
                }
                if(muUploads.size()==0){
                    musiclist.setVisibility(View.GONE);
                    musicT.setVisibility(View.GONE);
                }
                muAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(home.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        podcastlist = findViewById(R.id.podcastlist);
        podcastlist.setHasFixedSize(true);
        podcastlist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        pUploads = new ArrayList<>();
        pAdapter = new listAdapter(home.this, pUploads);
        podcastlist.setAdapter(pAdapter);
        pAdapter.setOnItemClickListener(home.this);

        pStorage = FirebaseStorage.getInstance();
        pDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Podcast");
        pDBListener = pDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pUploads.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    newUpload pupload = postSnapshot.getValue(newUpload.class);
                    pupload.setKey(postSnapshot.getKey());
                    if(pupload.getFileType().equals("Podcast")&& pupload.getViewers().contains(user)) {
                        pUploads.add(pupload);
                    }
                }
                if(pUploads.size()==0){
                    podcastlist.setVisibility(View.GONE);
                    podcastT.setVisibility(View.GONE);
                }
                pAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(home.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        tpodcastlist = findViewById(R.id.toppodcastlist);
        tpodcastlist.setHasFixedSize(true);
        tpodcastlist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        tpUploads = new ArrayList<>();
        tpAdapter = new listAdapter(home.this, tpUploads);
        tpodcastlist.setAdapter(tpAdapter);
        tpAdapter.setOnItemClickListener(home.this);

        tpStorage = FirebaseStorage.getInstance();
        tpDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Podcast").orderByChild("count").limitToFirst(50);
        tpDBListener = tpDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tpUploads.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    newUpload pupload = postSnapshot.getValue(newUpload.class);
                    pupload.setKey(postSnapshot.getKey());
                    if(pupload.getFileType().equals("Podcast")) {
                        tpUploads.add(pupload);
                    }
                }
                if(tpUploads.size()==0){
                    tpodcastlist.setVisibility(View.GONE);
                    TpodcastT.setVisibility(View.GONE);
                }
                tpAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(home.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        tmusiclist = findViewById(R.id.topmusiclist);
        tmusiclist.setHasFixedSize(true);
        tmusiclist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        tmuUploads = new ArrayList<>();
        tmuAdapter = new listAdapter(home.this, tmuUploads);
        tmusiclist.setAdapter(tmuAdapter);
        tmuAdapter.setOnItemClickListener(home.this);

        tmuStorage = FirebaseStorage.getInstance();
        tmuDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Music").orderByChild("count").limitToFirst(50);
        tmuDBListener = tmuDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tmuUploads.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    newUpload pupload = postSnapshot.getValue(newUpload.class);
                    pupload.setKey(postSnapshot.getKey());
                    if(pupload.getFileType().equals("Music")) {
                        tmuUploads.add(pupload);
                    }
                }
                if(tmuUploads.size()==0){
                    tmusiclist.setVisibility(View.GONE);
                    TmusicT.setVisibility(View.GONE);
                }
                tmuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(home.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        tmovielist = findViewById(R.id.topmovielist);
        tmovielist.setHasFixedSize(true);
        tmovielist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        tmUploads = new ArrayList<>();
        tmAdapter = new listAdapter(home.this, tmUploads);
        tmovielist.setAdapter(tmAdapter);
        tmAdapter.setOnItemClickListener(home.this);

        tmStorage = FirebaseStorage.getInstance();
        tmDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Movie").orderByChild("count").limitToFirst(50);
        tmDBListener = tmDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tmUploads.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    newUpload pupload = postSnapshot.getValue(newUpload.class);
                    pupload.setKey(postSnapshot.getKey());
                    if(pupload.getFileType().equals("Movie")) {
                        tmUploads.add(pupload);
                    }
                }
                if(tmUploads.size()==0){
                    tmovielist.setVisibility(View.GONE);
                    TmovieT.setVisibility(View.GONE);
                }
                tmAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(home.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        popupMenubtn = findViewById(R.id.popdownbar);
        popupMenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(home.this, v);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(home.this);

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
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);
                finish();
            }
        });

        podcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), podcasts.class);
                startActivity(intent);
                finish();
            }
        });
        movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), movies.class);
                startActivity(intent);
                finish();
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), music.class);
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
                        movielist.setLayoutManager(new LinearLayoutManager(home.this, LinearLayoutManager.HORIZONTAL, false));
                        mUploads = new ArrayList<>();
                        mAdapter = new listAdapter(home.this, mUploads);
                        movielist.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(home.this);
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
                                        Toast.makeText(home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                        musiclist = findViewById(R.id.musiclist);
                        musiclist.setHasFixedSize(true);
                        musiclist.setLayoutManager(new LinearLayoutManager(home.this, LinearLayoutManager.HORIZONTAL, false));
                        muUploads = new ArrayList<>();
                        muAdapter = new listAdapter(home.this, muUploads);
                        musiclist.setAdapter(muAdapter);
                        muAdapter.setOnItemClickListener(home.this);

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
                                        Toast.makeText(home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        podcastlist = findViewById(R.id.podcastlist);
                        podcastlist.setHasFixedSize(true);
                        podcastlist.setLayoutManager(new LinearLayoutManager(home.this, LinearLayoutManager.HORIZONTAL, false));
                        pUploads = new ArrayList<>();
                        pAdapter = new listAdapter(home.this, pUploads);
                        podcastlist.setAdapter(pAdapter);
                        pAdapter.setOnItemClickListener(home.this);

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
                                        Toast.makeText(home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch (Exception e){

                    }
                }


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
    public void onItemClicked(int position, newUpload uploadCurrent) {
        //Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
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
        tpDatabaseRef.removeEventListener(tpDBListener);
        tmDatabaseRef.removeEventListener(tmDBListener);
        tmuDatabaseRef.removeEventListener(tmuDBListener);
    }
}