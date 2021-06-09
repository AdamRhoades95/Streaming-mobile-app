package com.akgames.kimsstreamer;

import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.RequiresApi;

public class userProfile {
    private String day,month,year, email,username,password, cardHolder,cardNumber, experation, cvc, startD, currentVD;

    public userProfile() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public userProfile(String day, String month, String year, String email, String username,
                       String password, String cardHolder, String cardNumber, String experation, String cvc) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.email = email;
        this.username = username;
        this.password = password;
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.experation = experation;
        this.cvc = cvc;


        //Specifying date format that matches the given date
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();
        startD = sdf.format(c.getTime());

        try{
            //Setting the date to the given date
            c.setTime(sdf.parse(startD));
        }catch(ParseException e){
            e.printStackTrace();
        }

        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, 14);
        //Date after adding the days to the given date
        currentVD = sdf.format(c.getTime());
        System.out.println(startD);
        System.out.println(currentVD);
    }

    public String getStartD() {
        return startD;
    }

    public void setStartD(String startD) {
        this.startD = startD;
    }

    public String getCurrentVD() {
        return currentVD;
    }

    public void setCurrentVD(String currentVD) {
        this.currentVD = currentVD;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExperation() {
        return experation;
    }

    public void setExperation(String experation) {
        this.experation = experation;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
}
