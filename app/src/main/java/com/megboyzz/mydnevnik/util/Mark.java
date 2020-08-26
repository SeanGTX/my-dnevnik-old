package com.megboyzz.mydnevnik.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mark {

    public String getValue() {
        return value;
    }

    private String value;
    private Date date;
    private int numOfLesson;

    public Mark(String value, Date date, int numOfLesson) {
        this.value = value;
        this.date = date;
        this.numOfLesson = numOfLesson;
    }

    public Mark(int value, Date date, int numOfLesson) {
        this.value =""+value;
        this.date = date;
        this.numOfLesson = numOfLesson;
    }

    public Mark(String value, String date){
        this.value = value;
        String[] strDate = date.split(" ");
        numOfLesson = Integer.parseInt(strDate[6]);
        String day = strDate[3];
        String month = "01";
        switch (strDate[4]){
            case "января": month = "01"; break;
            case "февраля": month = "02"; break;
            case "марта": month = "03"; break;
            case "апреля": month = "04"; break;
            case "мая": month = "05"; break;
            case "июня": month = "06"; break;
            case "июля": month = "07"; break;
            case "августа": month = "08"; break;
            case "сентября": month = "09"; break;
            case "октября": month = "10"; break;
            case "ноября": month = "11"; break;
            case "декабря": month = "12"; break;
        }
        String year = strDate[5].split(",")[0];
        try {
            this.date = new SimpleDateFormat("dd MM yyyy").parse(day + " " + month + " " + year);
        } catch (ParseException e) {
            Log.e("MarkParseError", e.toString());
        }
    }

    public Mark(int value, String date){
        this.value = ""+value;
        String[] strDate = date.split(" ");
        numOfLesson = Integer.parseInt(strDate[6]);
        String day = strDate[3];
        String month = "01";
        switch (strDate[4]){
            case "января": month = "01"; break;
            case "февраля": month = "02"; break;
            case "марта": month = "03"; break;
            case "апреля": month = "04"; break;
            case "мая": month = "05"; break;
            case "июня": month = "06"; break;
            case "июля": month = "07"; break;
            case "августа": month = "08"; break;
            case "сентября": month = "09"; break;
            case "октября": month = "10"; break;
            case "ноября": month = "11"; break;
            case "декабря": month = "12"; break;
        }
        String year = strDate[5].split(",")[0];
        try {
            this.date = new SimpleDateFormat("dd MM yyyy").parse(day + " " + month + " " + year);
        } catch (ParseException e) {
            Log.e("MarkParseError", e.toString());
        }

    }


}
