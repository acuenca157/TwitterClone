package org.izv.di.acl.twitterclone.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;

import org.izv.di.acl.twitterclone.R;
import org.izv.di.acl.twitterclone.databinding.ActivityMainBinding;
import org.izv.di.acl.twitterclone.model.entity.Tweet;
import org.izv.di.acl.twitterclone.model.entity.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private static User user;
    private static Tweet actualTweet;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private CircleImageView profileMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void createTopbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    public void toggleUserOption(boolean status){
        if (status)
            profileMenuItem.setVisibility(View.VISIBLE);
        else
            profileMenuItem.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menuprofile);
        View view = MenuItemCompat.getActionView(menuItem);

        CircleImageView profileImage = view.findViewById(R.id.toolbar_profile_image);

        Glide.with(profileImage).load(user.urlUserPic).into(profileImage);

        profileMenuItem = profileImage;

        profileImage.setOnClickListener((View v) -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("searchUser", user);
            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main).navigate(R.id.action_tweetsFragment_to_profileFragment, bundle);
            //Toast.makeText(MainActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MainActivity.user = user;
    }

    public static Tweet getActualTweet() {
        return actualTweet;
    }

    public static void setActualTweet(Tweet actualTweet) {
        MainActivity.actualTweet = actualTweet;
    }
}
