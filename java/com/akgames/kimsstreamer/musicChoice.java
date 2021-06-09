package com.akgames.kimsstreamer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class musicChoice extends AppCompatActivity {

    Button playPause, next, previous,home;
    int trackPlayPause, x;
    private MediaPlayer player;// = MediaPlayer.create(this, brokebown);
    ImageView imageView;
    TextView authorView, descriptionView, title;
    //private SimpleExoPlayer player;
    private DatabaseReference reference;
    private String type, vid, user;
    private  long views;
    private  boolean track;
    private SharedPreferences sharedPreferences;
    private DatabaseReference reference2;
    private String userstr;
    private ValueEventListener mDBListener;
    private Query query;
    private ArrayList<newUpload> list = new ArrayList<>();
    private String[] movieTags = {"drama","comedy", "thriller","romance" ,"action",
                                    "horror","crime", "adventure", "mystery", "family",
                                    "war", "educational", "history", "sci-fi", "musical",
                                    "animation", "sports", "news", "other", "blues",
                                    "classical", "country", "jazz", "hip-hop", "soul",
                                    "rock", "pop","r and b", "latin", "metal",
                                    "rap" };
    private int size, songPlaying;
    private List<newUpload> mUploads = new ArrayList<>();;
    private int song2;
    private String author;
    private String queryStr = "";
    private DatabaseReference mDatabaseRef;
    String icon2, icon1, icon;
    private int pl;
    private String tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.music_choice);
        playPause = findViewById(R.id.play_pause);
        imageView = findViewById(R.id.iconView);
        authorView = findViewById(R.id.Author);
        descriptionView = findViewById(R.id.description);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.prevous);
        title = findViewById(R.id.title);
        size = -1;
        songPlaying = -1;
        song2 = 1;
        x = 0;
        track = false;
        pl = 1;

        vid = getIntent().getStringExtra("vid");
        tags = getIntent().getStringExtra("tags");
        //queryStr = getIntent().getStringExtra("tag");

        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        type = getIntent().getStringExtra("type");
        if(tags == null){

        }
        else {
            if (tags.contains("drama")) {
                queryStr = "drama";
            } else if (tags.contains("comedy")) {
                queryStr = "comedy";
            } else if (tags.contains("thriller")) {
                queryStr = "thriller";
            } else if (tags.contains("romance")) {
                queryStr = "romance";
            } else if (tags.contains("action")) {
                queryStr = "action";
            } else if (tags.contains("horror")) {
                queryStr = "horror";
            } else if (tags.contains("crime")) {
                queryStr = "crime";
            } else if (tags.contains("adventure")) {
                queryStr = "adventure";
            } else if (tags.contains("mystery")) {
                queryStr = "mystery";
            } else if (tags.contains("family")) {
                queryStr = "family";
            } else if (tags.contains("war")) {
                queryStr = "war";
            } else if (tags.contains("eductational")) {
                queryStr = "educational";
            } else if (tags.contains("history")) {
                queryStr = "history";
            } else if (tags.contains("sci-fi")) {
                queryStr = "sci-fi";
            } else if (tags.contains("musical")) {
                queryStr = "musical";
            } else if (tags.contains("animation")) {
                queryStr = "animation";
            } else if (tags.contains("sports")) {
                queryStr = "sports";
            } else if (tags.contains("news")) {
                queryStr = "news";
            } else if (tags.contains("other")) {
                queryStr = "other";
            } else if (tags.contains("blues")) {
                queryStr = "blues";
            } else if (tags.contains("classical")) {
                queryStr = "classical";
            } else if (tags.contains("country")) {
                queryStr = "country";
            } else if (tags.contains("jazz")) {
                queryStr = "jazz";
            } else if (tags.contains("hip-hop")) {
                queryStr = "Hip-hop";
            } else if (tags.contains("soul")) {
                queryStr = "soul";
            } else if (tags.contains("rock")) {
                queryStr = "rock";
            } else if (tags.contains("pop")) {
                queryStr = "pop";
            } else if (tags.contains("r and b")) {
                queryStr = "r and b";
            } else if (tags.contains("latin")) {
                queryStr = "latin";
            } else if (tags.contains("metal")) {
                queryStr = "metal";
            } else if (tags.contains("rap")) {
                queryStr = "rap";
            }
        }
        if(queryStr.equals("")){
            queryStr = "rock";
        }
        try {
            player.setDataSource(vid);
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
//                    mp.start();
//                    mp.pause();
//                    playPause.setVisibility(View.VISIBLE);
//                    next.setVisibility(View.VISIBLE);
//                    previous.setVisibility(View.VISIBLE);
                }
            });
            player.prepareAsync();
        }
        catch (IOException e) {
            e.printStackTrace();
            player.stop();
            player.release();
            player = null;
        }

        try{
            if (type.equals("Podcast")) {
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Podcast");
            }
            else if (type.equals("Music")) {
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("Music");
            }
            mDatabaseRef.orderByChild("count");

            mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mUploads.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        newUpload upload = postSnapshot.getValue(newUpload.class);
                        upload.setKey(postSnapshot.getKey());
                        if(upload.getFileUrl().equals(vid) ){
                        }
                        else {
                            if (upload.getTags().contains(queryStr)) {
                                mUploads.add(upload);
                            }
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }catch(Exception e){

        }




        trackPlayPause = 0;
        playPause.setBackgroundResource(R.drawable.play);


        //imageView.setImageURI(Uri.parse(icon));
        author = getIntent().getStringExtra("author");
        authorView.setText("Author: " + author);
        String description = getIntent().getStringExtra("description");
        descriptionView.setText("Description: " + description);
        icon = getIntent().getStringExtra("icon");
        Glide.with(musicChoice.this).load(icon).into(imageView);
        String strTitle = getIntent().getStringExtra("title");
        title.setText("Title: " + strTitle);
        views = Long.parseLong(getIntent().getStringExtra("views"));

        sharedPreferences = getSharedPreferences("kimsStreamerProfileSaved",MODE_PRIVATE);
        user = "_"+sharedPreferences.getString("publicname", "")+"_";
        userstr = sharedPreferences.getString("publicname", "");

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!track) {
                            try {

                                if (type.toLowerCase().equals("movie")) {
                                    reference = FirebaseDatabase.getInstance().getReference().child("uploads").child("Movie");
                                    reference2 = FirebaseDatabase.getInstance().getReference().child("userProfileViews").child(userstr).child("Movies");

                                }
                                else if (type.toLowerCase().equals("music")) {
                                    reference = FirebaseDatabase.getInstance().getReference().child("uploads").child("Music");
                                    reference2 = FirebaseDatabase.getInstance().getReference().child("userProfileViews").child(userstr).child("Music");
                                }
                                else if (type.toLowerCase().equals("podcast")) {
                                    reference = FirebaseDatabase.getInstance().getReference().child("uploads").child("Podcast");
                                    reference2 = FirebaseDatabase.getInstance().getReference().child("userProfileViews").child(userstr).child("Podcast");
                                }

                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                     public void onDataChange(DataSnapshot dataSnapshot) {

                                       for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            newUpload upload = postSnapshot.getValue(newUpload.class);
                                            upload.setKey(postSnapshot.getKey());
                                            if (upload.getFileUrl().equals(vid) && upload.getAuthor().equals(author) && upload.getName().equals(strTitle)) {
                                                views = Long.parseLong((postSnapshot.child("count").getValue()).toString()) - 1;
                                                //String key = dataSnapshot.getKey();

                                                if(!upload.getViewers().contains(user)) {
                                                    user += (String) upload.getViewers();
                                                    reference.child(postSnapshot.getKey()).child("viewers").setValue(String.valueOf(user));
                                                }

                                                try {

                                                        reference.child(postSnapshot.getKey()).child("count").setValue(String.valueOf(views));
                                                        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot snapshot) {
                                                                Long value = Long.valueOf(0);
                                                                if (snapshot.exists()) {
                                                                    userProfileViews userProfileViews1 = snapshot.getValue(userProfileViews.class);
                                                                    if (upload.getTags().contains(movieTags[0])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getDrama()) - 1;
                                                                        reference2.child("drama").setValue(value.toString());
                                                                    }
                                                                    if (upload.getTags().contains(movieTags[1])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getComedy()) - 1;
                                                                        reference2.child("comedy").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[2])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getThriller()) - 1;
                                                                        reference2.child("thriller").setValue(value.toString());
                                                                    }
                                                                    if (upload.getTags().contains(movieTags[3])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getRomance()) - 1;
                                                                        reference2.child("romance").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[4])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getAction()) - 1;
                                                                        reference2.child("action").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[5])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getHorror()) - 1;
                                                                        reference2.child("horror").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[6])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getCrime()) - 1;
                                                                        reference2.child("crime").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[7])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getAdventure()) - 1;
                                                                        reference2.child("adventure").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[8])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getMystery()) - 1;
                                                                        reference2.child("mystery").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[9])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getFamily()) - 1;
                                                                        reference2.child("family").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[10])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getWar()) - 1;
                                                                        reference2.child("war").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[11])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getEducational()) - 1;
                                                                        reference2.child("educational").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[12])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getHistory()) - 1;
                                                                        reference2.child("history").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[13])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getSciFi()) - 1;
                                                                        reference2.child("sciFi").setValue(value.toString());
                                                                    }


                                                                    if (upload.getTags().contains(movieTags[14])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getMusical()) - 1;
                                                                        reference2.child("musical").setValue(value.toString());
                                                                    }


                                                                    if (upload.getTags().contains(movieTags[15])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getAnimation()) - 1;
                                                                        reference2.child("animation").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[16])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getSports()) - 1;
                                                                        reference2.child("sports").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[17])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getNews()) - 1;
                                                                        reference2.child("news").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[18])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getOther()) - 1;
                                                                        reference2.child("other").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[19])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getBlues()) - 1;
                                                                        reference2.child("blues").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[20])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getClassical()) - 1;
                                                                        reference2.child("classical").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[21])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getCountry()) - 1;
                                                                        reference2.child("country").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[22])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getJazz()) - 1;
                                                                        reference2.child("jazz").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[23])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getHiphop()) - 1;
                                                                        reference2.child("hiphop").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[24])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getSoul()) - 1;
                                                                        reference2.child("soul").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[25])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getRock()) - 1;
                                                                        reference2.child("rock").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[26])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getPop()) - 1;
                                                                        reference2.child("pop").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[27])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getRandB()) - 1;
                                                                        reference2.child("randB").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[28])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getLatin()) - 1;
                                                                        reference2.child("latin").setValue(value.toString());
                                                                    }

                                                                    if (upload.getTags().contains(movieTags[29])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getMetal()) - 1;
                                                                        reference2.child("metal").setValue(value.toString());
                                                                    }


                                                                    if (upload.getTags().contains(movieTags[30])) {
                                                                        value = Long.parseLong((String) userProfileViews1.getRap()) - 1;
                                                                        reference2.child("rap").setValue(value.toString());
                                                                    }

                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });




                                                }catch (Exception e){

                                                }

                                                break;
                                            }
                                        }
                                        //upload.setKey(dataSnapshot.getKey());
                                        //dataSnapshot.child("password").equals();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
//                    //reference.child(user.getUserPassword()).setValue(user);
//
                                });

                            }
                            catch (Exception e) {

                            }




//                    Intent intent = new Intent(getApplicationContext(), videoplay.class);
//                    intent.putExtra("author", author);
//                    intent.putExtra("description", description);
//                    intent.putExtra("icon", icon);
//                    intent.putExtra("title", strTitle);
//                    intent.putExtra("views", String.valueOf(views));
//                    intent.putExtra("type", type);
//                    intent.putExtra("track", true);
//                    intent.putExtra("vid", vid);
//                    startActivity(intent);
//                    finish();

                }


                if(trackPlayPause==0){

                    if(pl == 1){
                        player.start();
                    }
                    playPause.setBackgroundResource(R.drawable.pause);
                    trackPlayPause = 1;
                    //player.setPlayWhenReady(true);
                }
                else if(trackPlayPause == 1){
                    playPause.setBackgroundResource(R.drawable.play);
                    if(player.isPlaying()){
                        pl = 1;
                        player.pause();
                    }
                    trackPlayPause = 0;
                    //player.setPlayWhenReady(false);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(size);
                System.out.println(mUploads.toString());
                if(mUploads != null) {
                    player.release();
                    size += 1;
                    if (size >= mUploads.size()) {

                    }
                    else {
                        try {
                            player = new MediaPlayer();
                            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            authorView.setText(mUploads.get(size).getAuthor());
                            descriptionView.setText(mUploads.get(size).getDescript());
                            title.setText(mUploads.get(size).getName());
                            player.setDataSource(mUploads.get(size).getFileUrl());
                            icon1 = mUploads.get(size).getImageUrl();
                            Glide.with(musicChoice.this).load(icon1).into(imageView);
                            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.start();
                                }
                            });
                            player.prepareAsync();
                            player.start();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                            player.stop();
                            player.release();
                            player = null;
                        }
                    }
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (player.isPlaying()) {
                        if (player.getCurrentPosition() > 3000) {
                            player.pause();
                            player.seekTo(0);
                            player.start();
                        }

                        else {
                            if(mUploads != null) {
                                player.release();
                                size -= 1;
                                if (size < 0) {
                                    try {
                                        player = new MediaPlayer();
                                        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                        player.setDataSource(vid);
                                        authorView.setText(author);
                                        descriptionView.setText(description);
                                        title.setText(strTitle);
                                        Glide.with(musicChoice.this).load(icon).into(imageView);
                                        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mp) {
                                                mp.start();
                                            }
                                        });
                                        player.prepareAsync();
                                        player.start();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        player.stop();
                                        player.release();
                                        player = null;
                                    }
                                }
                                else if (size >= mUploads.size()) {
                                }
                                else {
                                    try {
                                        player = new MediaPlayer();
                                        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                        authorView.setText(mUploads.get(size).getAuthor());
                                        descriptionView.setText(mUploads.get(size).getDescript());
                                        title.setText(mUploads.get(size).getName());
                                        player.setDataSource(mUploads.get(size).getFileUrl());
                                        icon1 = mUploads.get(size).getImageUrl();
                                        Glide.with(musicChoice.this).load(icon1).into(imageView);
                                        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mp) {
                                                mp.start();
                                            }
                                        });
                                        player.prepareAsync();
                                        player.start();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        player.stop();
                                        player.release();
                                        player = null;
                                    }
                                }
                            }
                        }
                    }
            }
        });
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    player.stop();
                }catch (Exception e){

                }
                try {
                    player.release();
                }catch (Exception e){

                }

                Intent intent = new Intent(getApplicationContext(),home.class);
                startActivity(intent);
                finish();
                //player.release();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mUploads != null) {
                    player.release();
                    size += 1;
                    if (size >= mUploads.size()) {
                    }
                    else {
                        try {
                            player = new MediaPlayer();
                            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            authorView.setText(mUploads.get(size).getAuthor());
                            descriptionView.setText(mUploads.get(size).getDescript());
                            title.setText(mUploads.get(size).getName());
                            player.setDataSource(mUploads.get(size).getFileUrl());
                            icon1 = mUploads.get(size).getImageUrl();
                            Glide.with(musicChoice.this).load(icon1).into(imageView);
                            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.start();
                                }
                            });
                            player.prepareAsync();
                            player.start();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                            player.stop();
                            player.release();
                            player = null;
                        }
                    }
                }
            }

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            player.stop();
        }catch (Exception e){

        }
        try {
            player.release();
        }catch (Exception e){

        }
    }


}