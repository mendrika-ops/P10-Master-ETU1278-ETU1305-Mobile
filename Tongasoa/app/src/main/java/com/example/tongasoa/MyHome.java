package com.example.tongasoa;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.example.tongasoa.databinding.ActivityMyHomeBinding;
import com.example.tongasoa.modele.User;
import com.example.tongasoa.ui.settings.SettingsFragment;
import com.example.tongasoa.ui.site.Sites;
import com.example.tongasoa.ui.site.SitesFavorite;
import com.example.tongasoa.utils.ReminderBroadcast;
import com.example.tongasoa.utils.Utils;
import com.example.tongasoa.vue.Login;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

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


        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        this.createNotificationChannel();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        boolean reminder = sharedPreferences.getBoolean("reminder", true);
        int hourReminder = sharedPreferences.getInt("hourReminder", 8);
        int minuteReminder = sharedPreferences.getInt("minuteReminder", 0);
        if(reminder){
            SettingsFragment.setupNotification(this, hourReminder, minuteReminder);
        }
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

    }

    public void refreshMenu() {
        // Obtenez une référence aux SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String valueUser = sharedPreferences.getString("user", null);
        NavigationMenuItemView itemFavorite = findViewById(R.id.nav_favorite); // Remplacez par l'ID de l'élément du menu à masquer
        NavigationMenuItemView itemLogout = findViewById(R.id.nav_logout); // Remplacez par l'ID de l'élément du menu à masquer
        NavigationMenuItemView itemLogin = findViewById(R.id.nav_connexion); // Remplacez par l'ID de l'élément du menu à masquer
        CardView avatar = findViewById(R.id.cardAvatar);
        boolean isConnected = (valueUser != null );
        itemLogin.setVisibility(isConnected ? View.GONE : View.VISIBLE);
        itemLogout.setVisibility(isConnected ? View.VISIBLE : View.GONE);
        itemFavorite.setVisibility(isConnected ? View.VISIBLE : View.GONE);
        avatar.setVisibility(isConnected ? View.VISIBLE : View.GONE);

        if (isConnected) {
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Affichez les informations de l'utilisateur connecté ici
                    // Par exemple, afficher une boîte de dialogue ou une nouvelle activité

                    Gson gson = new Gson();
                    User user = gson.fromJson(valueUser, User.class);
                    // Exemple de boîte de dialogue basique
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyHome.this);
                    builder.setTitle("User Information")
                            .setMessage("Name : " + (user.getFirstName()!=null ? user.getFirstName() : "") + " " + (user.getName()!=null ? user.getName() : "")
                                    +"\nEmail : " + user.getEmail())
                            .setPositiveButton("OK", null)
                            .show();
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_home, menu);
        this.refreshMenu();
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.refreshMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_my_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.refreshMenu();
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
        }else if(item.getItemId() == R.id.nav_settings || item.getItemId() == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.nav_connexion){
            if (Utils.isNetworkAvailable(this)){
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
            } else {
                Toast.makeText(MyHome.this.getApplicationContext(), "You are not connected to the internet", Toast.LENGTH_LONG).show();
            }

        } else if(item.getItemId() == R.id.nav_favorite){
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment sitesFragment = new SitesFavorite(fragmentManager);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentHome, sitesFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else if(item.getItemId() == R.id.nav_logout){
            // Obtenez une référence aux SharedPreferences
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            // Éditez les SharedPreferences pour supprimer un élément
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("user");
            editor.apply();
            invalidateOptionsMenu();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.println(Log.VERBOSE,"LOG CLICK" , "--------indroo ");
        int id = item.getItemId();
        this.refreshMenu();
        if (id == R.id.nav_menuTheme) {
            // Lorsque l'élément "nav_connexion" est cliqué, redirigez vers l'Activity "Login"
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LearningReminderChannel";
            String description = "Channel for Learning Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifReminder", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            /*NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notifReminder")
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("Titre de la notification")
                    .setContentText("Contenu de la notification")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            notificationManager.notify(1, builder.build()); */
            Intent intent = new Intent(this, ReminderBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        }
    }

}