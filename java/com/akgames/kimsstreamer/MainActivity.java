package com.akgames.kimsstreamer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String user;
    private String pass;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("kimsStreamerProfileSaved",MODE_PRIVATE);
        String check = sharedPreferences.getString("check","").toString();
        //String isLoggedOut = sharedPreferences.getString("logout","").toString();
        if(check.equals("t")) {
            user = sharedPreferences.getString("publicname", "");
            pass = sharedPreferences.getString("pass", "");

            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("usersprofile");
            reference.child(user).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        if (dataSnapshot.child("password").getValue().equals(pass)) {
                            Intent intent = new Intent(getApplicationContext(), home.class);
                            //intent.putExtra("username", user);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });

        }

        else{
            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, loginScreen.class));
                    finish();
                }
            };
            Timer opening = new Timer();
            opening.schedule(task, 3000);
        }
    }
}