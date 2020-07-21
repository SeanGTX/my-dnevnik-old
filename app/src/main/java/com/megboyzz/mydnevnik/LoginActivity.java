package com.megboyzz.mydnevnik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.megboyzz.mydnevnik.network.LoginManager;
import com.megboyzz.mydnevnik.util.SessionDataManager;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private String login;
    private String password;
    private EditText loginField;
    private EditText passwordField;
    private Button InsertButton;
    private Button ClearLoginButton;
    private ToggleButton ShowPasswordButton;
    private Map<String, String> loginCookies;
    private SessionDataManager DataManager = new SessionDataManager(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (DataManager.RelevantCookiesIsExists()){

            loginCookies = DataManager.getCookiesFromPreferences();

            LoginManager loginManager = new LoginManager(loginCookies, LoginActivity.this);

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

            loginManager.execute();
            return;

        }

        setContentView(R.layout.login_activity);

        loginField = findViewById(R.id.LoginInput);
        passwordField = findViewById(R.id.PasswordInput);

        InsertButton = findViewById(R.id.InsertData);

        InsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginField.setText("08012002@mail.ru");
                passwordField.setText("1282565121024QWEasdfZXC");
            }
        });

        ClearLoginButton = findViewById(R.id.ClearLogin);

        ClearLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginField.setText("");
            }
        });

        ShowPasswordButton = findViewById(R.id.ShowPassword);

        ShowPasswordButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) passwordField.setTransformationMethod(null);
                else passwordField.setTransformationMethod(new PasswordTransformationMethod());
                passwordField.setSelection(passwordField.length());
            }
        });

    }

    public void goToSwitcher(View v) {

        login = loginField.getText().toString();
        password = passwordField.getText().toString();

        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.fields_isempty), Toast.LENGTH_LONG).show();
            return;
        }



        LoginManager loginManager = new LoginManager(login, password, LoginActivity.this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        loginManager.execute();

    }

    public void RestoreAccess(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://login.dnevnik.ru/recovery"));
        startActivity(browserIntent);
    }

}