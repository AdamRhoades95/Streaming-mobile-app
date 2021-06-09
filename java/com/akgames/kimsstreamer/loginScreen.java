package com.akgames.kimsstreamer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginScreen extends AppCompatActivity {
    private Button login, register;
    private EditText username, password;
    private CheckBox remember;
    private String user, pass;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        login = findViewById(R.id.login);

        register = findViewById(R.id.register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);


        remember = findViewById(R.id.remember);
        sharedPreferences = getSharedPreferences("kimsStreamerProfileSaved",MODE_PRIVATE);
        user = "";
        pass = "";
        //=================Button Events===================
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString().trim();
                pass = password.getText().toString();
                if(user.equals("")){
                    username.setHint("cannot be blank!");
                }
                else if (user.contains(".")){
                    username.setText("");
                    username.setHint("invalid username");
                }
                if(pass.equals("")){
                    password.setHint("cannot be blank!");
                }
                else{
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("usersprofile");

                    reference.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                if(dataSnapshot.child("password").getValue().equals(pass)){
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    if(remember.isChecked()) {
                                        editor.putString("user", dataSnapshot.child("email").getValue().toString());
                                        editor.putString("publicname", user);
                                        editor.putString("pass", pass);
                                        editor.putString("check", "t");
                                        String card = dataSnapshot.child("cardNumber").getValue().toString();
                                        editor.putString("card", card.substring(card.length()-4));
                                        editor.apply();
                                    }
                                    else{
                                        editor.putString("user", dataSnapshot.child("email").getValue().toString());
                                        editor.putString("publicname", user);
                                        editor.putString("pass", "");
                                        editor.putString("check", "f");
                                        String card = dataSnapshot.child("cardNumber").getValue().toString();
                                        editor.putString("card", card.substring(card.length()-4));

                                        editor.apply();

                                    }

                                    Intent intent = new Intent(getApplicationContext(),home.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    username.setText("");
                                    username.setHint("Username might be Wrong");
                                    password.setText("");
                                    password.setHint("Password might be Wrong");
                                }
                                //dataSnapshot.child("password").equals();
                            }

                            else{
                                password.setText("");
                                password.setHint("Password might be Wrong");
                                username.setText("");
                                username.setHint("Username might be Wrong");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
                finish();
            }
        });
    }
}