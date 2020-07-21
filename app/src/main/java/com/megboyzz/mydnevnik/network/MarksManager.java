package com.megboyzz.mydnevnik.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.Formatter;
import java.util.Map;

public class MarksManager extends AsyncTask<View, Void, View> {

    private String DnevnikMarksURL = "https://schools.dnevnik.ru/marks.aspx?school=%s&index=%s&tab=period&%shomebasededucation=False";
    private Map<String, String> loginCookies;
    private Connection.Response MarksResponse;
    private Document MarksPage;
    private Context context;
    private ViewModel viewModel;

    public MarksManager(Map<String, String> loginCookies, Context context) {
        this.loginCookies = loginCookies;
        this.context = context;
    }

    @Override
    protected View doInBackground(View... views) {
        return null;
    }

    @Override
    protected void onPostExecute(View view) {
        super.onPostExecute(view);
    }
}
