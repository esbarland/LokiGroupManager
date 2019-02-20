package com.example.lokigroupmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.lokigroupmanager.BasicsActivity.GroupsActivity;
import com.example.lokigroupmanager.BasicsActivity.ReminderActivity;
import com.example.lokigroupmanager.BasicsActivity.ScheduleActivity;
import com.example.lokigroupmanager.BasicsActivity.UsersActivity;
import com.example.lokigroupmanager.Modele.Group;
import com.example.lokigroupmanager.Modele.User;
import com.example.lokigroupmanager.OptionsActivity.AboutUsActivity;
import com.example.lokigroupmanager.OptionsActivity.SettingsActivity;
import com.example.lokigroupmanager.Persistence.ILoader;
import com.example.lokigroupmanager.Persistence.StubDataManager;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ILoader loader = new StubDataManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        List<Group> listGroups = loader.loadGroups();
        for (Group group : listGroups) {
            Log.d("ESTEBAN", group.getGroupName());
            for (User user: group.getListUsers()) {
                Log.d("ESTEBAN", user.getFirstname() + " - " + user.getSurname() + " - " + user.getEmail());
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /***
     * @param item Menu
     * Manage the behavior when we select a menu element.
     * @return true
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id){
            case R.id.nav_users:
                break;
            case R.id.nav_groups:
                break;
            case R.id.nav_schedule:
                break;
            case R.id.nav_reminder:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_info:
                break;
        }
        /*
        if (id == R.id.nav_users) {
            // Handle the camera action
        } else if (id == R.id.nav_groups) {

        } else if (id == R.id.nav_schedule) {

        } else if (id == R.id.nav_reminder) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_info) {

        }
        */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     *
     * startActivity pas propre car ça écrase les données passées
     *
     */

    /**
     * Event handle when press on settings option
     * @param item Menu
     */
    public void clickeventSettings(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * Event handle when press on about us option
     * @param item Menu
     */
    public void clickeventAboutUs(MenuItem item) {
        Intent intent = new Intent(this, AboutUsActivity.class);
        startActivity(intent);
    }

    /**
     * Event handle when press on users option
     * @param item Menu
     */
    public void clickeventUsers(MenuItem item) {
        Intent intent = new Intent(this, UsersActivity.class);
        startActivity(intent);
    }

    /**
     * Event handle when press on groups option
     * @param item Menu
     */
    public void clickeventGroups(MenuItem item) {
        Intent intent = new Intent(this, GroupsActivity.class);
        startActivity(intent);
    }

    /**
     * Event handle when press on schedule option
     * @param item Menu
     */
    public void clickeventSchedule(MenuItem item) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }

    /**
     * Event handle when press on reminder option
     * @param item Menu
     */
    public void clickeventReminder(MenuItem item) {
        Intent intent = new Intent(this, ReminderActivity.class);
        startActivity(intent);
    }

}
