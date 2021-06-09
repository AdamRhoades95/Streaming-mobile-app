package com.akgames.kimsstreamer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

public class profile extends AppCompatActivity {
    private Button home, popup, changePay;
    private TextView user,email, cardNumber;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        home = findViewById(R.id.homepage);
        popup = findViewById(R.id.popdownbar);
        changePay = findViewById(R.id.changepayment);
        user = findViewById(R.id.username);
        email = findViewById(R.id.email);
        cardNumber = findViewById(R.id.cardnumber);
        sharedPreferences = getSharedPreferences("kimsStreamerProfileSaved",MODE_PRIVATE);
        email.setText(sharedPreferences.getString("user", ""));
        user.setText("Welcome "+sharedPreferences.getString("publicname", ""));
        cardNumber.setText("Card: XXXX-XXXX-XXXX-"+sharedPreferences.getString("card",""));


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);
                finish();
            }
        });
        changePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), changePayment.class);
                startActivity(intent);
                finish();
            }
        });
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(profile.this, v);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(profile.this);

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
}