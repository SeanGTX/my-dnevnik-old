package com.megboyzz.mydnevnik.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SessionDataManager {

    private Context context;

    public SessionDataManager(Context context){
        this.context = context;
    }

    public void saveCookies(Map<String, String> cookies){
        SharedPreferences CookiesPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor CookiesPreferencesEditor = CookiesPreferences.edit();
        if(cookies.get("t0") != null) CookiesPreferencesEditor.putString("t0", cookies.get("t0"));
        if(cookies.get("t1") != null) CookiesPreferencesEditor.putString("t1", cookies.get("t1"));
        if(cookies.get("t2") != null) CookiesPreferencesEditor.putString("t2", cookies.get("t2"));
        if(cookies.get("dnevnik_sst") != null) CookiesPreferencesEditor.putString("dnevnik_sst", cookies.get("dnevnik_sst"));
        if(cookies.get("DnevnikAuth_l") != null) CookiesPreferencesEditor.putString("DnevnikAuth_l", cookies.get("DnevnikAuth_l"));
        if(cookies.get("Education_im_userlastActivity") != null) CookiesPreferencesEditor.putString("Education_im_userlastActivity", cookies.get("Education_im_userlastActivity"));
        if(cookies.get("a_r_p_i") != null) CookiesPreferencesEditor.putString("a_r_p_i", cookies.get("a_r_p_i"));
        if(cookies.get("DnevnikAuth_a") != null) CookiesPreferencesEditor.putString("DnevnikAuth_a", cookies.get("DnevnikAuth_a"));
        CookiesPreferencesEditor.commit();
    }

    public void saveGeneralInfo(String surname, String name, String middleName, String Class, String school, String year){
        SharedPreferences CookiesPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int yearCode = Integer.parseInt(Class.split("-")[0]);
        SharedPreferences.Editor CookiesPreferencesEditor = CookiesPreferences.edit();
        CookiesPreferencesEditor.putString("surname", surname);
        CookiesPreferencesEditor.putString("name", name);
        CookiesPreferencesEditor.putString("middleName", middleName);
        CookiesPreferencesEditor.putString("class", Class);
        CookiesPreferencesEditor.putInt("yearCode", yearCode);
        CookiesPreferencesEditor.putString("school", school);
        CookiesPreferencesEditor.putString("year", year);
        CookiesPreferencesEditor.commit();
    }

    public String getUserName(){
        SharedPreferences CookiesPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return CookiesPreferences.getString("name", "null");
    }

    public String getUserSurname(){
        SharedPreferences CookiesPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return CookiesPreferences.getString("surname", "null");
    }

    public String getUserMiddleName(){
        SharedPreferences CookiesPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return CookiesPreferences.getString("middleName", "null");
    }

    public String getUserClass(){
        SharedPreferences CookiesPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return CookiesPreferences.getString("class", "null");
    }

    public String getUserSchool(){
        SharedPreferences CookiesPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return CookiesPreferences.getString("school", "null");
    }

    public String getSchoolYear(){
        SharedPreferences CookiesPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return CookiesPreferences.getString("year", "null");
    }

    public void deleteCookies() {
        SharedPreferences CookiesPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor CookiesPreferencesEditor = CookiesPreferences.edit();
        CookiesPreferencesEditor.putString("t0", "");
        CookiesPreferencesEditor.putString("t1", "");
        CookiesPreferencesEditor.putString("t2", "");
        CookiesPreferencesEditor.putString("dnevnik_sst", "");
        CookiesPreferencesEditor.putString("DnevnikAuth_l", "");
        CookiesPreferencesEditor.putString("Education_im_userlastActivity", "");
        CookiesPreferencesEditor.putString("a_r_p_i", "");
        CookiesPreferencesEditor.putString("DnevnikAuth_a", "");
        CookiesPreferencesEditor.commit();
    }

    public Map<String, String> getCookiesFromPreferences(){
        SharedPreferences CookiesPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String, String> cookies = new HashMap<>();
        cookies.put("t0", CookiesPreferences.getString("t0", ""));
        cookies.put("t1", CookiesPreferences.getString("t1", ""));
        cookies.put("t2", CookiesPreferences.getString("t2", ""));
        cookies.put("DnevnikAuth_l", CookiesPreferences.getString("DnevnikAuth_l", ""));
        cookies.put("Education_im_userlastActivity", CookiesPreferences.getString("Education_im_userlastActivity", ""));
        cookies.put("a_r_p_i", CookiesPreferences.getString("a_r_p_i", ""));
        cookies.put("DnevnikAuth_a", CookiesPreferences.getString("DnevnikAuth_a", ""));
        cookies.put("dnevnik_sst", CookiesPreferences.getString("dnevnik_sst", ""));
        return cookies;
    }

    public String getCookie(String cookieName){
        SharedPreferences CookiesPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return CookiesPreferences.getString(cookieName, "");
    }

    public File createHTML(String fileName){

        File InnerMem = context.getFilesDir();

        File htmlFile = new File(InnerMem, fileName + ".html");

        if (!htmlFile.exists())
            try {
                htmlFile.createNewFile();
            } catch (IOException e) {
                Log.i(fileName, e.toString());
            }

        Log.i(fileName, " - created!!");

        return htmlFile;
    }

    public void deleteHTMLs(){
        File InnerMem = context.getFilesDir();

        File CallSchedulesFile = new File(InnerMem,  "CallSchedulesFile.html");
        if(CallSchedulesFile.exists()) CallSchedulesFile.delete();

        File PrimaryHomeWorksFile = new File(InnerMem, "PrimaryHomeWorksFile.html");
        if(PrimaryHomeWorksFile.exists()) PrimaryHomeWorksFile.delete();

        File PrimaryMarksFile = new File(InnerMem, "PrimaryMarksFile.html");
        if(PrimaryMarksFile.exists()) PrimaryMarksFile.delete();

        File PrimarySchedulesFile = new File(InnerMem, "PrimarySchedulesFile.html");
        if(PrimarySchedulesFile.exists()) PrimarySchedulesFile.delete();
    }

    public boolean RelevantCookiesIsExists(){

        SharedPreferences CookiesPreferences = PreferenceManager.getDefaultSharedPreferences(context);


        if(
                CookiesPreferences.getString("t0", "").isEmpty() &&
                        CookiesPreferences.getString("t1", "").isEmpty() &&
                        CookiesPreferences.contains("t2") &&
                        CookiesPreferences.getString("DnevnikAuth_l", "").isEmpty() &&
                        CookiesPreferences.contains("Education_im_userlastActivity") &&
                        CookiesPreferences.getString("a_r_p_i", "").isEmpty() &&
                        CookiesPreferences.getString("DnevnikAuth_a", "").isEmpty()
        ) return false;

        String DnevnikSST = CookiesPreferences.getString("dnevnik_sst", "");

        if(!DnevnikSST.isEmpty()){

            Date nowDate = new Date();
            int len = DnevnikSST.length();
            String s = DnevnikSST.substring(37, len);
            Date DnevnikSSTdate = new Date();

            try {
                DnevnikSSTdate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long milliseconds = DnevnikSSTdate.getTime() - nowDate.getTime();

            return milliseconds / (60 * 60 * 1000) >= 1;

        }else return false;
    }

    public void saveLoginData(String login, String password){
        SharedPreferences CookiesPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor CookiesPreferencesEditor = CookiesPreferences.edit();
        CookiesPreferencesEditor.putString("login", login);
        CookiesPreferencesEditor.putString("password", password);
        CookiesPreferencesEditor.commit();
    }

    public boolean IsLoginDataRight(String login, String password){
        return false;
    }

    public void DeleteCookies(){

    }
}
