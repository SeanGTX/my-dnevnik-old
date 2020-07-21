package com.megboyzz.mydnevnik.network;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.megboyzz.mydnevnik.NavigationActivity;
import com.megboyzz.mydnevnik.R;
import com.megboyzz.mydnevnik.util.SessionDataManager;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Formatter;
import java.util.Map;

public class LoginManager extends AsyncTask<Void, String, Integer> {

    //показ расписания окошком))

    protected static final String dnevnikLoginURL = "https://login.dnevnik.ru/";
    protected static final String dnevnikURL = "https://dnevnik.ru/"; //"https://schools.dnevnik.ru/marks.aspx?school=%s&index=10&tab=period&period=1&homebasededucation=False"
    protected static String DnevnikMarksURL = "https://schools.dnevnik.ru/marks.aspx?school=%s&index=10&tab=period&homebasededucation=False";
    protected static final String DnevnikHomeworksURL = "https://schools.dnevnik.ru/marks.aspx";
    protected static String DnevnikSchedulesURL = "https://schools.dnevnik.ru/schedules";
    private String login = "";
    private String password = "";
    private Connection.Response LoginResponse;
    private static Map<String, String> loginCookies;
    private static final int MAINTENANCE_CODE = 2;
    private static final int SUCCESS_LOGIN_CODE = 1;
    private static final int FAILED_LOGIN_CODE = 0;
    private static final int UNKNOWN_ERROR_CODE = -1;
    private static final int NO_INTERNET_CONNECTION_CODE = -2;
    private static final int REQUEST_LIMIT_EXCEEDED = -3;
    private Context context;
    private SessionDataManager DataManager;
    private ProgressDialog progressDialog;
    protected boolean ReEntry = false;

    public LoginManager(String login, String password, Context context) {
        this.login = login;
        this.password = password;
        this.context = context;
        DataManager = new SessionDataManager(context);
    }

    public LoginManager(Map<String, String> loginCookies, Context context){
        LoginManager.loginCookies = loginCookies;
        this.context = context;
        DataManager = new SessionDataManager(context);
        ReEntry = true;
    }

    protected static void ShowDetailsOf(Connection.Response response){
        Log.i("LoginManager",
               "\n" + "Cookies: " + response.cookies()  + "\n" +
                        "Cookies length = " + response.cookies().size() + "\n" +
                        "Location: " + response.headers().get("Location") + "\n" +
                        "URL: " + response.url() + "\n" +
                        "Status: " + response.statusCode() + " " + response.statusMessage() + "\n"
        );
    }

    public void run(int status){
        Intent intent = new Intent(context, NavigationActivity.class);
        intent.putExtra("status", status);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setMessage(context.getString(R.string.please_wait));
    }

    @Override
    protected Integer doInBackground(Void... voids) {

        Connection.Response CheckResponse = null;
        try {
            CheckResponse = Jsoup.connect("http://dnevnik.ru/").execute();
        }catch (HttpStatusException e) {
            Log.e("LoginManager", e.toString());
            return MAINTENANCE_CODE;
        }catch (IOException e){
            Log.e("LoginManager", e.toString());
            return NO_INTERNET_CONNECTION_CODE;
        }

        Log.i("LoginManager", "No Maintance works!:)");

        try {
            if (ReEntry)
                LoginResponse = Jsoup.connect(dnevnikURL)
                        .cookies(loginCookies)
                        .execute();
            else
                LoginResponse = Jsoup.connect(dnevnikLoginURL)
                        .data("login", login)
                        .data("password", password)
                        .method(Connection.Method.POST)
                        .execute();
        }catch (HttpStatusException e){
            Log.e("LoginManager", e.toString());
            return REQUEST_LIMIT_EXCEEDED;
        }catch (Exception e){
            Log.e("LoginManager", e.toString());
            return UNKNOWN_ERROR_CODE;
        }

        DataManager.saveCookies(LoginResponse.cookies());

        loginCookies = DataManager.getCookiesFromPreferences();

        PrimaryMarks primaryMarks = new PrimaryMarks(ReEntry, context, loginCookies);
        primaryMarks.execute();

        PrimaryHomeworks primaryHomeworks = new PrimaryHomeworks(ReEntry, context, loginCookies);
        primaryHomeworks.execute();

        PrimarySchedules primarySchedules = new PrimarySchedules(ReEntry, context, loginCookies);
        primarySchedules.execute();


        if(ReEntry) return SUCCESS_LOGIN_CODE;

        if(!DataManager.getCookie("DnevnikAuth_l").isEmpty()) return SUCCESS_LOGIN_CODE;
        else return FAILED_LOGIN_CODE;

    }

    @Override
    protected void onPostExecute(Integer status) {
        super.onPostExecute(status);
        progressDialog.cancel();

        switch (status){
            case SUCCESS_LOGIN_CODE: {
                if(!ReEntry) Toast.makeText(context, context.getString(R.string.succsess_login), Toast.LENGTH_LONG).show();
                run(SUCCESS_LOGIN_CODE);
            }break;
            case FAILED_LOGIN_CODE: Toast.makeText(context, context.getString(R.string.wrong_login), Toast.LENGTH_LONG).show(); break;
            case MAINTENANCE_CODE:{

                AlertDialog.Builder MaintenanceDialog = new AlertDialog.Builder(context);

                MaintenanceDialog.setTitle(R.string.maintenance_work);

                if(ReEntry) {

                    MaintenanceDialog.setMessage(R.string.maintenance_work_message);

                    MaintenanceDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            run(MAINTENANCE_CODE);
                        }
                    });

                    MaintenanceDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    });
                    MaintenanceDialog.show();

                }else Toast.makeText(context, context.getString(R.string.maintenance_work), Toast.LENGTH_LONG).show();

            } break;
            case UNKNOWN_ERROR_CODE: Toast.makeText(context, context.getString(R.string.server_error), Toast.LENGTH_LONG).show(); break;
            case NO_INTERNET_CONNECTION_CODE:{

                AlertDialog.Builder NoInternetDialog = new AlertDialog.Builder(context);

                NoInternetDialog.setTitle(R.string.no_connection);

                if(ReEntry){
                    NoInternetDialog.setMessage(R.string.no_connection_message);
                    NoInternetDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            run(NO_INTERNET_CONNECTION_CODE);
                        }
                    });
                    NoInternetDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    });
                    NoInternetDialog.show();
                }else Toast.makeText(context, context.getString(R.string.no_connection), Toast.LENGTH_LONG).show();


            }
        }
    }
}

class PrimaryMarks extends AsyncTask<Void, Void, Void>{

    private boolean ReEntry = false;
    private Context context;
    private Map<String, String> loginCookies;
    private SessionDataManager DataManager;
    private Connection.Response PrimaryDataResponse;

    public PrimaryMarks(boolean reEntry, Context context, Map<String, String> loginCookies) {
        ReEntry = reEntry;
        this.context = context;
        this.loginCookies = loginCookies;
        DataManager = new SessionDataManager(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        File PrimaryMarksFile = DataManager.createHTML("PrimaryMarksFile");

        try{
            Formatter URL_formatter = new Formatter();
            URL_formatter.format(LoginManager.DnevnikMarksURL, loginCookies.get("t0"));
            LoginManager.DnevnikMarksURL = URL_formatter.toString();
            PrimaryDataResponse = Jsoup.connect(LoginManager.DnevnikMarksURL)
                    .referrer(LoginManager.dnevnikURL)
                    .cookies(loginCookies)
                    .execute();
            Document MarksDoc = PrimaryDataResponse.charset("utf-8").parse();
            String[] name = MarksDoc.selectFirst("h2").text().split(" ");
            String[] Class = MarksDoc.selectFirst("h3").text().split(" ");
            String School = MarksDoc.selectFirst("ul.crumbs").selectFirst("a").text();
            DataManager.saveGeneralInfo(name[0], name[1], name[2], Class[0], School, Class[1]);
            BufferedWriter MarksWriter = new BufferedWriter(new FileWriter(PrimaryMarksFile));
            MarksWriter.write(String.valueOf(MarksDoc.select("div#content").first()));
            MarksWriter.close();
        }catch (Exception e){
            Log.e("LoginManager", e.toString());
            cancel(true);
        }

        DataManager.saveCookies(PrimaryDataResponse.cookies());

        loginCookies = DataManager.getCookiesFromPreferences();

        return null;
    }
}

class PrimaryHomeworks extends AsyncTask<Void, Void, Void>{

    private boolean ReEntry = false;
    private Context context;
    private Map<String, String> loginCookies;
    private SessionDataManager DataManager;
    private Connection.Response PrimaryDataResponse;

    public PrimaryHomeworks(boolean reEntry, Context context, Map<String, String> loginCookies) {
        ReEntry = reEntry;
        this.context = context;
        this.loginCookies = loginCookies;
        DataManager = new SessionDataManager(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        File PrimaryHomeWorksFile = DataManager.createHTML("PrimaryHomeWorksFile");

        try{
            PrimaryDataResponse = Jsoup.connect(LoginManager.DnevnikHomeworksURL)
                    .referrer(LoginManager.dnevnikURL)
                    .cookies(loginCookies)
                    .execute();
            Document HomeWorksDoc = PrimaryDataResponse.charset("utf-8").parse();
            BufferedWriter MarksWriter = new BufferedWriter(new FileWriter(PrimaryHomeWorksFile));
            MarksWriter.write(String.valueOf(HomeWorksDoc.select("div#content").first()));
            MarksWriter.close();
        }catch (Exception e){
            Log.e("LoginManager", e.toString());
            cancel(true);
        }

        DataManager.saveCookies(PrimaryDataResponse.cookies());

        return null;
    }
}

class PrimarySchedules extends AsyncTask<Void, Void, Void>{

    private boolean ReEntry = false;
    private Context context;
    private Map<String, String> loginCookies;
    private SessionDataManager DataManager;
    private Connection.Response PrimaryDataResponse;

    public PrimarySchedules(boolean reEntry, Context context, Map<String, String> loginCookies) {
        ReEntry = reEntry;
        this.context = context;
        this.loginCookies = loginCookies;
        DataManager = new SessionDataManager(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        File PrimarySchedulesFile = DataManager.createHTML("PrimarySchedulesFile");

        try{
            PrimaryDataResponse = Jsoup.connect(LoginManager.DnevnikSchedulesURL)
                    .referrer(LoginManager.dnevnikURL)
                    .cookies(loginCookies)
                    .execute();
            Document SchedulesDoc = PrimaryDataResponse.charset("utf-8").parse();
            BufferedWriter MarksWriter = new BufferedWriter(new FileWriter(PrimarySchedulesFile));
            MarksWriter.write(String.valueOf(SchedulesDoc.select("div#content").first()));
            MarksWriter.close();
        }catch (Exception e){
            Log.e("LoginManager", e.toString());
            cancel(true);
        }

        if(!ReEntry)
            try{
                File CallSchedulesFile = DataManager.createHTML("CallSchedulesFile");

                PrimaryDataResponse = Jsoup.connect(LoginManager.DnevnikSchedulesURL)
                        .referrer(LoginManager.dnevnikURL)
                        .cookies(loginCookies)
                        .execute();

                DataManager.saveCookies(PrimaryDataResponse.cookies());
                loginCookies = DataManager.getCookiesFromPreferences();

                PrimaryDataResponse = Jsoup.connect(PrimaryDataResponse.url() + "&tab=timetable")
                        .referrer(LoginManager.dnevnikURL)
                        .cookies(loginCookies)
                        .execute();

                Document SchedulesDoc = PrimaryDataResponse.charset("utf-8").parse();
                BufferedWriter MarksWriter = new BufferedWriter(new FileWriter(CallSchedulesFile));
                MarksWriter.write(String.valueOf(SchedulesDoc.select("div#content").first()));
                MarksWriter.close();

            }catch (Exception e){
                Log.e("LoginManager", e.toString());
                cancel(true);
            }

        DataManager.saveCookies(loginCookies);

        return null;
    }
}

