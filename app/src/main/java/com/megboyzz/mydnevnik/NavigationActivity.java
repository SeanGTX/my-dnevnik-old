package com.megboyzz.mydnevnik;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.megboyzz.mydnevnik.ui.marks.MarksFragment;
import com.megboyzz.mydnevnik.util.SessionDataManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.Locale;

public class NavigationActivity extends AppCompatActivity {

    private SessionDataManager DataManager = new SessionDataManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("NavAct", "NA|onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_schedules,
                R.id.navigation_marks, R.id.navigation_homeworks)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        if(Locale.getDefault().getLanguage().equals("ru"))
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                navView.getMenu().findItem(R.id.navigation_homeworks).setTitle(R.string.title_homeworks);
            else navView.getMenu().findItem(R.id.navigation_homeworks).setTitle(R.string.title_homeworks_short);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if(fragment.equals(new MarksFragment())){
            getActionBar().setTitle(getString(R.string.current_marks) +  DataManager.getSchoolYear() + getString(R.string.current_marks_end));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Невозможно вернуться назад", Toast.LENGTH_LONG).show();
    }

    public void GoToSettings(MenuItem item) {
        Intent intent = new Intent(NavigationActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}
