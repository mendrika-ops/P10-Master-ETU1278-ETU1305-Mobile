package com.example.tongasoa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tongasoa.databinding.ActivityMyHomeBinding;
import com.example.tongasoa.ui.site.Sites;
import com.example.tongasoa.utils.Utils;
import com.example.tongasoa.vue.Login;
import com.example.tongasoa.vue.SitePage;
import com.google.android.material.navigation.NavigationView;

public class MyHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMyHomeBinding binding;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMyHome.toolbar);
      /*  binding.appBarMyHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        Log.println(Log.VERBOSE,"NAV ", navigationView.getMenu().toString());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_my_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_my_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_menuTheme) {
            // Obtenir le gestionnaire de fragments (FragmentManager)
            FragmentManager fragmentManager = getSupportFragmentManager();

            // Créer une instance du Fragment "Sites"
            Fragment sitesFragment = new Sites(fragmentManager);

            // Commencer une transaction de fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Remplacer le contenu du conteneur principal par le fragment "Sites"
            fragmentTransaction.replace(R.id.fragmentHome, sitesFragment);
            // Ajouter la transaction au back stack (pour permettre le retour en arrière)
            fragmentTransaction.addToBackStack(null);

            // Confirmer la transaction
            fragmentTransaction.commit();
            return true;
        }else if(item.getItemId() == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.nav_connexion){
            if (Utils.isNetworkAvailable(this)){
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
            } else {
                Toast.makeText(MyHome.this.getApplicationContext(), "You are not connected to the internet", Toast.LENGTH_LONG).show();
            }

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.println(Log.VERBOSE,"LOG CLICK" , "--------indroo ");
        int id = item.getItemId();
        if (id == R.id.nav_menuTheme) {
            // Lorsque l'élément "nav_connexion" est cliqué, redirigez vers l'Activity "Login"
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}