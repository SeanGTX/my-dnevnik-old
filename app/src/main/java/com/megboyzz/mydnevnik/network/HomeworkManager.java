package com.megboyzz.mydnevnik.network;

import android.os.AsyncTask;

import java.util.Map;

public class HomeworkManager extends AsyncTask<Void, Void, Void> {

    private final String HomeworksURL = "https://schools.dnevnik.ru/homework.aspx";

    public Map<String, String> getLoginCookies() {
        return loginCookies;
    }

    public void setLoginCookies(Map<String, String> loginCookies) {
        this.loginCookies = loginCookies;
    }

    private Map<String, String> loginCookies;

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}
