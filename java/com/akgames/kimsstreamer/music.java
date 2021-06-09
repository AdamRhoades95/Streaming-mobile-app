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
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class music extends AppCompatActivity implements listAdapter.OnItemClickListener {
    private Button home,search,popupMenubtn;
    private EditText searchBar;
    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mIcon = new ArrayList<>();
    private ArrayList<RecyclerView> cat = new ArrayList<>();

    private listAdapter tmuAdapter;
    private FirebaseStorage tmuStorage;
    private Query tmuDatabaseRef;
    private List<newUpload> tmuUploads;
    private ValueEventListener tmuDBListener;

    private listAdapter muAdapter;
    private FirebaseStorage muStorage;
    private Query muDatabaseRef;
    private List<newUpload> muUploads;
    private ValueEventListener muDBListener;

    private listAdapter tView1Adapter;
    private FirebaseStorage tView1Storage;
    private Query tView1DatabaseRef;
    private List<newUpload> tView1Uploads;
    private ValueEventListener tView1DBListener;



    private listAdapter tView2Adapter;
    private FirebaseStorage tView2Storage;
    private Query tView2DatabaseRef;
    private List<newUpload> tView2Uploads;
    private ValueEventListener tView2DBListener;

    private listAdapter tView3Adapter;
    private FirebaseStorage tView3Storage;
    private Query tView3DatabaseRef;
    private List<newUpload> tView3Uploads;
    private ValueEventListener tView3DBListener;

    private listAdapter tView4Adapter;
    private FirebaseStorage tView4Storage;
    private Query tView4DatabaseRef;
    private List<newUpload> tView4Uploads;
    private ValueEventListener tView4DBListener;

    private listAdapter tView5Adapter;
    private FirebaseStorage tView5Storage;
    private Query tView5DatabaseRef;
    private List<newUpload> tView5Uploads;
    private ValueEventListener tView5DBListener;



    private RecyclerView movielist, podcastlist, musiclist, tmovielist, tpodcastlist, tmusiclist;
    private SharedPreferences sharedPreferences;
    private String user;
    private TextView movieT, musicT, podcastT, TmovieT, TmusicT, TpodcastT;
    private TextView tViewT1, tViewT2, tViewT3, tViewT4, tViewT5;
    private RecyclerView tView1, tView2, tView3, tView4, tView5;
    private ArrayList<Long> topTags = new ArrayList<>();
    private ArrayList<String> topTagsId = new ArrayList<>();
    private String userstr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        home = findViewById(R.id.homepage);
        search = findViewById(R.id.search);

        sharedPreferences = getSharedPreferences("kimsStreamerProfileSaved",MODE_PRIVATE);
        user = "_"+sharedPreferences.getString("publicname", "")+"_";
        userstr = sharedPreferences.getString("publicname", "");
        popupMenubtn = findViewById(R.id.popdownbar);
        popupMenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(music.this, v);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(music.this);

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
                Intent intent = new Intent(getApplicationContext(),home.class);
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


                        musiclist = findViewById(R.id.musiclist);
                        musiclist.setHasFixedSize(true);
                        musiclist.setLayoutManager(new LinearLayoutManager(music.this, LinearLayoutManager.HORIZONTAL, false));
                        muUploads = new ArrayList<>();
                        muAdapter = new listAdapter(music.this, muUploads);
                        musiclist.setAdapter(muAdapter);
                        muAdapter.setOnItemClickListener(music.this);

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
                                        Toast.makeText(music.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(music.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(music.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }catch (Exception e){

                    }
                }


            }
        });


        musicT = findViewById(R.id.musictitle);
        TmusicT = findViewById(R.id.topmusictitle);

        tViewT1 = findViewById(R.id.tViewT1);
        tViewT2 = findViewById(R.id.tViewT2);
        tViewT3 = findViewById(R.id.tViewT3);
        tViewT4 = findViewById(R.id.tViewT4);
        tViewT5 = findViewById(R.id.tViewT5);

        tView1 = findViewById(R.id.tView1);
        tView2 = findViewById(R.id.tView2);
        tView3 = findViewById(R.id.tView3);
        tView4 = findViewById(R.id.tView4);
        tView5 = findViewById(R.id.tView5);

        musiclist = findViewById(R.id.musiclist);
        musiclist.setHasFixedSize(true);
        musiclist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        muUploads = new ArrayList<>();
        muAdapter = new listAdapter(music.this, muUploads);
        musiclist.setAdapter(muAdapter);
        muAdapter.setOnItemClickListener(music.this);

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
                Toast.makeText(music.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        tmusiclist = findViewById(R.id.topmusiclist);
        tmusiclist.setHasFixedSize(true);
        tmusiclist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        tmuUploads = new ArrayList<>();
        tmuAdapter = new listAdapter(music.this, tmuUploads);
        tmusiclist.setAdapter(tmuAdapter);
        tmuAdapter.setOnItemClickListener(music.this);

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
                Toast.makeText(music.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseDatabase.getInstance().getReference().child("userProfileViews").child(userstr).child("Music").orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //topTags.clear();
//
                if (snapshot.exists()) {

                    userProfileViews userProfileViews1 = snapshot.getValue(userProfileViews.class);
                    int loop = 0;
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        String x = (String) postSnapshot.getValue().toString();

                        topTags.add((Long) Long.parseLong(x));

                    }
                    Collections.sort(topTags);

                    for (Long i : topTags) {
                        if (loop < 5) {
                            String x = String.valueOf(i);
                            if (userProfileViews1.getDrama().equals(x) && !topTagsId.contains("Drama")) {
                                topTagsId.add("Drama");
                            } else if (userProfileViews1.getComedy().equals(x) && !topTagsId.contains("Comedy")) {
                                topTagsId.add("Comedy");
                            } else if (userProfileViews1.getThriller().equals(x) && !topTagsId.contains("Thriller")) {
                                topTagsId.add("Thriller");
                            } else if (userProfileViews1.getRomance().equals(x) && !topTagsId.contains("Romance")) {
                                topTagsId.add("Romance");
                            } else if (userProfileViews1.getAction().equals(x) && !topTagsId.contains("Action")) {
                                topTagsId.add("Action");
                            } else if (userProfileViews1.getHorror().equals(x) && !topTagsId.contains("Horror")) {
                                topTagsId.add("Horror");
                            } else if (userProfileViews1.getCrime().equals(x) && !topTagsId.contains("Crime")) {
                                topTagsId.add("Crime");
                            } else if (userProfileViews1.getAdventure().equals(x) && !topTagsId.contains("Adventure")) {
                                topTagsId.add("Adventure");
                            } else if (userProfileViews1.getMystery().equals(x) && !topTagsId.contains("Mystery")) {
                                topTagsId.add("Mystery");
                            } else if (userProfileViews1.getFamily().equals(x) && !topTagsId.contains("Family")) {
                                topTagsId.add("Family");
                            } else if (userProfileViews1.getWar().equals(x) && !topTagsId.contains("War")) {
                                topTagsId.add("War");
                            } else if (userProfileViews1.getEducational().equals(x) && !topTagsId.contains("Educational")) {
                                topTagsId.add("Educational");
                            } else if (userProfileViews1.getHistory().equals(x) && !topTagsId.contains("History")) {
                                topTagsId.add("History");
                            } else if (userProfileViews1.getSciFi().equals(x) && !topTagsId.contains("Sci-Fi")) {
                                topTagsId.add("Sci-Fi");
                            } else if (userProfileViews1.getMusical().equals(x) && !topTagsId.contains("Musical")) {
                                topTagsId.add("Musical");
                            } else if (userProfileViews1.getAnimation().equals(x) && !topTagsId.contains("Animation")) {
                                topTagsId.add("Animation");
                            } else if (userProfileViews1.getSports().equals(x) && !topTagsId.contains("Sports")) {
                                topTagsId.add("Sports");
                            } else if (userProfileViews1.getNews().equals(x) && !topTagsId.contains("News")) {
                                topTagsId.add("News");
                            } else if (userProfileViews1.getOther().equals(x) && !topTagsId.contains("Other")) {
                                topTagsId.add("Other");
                            } else if (userProfileViews1.getBlues().equals(x) && !topTagsId.contains("Blues")) {
                                topTagsId.add("Blues");
                            } else if (userProfileViews1.getClassical().equals(x) && !topTagsId.contains("Classical")) {
                                topTagsId.add("Classical");
                            } else if (userProfileViews1.getCountry().equals(x) && !topTagsId.contains("Country")) {
                                topTagsId.add("Country");
                            } else if (userProfileViews1.getJazz().equals(x) && !topTagsId.contains("Jazz")) {
                                topTagsId.add("Jazz");
                            } else if (userProfileViews1.getHiphop().equals(x) && !topTagsId.contains("Hip-hop")) {
                                topTagsId.add("Hip-hop");
                            } else if (userProfileViews1.getSoul().equals(x) && !topTagsId.contains("Soul")) {
                                topTagsId.add("Soul");
                            } else if (userProfileViews1.getRock().equals(x) && !topTagsId.contains("Rock")) {
                                topTagsId.add("Rock");
                            } else if (userProfileViews1.getPop().equals(x) && !topTagsId.contains("Pop")) {
                                topTagsId.add("Pop");
                            } else if (userProfileViews1.getRandB().equals(x) && !topTagsId.contains("R&B")) {
                                topTagsId.add("R&B");
                            } else if (userProfileViews1.getLatin().equals(x) && !topTagsId.contains("Latin")) {
                                topTagsId.add("Latin");
                            } else if (userProfileViews1.getMetal().equals(x) && !topTagsId.contains("Metal")) {
                                topTagsId.add("Metal");
                            } else if (userProfileViews1.getRap().equals(x) && !topTagsId.contains("Rap")) {
                                topTagsId.add("Rap");
                            }

                            loop++;
                            //System.out.println(topTagsId);
                            //Collections.sort(topTags);
                        }
                        else {
                            break;
                        }
                    }


                    if(topTagsId.size()>=5) {
                        tViewT1.setText(topTagsId.get(0));
                        String string1 = topTagsId.get(0).toLowerCase();//.toLowerCase();
                        tView1.setHasFixedSize(true);
                        tView1.setLayoutManager(new LinearLayoutManager(music.this, LinearLayoutManager.HORIZONTAL, false));
                        tView1Uploads = new ArrayList<>();
                        tView1Adapter = new listAdapter(music.this, tView1Uploads);
                        tView1.setAdapter(tView1Adapter);
                        tView1Adapter.setOnItemClickListener(music.this);

                        tViewT2.setText(topTagsId.get(1));
                        String string2 = topTagsId.get(1).toLowerCase();
                        tView2.setHasFixedSize(true);
                        tView2.setLayoutManager(new LinearLayoutManager(music.this, LinearLayoutManager.HORIZONTAL, false));
                        tView2Uploads = new ArrayList<>();
                        tView2Adapter = new listAdapter(music.this, tView2Uploads);
                        tView2.setAdapter(tView2Adapter);
                        tView2Adapter.setOnItemClickListener(music.this);

                        tViewT3.setText(topTagsId.get(2));
                        String string3 = topTagsId.get(2).toLowerCase();
                        tView3.setHasFixedSize(true);
                        tView3.setLayoutManager(new LinearLayoutManager(music.this, LinearLayoutManager.HORIZONTAL, false));
                        tView3Uploads = new ArrayList<>();
                        tView3Adapter = new listAdapter(music.this, tView3Uploads);
                        tView3.setAdapter(tView3Adapter);
                        tView3Adapter.setOnItemClickListener(music.this);

                        tViewT4.setText(topTagsId.get(3));
                        String string4 = topTagsId.get(3).toLowerCase();
                        tView4.setHasFixedSize(true);
                        tView4.setLayoutManager(new LinearLayoutManager(music.this, LinearLayoutManager.HORIZONTAL, false));
                        tView4Uploads = new ArrayList<>();
                        tView4Adapter = new listAdapter(music.this, tView4Uploads);
                        tView4.setAdapter(tView4Adapter);
                        tView4Adapter.setOnItemClickListener(music.this);

                        tViewT5.setText(topTagsId.get(4));
                        String string5 = topTagsId.get(4).toLowerCase();
                        tView5.setHasFixedSize(true);
                        tView5.setLayoutManager(new LinearLayoutManager(music.this, LinearLayoutManager.HORIZONTAL, false));
                        tView5Uploads = new ArrayList<>();
                        tView5Adapter = new listAdapter(music.this, tView5Uploads);
                        tView5.setAdapter(tView5Adapter);
                        tView5Adapter.setOnItemClickListener(music.this);

                        tView1Storage = FirebaseStorage.getInstance();
                        tView1DatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Music");
                        tView1DBListener = tView1DatabaseRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                tView1Uploads.clear();
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    newUpload tViewupload = postSnapshot.getValue(newUpload.class);
                                    tViewupload.setKey(postSnapshot.getKey());
                                    if (tViewupload.getFileType().equals("Music")&& tViewupload.getTags().contains(string1)) {
                                        tView1Uploads.add(tViewupload);
                                    }
                                    if (tViewupload.getFileType().equals("Music")&& tViewupload.getTags().contains(string2)) {
                                        tView2Uploads.add(tViewupload);
                                    }
                                    if (tViewupload.getFileType().equals("Music")&& tViewupload.getTags().contains(string3)) {
                                        tView3Uploads.add(tViewupload);
                                    }
                                    if (tViewupload.getFileType().equals("Music")&& tViewupload.getTags().contains(string4)) {
                                        tView4Uploads.add(tViewupload);
                                    }
                                    if (tViewupload.getFileType().equals("Music")&& tViewupload.getTags().contains(string5)) {
                                        tView5Uploads.add(tViewupload);
                                    }
                                }

                                tView1Adapter.notifyDataSetChanged();
                                tView2Adapter.notifyDataSetChanged();
                                tView3Adapter.notifyDataSetChanged();
                                tView4Adapter.notifyDataSetChanged();
                                tView5Adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(music.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public boolean checkMusicin(newUpload upload){

        for (newUpload NextUpload:muUploads){
            if (NextUpload.getAuthor().equals(upload.getAuthor())&&NextUpload.getFileUrl().equals(upload.getFileUrl())){
                return true;
            }
        }

        return false;
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(music.this,home.class);

        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClicked(int position, newUpload uploadCurrent) {
        //Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
        newUpload selectedItem = uploadCurrent;
        Intent intent;
        String vid = selectedItem.getFileUrl();

        if(selectedItem.getFileType().equals("Music")){
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
        intent.putExtra("tag", "");
        for(int i = 0; topTagsId.size()>i ; i++) {
            if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Drama");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Comedy");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Thriller");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Romance");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Action");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Horror");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Crime");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Adventure");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Mystery");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Family");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "War");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Educational");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "History");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Sci-Fi");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Musical");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Animation");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Sports");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "News");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Other");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Blues");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Classical");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Country");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Jazz");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Hip-hop");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Soul");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Rock");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Pop");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "R&B");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Latin");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Metal");
            } else if (selectedItem.getTags().contains(topTagsId.get(i))) {
                intent.putExtra("tag", "Rap");
            }
            if (!intent.getStringExtra("tag").contains("")) {
                break;
            }
        }
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
        muDatabaseRef.removeEventListener(muDBListener);
        //tView1DatabaseRef.removeEventListener(tView1DBListener);
        //tView2DatabaseRef.removeEventListener(tView2DBListener);
       // tView3DatabaseRef.removeEventListener(tView3DBListener);
        tmuDatabaseRef.removeEventListener(tmuDBListener);
        //tView4DatabaseRef.removeEventListener(tView4DBListener);
        //tView5DatabaseRef.removeEventListener(tView5DBListener);
    }
}