package com.akgames.kimsstreamer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.google.firebase.database.ValueEventListener;

public class movieChoice extends AppCompatActivity {
    private Button playPause, home;
    int trackPlayPause;
    private MediaPlayer player;
    private TextView authorView, descriptionView, title;
    private ImageView imageView;
    private long views;
    private DatabaseReference reference;
    private boolean track;

    private String author, description, icon, strTitle, type, user, tags;
    private DatabaseReference reference2;
    private SharedPreferences sharedPreferences;
    private String userstr;
    private String[] movieTags = {"Drama","Comedy", "Thriller","Romance" ,"Action",
            "Horror","Crime", "Adventure", "Mystery", "Family",
            "War", "Educational", "History", "SciFi", "Musical",
            "Animation", "Sports", "News", "Other", "Blues",
            "Classical", "Country", "Jazz", "Hiphop", "Soul",
            "Rock", "Pop","R and B", "Latin", "Metal",
            "Rap" };
    private int x;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        track = false;
        x = 0;
        setContentView(R.layout.activity_movie_choice);
        imageView = findViewById(R.id.iconView);
        playPause = findViewById(R.id.play_pause);
        player = MediaPlayer.create(this, R.raw.test);
        String vid = getIntent().getStringExtra("vid");
        playPause.setBackgroundResource(R.drawable.play);
        authorView = findViewById(R.id.author);
        title = findViewById(R.id.title);
        descriptionView = findViewById(R.id.description);

        sharedPreferences = getSharedPreferences("kimsStreamerProfileSaved",MODE_PRIVATE);
        user = "_"+sharedPreferences.getString("publicname", "")+"_";
        userstr = sharedPreferences.getString("publicname", "");

            author = getIntent().getStringExtra("author");
            authorView.setText("Author: "+author);
            description = getIntent().getStringExtra("description");
            descriptionView.setText("Description: "+description);
            icon = getIntent().getStringExtra("icon");
            strTitle = getIntent().getStringExtra("title");
            type = getIntent().getStringExtra("type");
            views = Long.parseLong(getIntent().getStringExtra("views"));
            title.setText("Title: "+ strTitle);
            Glide.with(movieChoice.this ).load(icon).into(imageView);
            tags = getIntent().getStringExtra("tags");


        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        //imageView.setImageURI(Uri.parse(icon));


                if(!track) {

//                    final AsyncTask<Void,Void,Void> task = new AsyncTask<Void,Void,Void>(){
//                        protected Void doInBackground(Void... voids) {


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
                                                    if (snapshot.exists()) {
                                                        userProfileViews userProfileViews1 = snapshot.getValue(userProfileViews.class);
                                                        if (upload.getTags().contains(movieTags[0])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getDrama()) - 1;
                                                            reference2.child("drama").setValue(value.toString());
                                                        }
                                                        if (upload.getTags().contains(movieTags[1])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getComedy()) - 1;
                                                            reference2.child("comedy").setValue(value.toString());
                                                        }


                                                        if (upload.getTags().contains(movieTags[2])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getThriller()) - 1;
                                                            reference2.child("thriller").setValue(value.toString());
                                                        }
                                                        if (upload.getTags().contains(movieTags[3])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getRomance()) - 1;
                                                            reference2.child("romance").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[4])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getAction()) - 1;
                                                            reference2.child("action").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[5])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getHorror()) - 1;
                                                            reference2.child("horror").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[6])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getCrime()) - 1;
                                                            reference2.child("crime").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[7])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getAdventure()) - 1;
                                                            reference2.child("adventure").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[8])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getMystery()) - 1;
                                                            reference2.child("mystery").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[9])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getFamily()) - 1;
                                                            reference2.child("family").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[10])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getWar()) - 1;
                                                            reference2.child("war").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[11])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getEducational()) - 1;
                                                            reference2.child("educational").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[12])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getHistory()) - 1;
                                                            reference2.child("history").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[13])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getSciFi()) - 1;
                                                            reference2.child("sciFi").setValue(value.toString());
                                                        }


                                                        if (upload.getTags().contains(movieTags[14])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getMusical()) - 1;
                                                            reference2.child("musical").setValue(value.toString());
                                                        }


                                                        if (upload.getTags().contains(movieTags[15])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getAnimation()) - 1;
                                                            reference2.child("animation").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[16])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getSports()) - 1;
                                                            reference2.child("sports").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[17])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getNews()) - 1;
                                                            reference2.child("news").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[18])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getOther()) - 1;
                                                            reference2.child("other").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[19])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getBlues()) - 1;
                                                            reference2.child("blues").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[20])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getClassical()) - 1;
                                                            reference2.child("classical").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[21])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getCountry()) - 1;
                                                            reference2.child("country").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[22])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getJazz()) - 1;
                                                            reference2.child("jazz").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[23])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getHiphop()) - 1;
                                                            reference2.child("hiphop").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[24])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getSoul()) - 1;
                                                            reference2.child("soul").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[25])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getRock()) - 1;
                                                            reference2.child("rock").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[26])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getPop()) - 1;
                                                            reference2.child("pop").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[27])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getRandB()) - 1;
                                                            reference2.child("randB").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[28])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getLatin()) - 1;
                                                            reference2.child("latin").setValue(value.toString());
                                                        }

                                                        if (upload.getTags().contains(movieTags[29])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getMetal()) - 1;
                                                            reference2.child("metal").setValue(value.toString());
                                                        }


                                                        if (upload.getTags().contains(movieTags[30])) {
                                                            Long value = Long.parseLong((String) userProfileViews1.getRap()) - 1;
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

//                            return null;
//                        }
//
//                        @Override
//                        protected void onPostExecute(Void aVoid) {
//                            super.onPostExecute(aVoid);
//                        }
//
//                    };
//
//                            task.execute();
                    Intent intent = new Intent(getApplicationContext(), videoplay.class);
                    intent.putExtra("author", author);
                    intent.putExtra("description", description);
                    intent.putExtra("icon", icon);
                    intent.putExtra("title", strTitle);
                    intent.putExtra("views", String.valueOf(views));
                    intent.putExtra("type", type);
                    intent.putExtra("track", true);
                    intent.putExtra("vid", vid);
                    startActivity(intent);
                    finish();

                }


            }
        });
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),home.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(movieChoice.this,movies.class);
        startActivity(intent);
        finish();
    }
}