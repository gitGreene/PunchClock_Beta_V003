package co.codemaestro.punchclock_beta_v003.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.Fragments.AddCategoryFragment;
import co.codemaestro.punchclock_beta_v003.Fragments.FavoritesFragment;
import co.codemaestro.punchclock_beta_v003.Fragments.HomeFragment;
import co.codemaestro.punchclock_beta_v003.Fragments.SettingsFragment;
import co.codemaestro.punchclock_beta_v003.R;
import co.codemaestro.punchclock_beta_v003.ViewModel.CategoryViewModel;

public class MainActivity extends AppCompatActivity implements
        AddCategoryFragment.AddCategoryFragmentListener{

    private CategoryViewModel catViewModel;
    private BottomNavigationView bottomNav;

    //Shared Preferences key for night mode boolean and sharedPref object
    private static final String PREFS_FILE = "SharedPreferences";
    private static final int PREFS_MODE = Context.MODE_PRIVATE;
    private static final String nightModeBooleanKey = "co.codemaestro.punchclock_beta_v003.nightModeKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new HomeFragment());
        catViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        // Use sharedprefs to reactivate nightMode
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, PREFS_MODE);
        boolean nightModeEnabled = prefs.getBoolean(nightModeBooleanKey, false);
        if (nightModeEnabled) {
            // Set the night mode theme
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //recreate();
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCategoryFragment addCategoryFragment = AddCategoryFragment.newInstance();
                addCategoryFragment.show(getSupportFragmentManager(), "add category fragment");
            }
        });

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.bottom_nav_home:
                                fragment = new HomeFragment();
                                break;
                            case R.id.bottom_nav_favorites:
                                fragment = new FavoritesFragment();
                                break;
                            case R.id.bottom_nav_settings:
                                fragment = new SettingsFragment();
                                break;
                        }
                        return loadFragment(fragment);
                    }
                }
        );
    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            transaction.replace(R.id.container, fragment);
            transaction.commit();
            return true;
        }
        return false;
    }

    @Override
    public void onChoice(boolean choice, String newCategory) {
        // Add category to database
        Log.d("LOG", newCategory);
        Category addedCategory = new Category(newCategory, "00:00:00");
        catViewModel.insert(addedCategory);
    }
}
