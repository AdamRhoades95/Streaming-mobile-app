package com.akgames.kimsstreamer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class changePayment extends AppCompatActivity {
    private Button home, cancel, change;
    private EditText cardName, cardNumber, cvc, experation, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_payment);
        home = findViewById(R.id.homepage);
        cancel = findViewById(R.id.cancel);
        change = findViewById(R.id.Change);
        cardName = findViewById(R.id.cardName);
        cardNumber = findViewById(R.id.cardNumber);
        cvc = findViewById(R.id.cvc);
        experation = findViewById(R.id.expration);
        password = findViewById(R.id.password);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);
                finish();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int check = 0;
                try {
                    if (password.getText().toString().trim().equals("")) {
                        password.setText("");
                        password.setHint("invalid password");
                        check = 1;
                        password.setBackgroundColor(Color.RED);
                    }
                }catch (Exception e) {
                    password.setText("");
                    password.setHint("invalid password");
                    check = 1;
                    password.setBackgroundColor(Color.RED);
                }
                try {
                    if (cardName.getText().toString().trim().equals("")) {
                        cardName.setText("");
                        cardName.setHint("invalid card holder name");
                        check = 1;
                        cardName.setBackgroundColor(Color.RED);
                    }
                }catch (Exception e) {
                    cardName.setText("");
                    cardName.setHint("invalid card holder name");
                    check = 1;
                    cardName.setBackgroundColor(Color.RED);
                }
                try {
                    if ((cardNumber.getText().toString().trim().length()) < 13 || cardNumber.getText().toString().trim().length() > 19) {
                        cardNumber.setText("");
                        cardNumber.setHint("invalid card number");
                        check = 1;
                        cardNumber.setBackgroundColor(Color.RED);
                    }
                }catch (Exception e) {
                    cardNumber.setText("");
                    cardNumber.setHint("invalid card number");
                    check = 1;
                    cardNumber.setBackgroundColor(Color.RED);
                }
                try {
                    if ((experation.getText().toString().trim().length()) != 5 || !experation.getText().toString().trim().contains("/")) {
                        experation.setText("");
                        experation.setHint("invalid expire date");
                        check = 1;
                        experation.setBackgroundColor(Color.RED);
                    }
                }catch (Exception e) {
                    experation.setText("");
                    experation.setHint("invalid expire date");
                    check = 1;
                    experation.setBackgroundColor(Color.RED);
                }
                try {
                    if (cvc.getText().toString().trim().length() != 3 && cvc.getText().toString().trim().length() != 4) {
                        cvc.setText("");
                        cvc.setHint("123 or 1234");
                        check = 1;
                        cvc.setBackgroundColor(Color.RED);
                    }
                }catch (Exception e) {
                    cvc.setText("");
                    cvc.setHint("123 or 1234");
                    cvc.setBackgroundColor(Color.RED);
                    check = 1;
                }

                if(check ==0) {
                    SharedPreferences sharedPreferences = getSharedPreferences("kimsStreamerProfileSaved", MODE_PRIVATE);
                    String user = sharedPreferences.getString("publicname", "");
                    //DatabaseReference reference;
                    FirebaseDatabase.getInstance().getReference("usersprofile").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                if (dataSnapshot.child("password").getValue().equals(password.getText().toString().trim())) {
                                    FirebaseDatabase.getInstance().getReference("usersprofile").child(user).child("cardHolder").setValue(cardName.getText().toString().trim());
                                    FirebaseDatabase.getInstance().getReference("usersprofile").child(user).child("cardNumber").setValue(cardNumber.getText().toString().trim());
                                    FirebaseDatabase.getInstance().getReference("usersprofile").child(user).child("cvc").setValue(cvc.getText().toString().trim());
                                    FirebaseDatabase.getInstance().getReference("usersprofile").child(user).child("experation").setValue(experation.getText().toString().trim());
                                    Intent intent = new Intent(getApplicationContext(), home.class);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    String card = cardNumber.getText().toString();
                                    editor.putString("card", card.substring(card.length()-4));
                                    editor.apply();
                                    startActivity(intent);
                                    finish();
                                } else {
                                    password.setHint("Password is Incorrect");
                                    password.setText("");
                                    password.setBackgroundColor(Color.RED);
                                }
                            } else {
                                Toast.makeText(changePayment.this, "Error please loggout and try again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }
}