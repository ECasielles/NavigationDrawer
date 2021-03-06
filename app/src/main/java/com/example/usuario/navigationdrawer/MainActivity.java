package com.example.usuario.navigationdrawer;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements FragmentOne.OnFragmentInteractionListener,
        FragmentTwo.OnFragmentInteractionListener {
//MODO PACO: implements NavigationView.OnNavigationItemSelectedListener { <- MAL

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navview);

        //Icono de inicio de la toolbar
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_home);
        //Cuando haga clic en el icono, sea una opción del menú
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupNavigationView();
    }

    /**
     * Método que inicializa el Listener NavigationItemSelected, y en base a la opción
     * seleccionada se realiza una acción.
     */
    private void setupNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();
                if (id == R.id.action_home) {
                    launchFragment(FragmentOne.class, item);
                } else if (id == R.id.action_dependency) {
                    launchFragment(FragmentTwo.class, item);
                } else if (id == R.id.action_sector) {
                    launchFragment(FragmentOne.class, item);
                } else if (id == R.id.action_settings) {
                    launchSettingsActivity();
                } else if (id == R.id.action_help) {
                    launchFragment(FragmentTwo.class, item);
                }
                return true;
            }
        });
    }

    public void launchSettingsActivity() {
        //Si está abierto, lo cierra
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START))
            this.drawerLayout.closeDrawer(GravityCompat.START);
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    public void launchFragment(Class fragmentClass, MenuItem item) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_main, fragment).commit();

            //No hay que deseleccionar en los elementos del grupo de menú
            item.setChecked(true);
            //TODO: Deseleccionar las opciones de help y settings, que no están en un grupo
            getSupportActionBar().setTitle(item.getTitle());
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Nos sacamos home de Android
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                Log.d("NavigationDrawer", "Se ha pulsado icono home");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Sobreescribo el botón back
    @Override
    public void onBackPressed() {
        //Si está abierto, lo cierra
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START))
            this.drawerLayout.closeDrawer(GravityCompat.START);
        else
            //Patata caliente
            super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
