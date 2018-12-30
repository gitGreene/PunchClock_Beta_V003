package co.codemaestro.punchclock_beta_v003;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

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

        // Use sharedprefs to reactivate nightMode
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, PREFS_MODE);
        boolean nightModeEnabled = prefs.getBoolean(nightModeBooleanKey, false);
        if (nightModeEnabled) {
            // Set the night mode theme
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //recreate();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
                        switch (menuItem.getItemId()) {
                            case R.id.action_home:
                                // Do nothing?
                                break;
                            case R.id.action_favorites:
                                // TODO: code that reloads the recyclerView with favorites
                                //Intent favIntent = new Intent(getBaseContext(), FavoritesActivity.class);
                                //startActivity(favIntent);
                                Toast.makeText(MainActivity.this, "There is no favorites activity", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_settings:
                                // Intent to SettingsActivity
                                Intent actionIntent = new Intent(getBaseContext(), SettingsActivity.class);
                                startActivity(actionIntent);
                                break;
                        }

                        return false;
                    }
                }
        );

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CategoryAdapter adapter = new CategoryAdapter(this);
        recyclerView.setAdapter(adapter);
        // TODO: Fix spanCount
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        catViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        catViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                adapter.setCategories(categories);
            }
        });
    }


    @Override
    public void onChoice(boolean choice, String newCategory) {
        // Add category to database
        Log.d("LOG", newCategory);
        Category addedCategory = new Category(newCategory, "00:00:00");
        catViewModel.insert(addedCategory);
    }
}
