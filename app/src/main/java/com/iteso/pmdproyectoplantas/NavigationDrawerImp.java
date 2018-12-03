package com.iteso.pmdproyectoplantas;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class NavigationDrawerImp extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mActionBarToolbar;
    private DrawerLayout mDrawerLayout;

    /**
     * Obtiene y crea el drawer para el NavigationDrawer.
     * Este método es llamado en el postCreate, debido a la llamada
     * toggle.syncState() que se recomienda hacer en el postCreate.
     */
    private void setupNavDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(mDrawerLayout == null) {
            return;
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mActionBarToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView == null) {
            return;
        }

        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     *Hace la llamada a getActionBarToolbar, es recomendado hacer esta llamada en
     * el método setContentView ya que, para el momento que se llega al onCreate,
     * ya está definido el ActionBar.
     */
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }

    /**
     * @return el toolbar de la actividad. Para la mayoría de las actividades de este
     * proyecto se utiliza el tema AppTheme.NoActionBar, por lo que generalmente también
     * habrá un AppBarLayout en el layout de la actividad.
     */
    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_plants_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_plants) {
            // Handle the camera action
            Intent intent = new Intent(this, ActivityPlants.class);
            startActivity(intent);
        } else if (id == R.id.nav_events) {
            Intent intent = new Intent(this, ActivityMain.class);
            startActivity(intent);
        } /*else if (id == R.id.nav_alarms) {

        } else if (id == R.id.nav_notifications) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_settings) {

        } */else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();

            Intent intent = new Intent(this, ActivityLogin.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
