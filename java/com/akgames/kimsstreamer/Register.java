package com.akgames.kimsstreamer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    private Button cancel, create;
    private EditText day,month,year, email,username,password, cardHolder,cardNumber, experation, cvc;
    private DatabaseReference reference;
    private DatabaseReference reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        cancel = findViewById(R.id.cancel);
        day = findViewById(R.id.day);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        cardHolder = findViewById(R.id.cardName);
        cardNumber = findViewById(R.id.cardNumber);
        experation = findViewById(R.id.expration);
        cvc = findViewById(R.id.cvc);
        create = findViewById(R.id.create);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),loginScreen.class);
                startActivity(intent);
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = 0;
                try {
                    if (Integer.parseInt(day.getText().toString()) > 31 || Integer.parseInt(day.getText().toString()) < 1) {
                        day.setText("");
                        day.setHint("1-31");
                        check = 1;
                    }
                }catch (Exception e){
                    day.setText("");
                    day.setHint("1-31");
                    check = 1;
                }
                try {
                    if (Integer.parseInt(month.getText().toString()) > 12 || Integer.parseInt(month.getText().toString()) < 1) {
                        month.setText("");
                        month.setHint("1-12");
                        check = 1;
                    }
                }catch (Exception e){
                    month.setText("");
                    month.setHint("1-12");
                    check = 1;
                }
                try {
                    if (Integer.parseInt(year.getText().toString()) > (Calendar.getInstance().get(Calendar.YEAR)) || Integer.parseInt(year.getText().toString()) < 1) {
                        year.setText("");
                        year.setHint("2021");
                        check = 1;
                    }
                }catch (Exception e) {
                    year.setText("");
                    year.setHint("2021");
                    check = 1;
                }
                try {
                    if (!email.getText().toString().trim().contains("@")) {
                        email.setText("");
                        email.setHint("invalid email");
                        check = 1;
                    }
                }catch (Exception e) {
                    email.setText("");
                    email.setHint("invalid email");
                    check = 1;
                }
                try {
                    if (username.getText().toString().trim().equals("")) {
                        username.setText("");
                        username.setHint("invalid username");
                        check = 1;
                    }
                    else if (username.getText().toString().trim().contains(".")) {
                        username.setText("");
                        username.setHint("username cannot contain a period(.)");
                        username.setTextSize(14);
                        check = 1;
                    }
                }catch (Exception e) {
                    username.setText("");
                    username.setHint("invalid username");
                    check = 1;
                }
                try {
                    if (password.getText().toString().trim().equals("")) {
                        password.setText("");
                        password.setHint("invalid password");
                        check = 1;
                    }
                }catch (Exception e) {
                    password.setText("");
                    password.setHint("invalid password");
                    check = 1;
                }
                try {
                    if (cardHolder.getText().toString().trim().equals("")) {
                        cardHolder.setText("");
                        cardHolder.setHint("invalid card holder name");
                        check = 1;
                    }
                }catch (Exception e) {
                    cardHolder.setText("");
                    cardHolder.setHint("invalid card holder name");
                    check = 1;
                }
                try {
                    if ((cardNumber.getText().toString().trim().length()) < 13 || cardNumber.getText().toString().trim().length() > 19) {
                        cardNumber.setText("");
                        cardNumber.setHint("invalid card number");
                        check = 1;
                    }
                }catch (Exception e) {
                    cardNumber.setText("");
                    cardNumber.setHint("invalid card number");
                    check = 1;
                }
                try {
                    if ((experation.getText().toString().trim().length()) != 5 || !experation.getText().toString().trim().contains("/")) {
                        experation.setText("");
                        experation.setHint("invalid expire date");
                        check = 1;
                    }
                }catch (Exception e) {
                    experation.setText("");
                    experation.setHint("invalid expire date");
                    check = 1;
                }
                try {
                    if (cvc.getText().toString().trim().length() != 3 && cvc.getText().toString().trim().length() != 4) {
                        cvc.setText("");
                        cvc.setHint("123 or 1234");
                        check = 1;
                    }
                }catch (Exception e) {
                    cvc.setText("");
                    cvc.setHint("123 or 1234");
                    check = 1;
                }
                if (check == 0) {
                    reference = FirebaseDatabase.getInstance().getReference().child("usersprofile");
                    //reference = rootNode.getReference().child("usersprofile");
                    String mail = username.getText().toString().trim();
                    reference.child(mail).addListenerForSingleValueEvent(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                username.setText("");
                                username.setHint("User Taken");
                                //dataSnapshot.child("password").equals();
                            } else {
                                try {
                                    reference = FirebaseDatabase.getInstance().getReference().child("usersprofile");
                                    userProfile userProfile = new userProfile(day.getText().toString().trim(), month.getText().toString().trim(),
                                            year.getText().toString().trim(), email.getText().toString().trim(), username.getText().toString().trim(),
                                            password.getText().toString().trim(), cardHolder.getText().toString().trim(), cardNumber.getText().toString().trim(),
                                            experation.getText().toString().trim(), cvc.getText().toString().trim());
                                    reference.push();
                                    reference.child(username.getText().toString().trim()).setValue(userProfile);
                                    reference2 = FirebaseDatabase.getInstance().getReference().child("userProfileViews").child(userProfile.getUsername());
                                    reference2.push();
                                    userProfileViews userProfileViews = new userProfileViews();
                                    reference2.child("Movies").setValue(userProfileViews);
                                    reference2.child("Podcast").setValue(userProfileViews);
                                    reference2.child("Music").setValue(userProfileViews);

                                }
                                catch(Exception e){
//
                                }
//
                                Intent intent = new Intent(getApplicationContext(), loginScreen.class);
                                //intent.putExtra("username", username.getText().toString().trim());
//
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

//
//
//                    //reference.child(user.getUserPassword()).setValue(user);
//
                    });
                }
            }
        });
    }
}