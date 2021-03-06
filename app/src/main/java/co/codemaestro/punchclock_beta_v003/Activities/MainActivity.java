package co.codemaestro.punchclock_beta_v003.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import co.codemaestro.punchclock_beta_v003.Adapters.PlusCardViewHolder;
import co.codemaestro.punchclock_beta_v003.Adapters.CategoryViewHolder;
import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.Fragments.AddCategoryFragment;
import co.codemaestro.punchclock_beta_v003.Fragments.FavoritesFragment;
import co.codemaestro.punchclock_beta_v003.Fragments.GoalsFragment;
import co.codemaestro.punchclock_beta_v003.Fragments.HomeFragment;
import co.codemaestro.punchclock_beta_v003.Fragments.TimerFragment;
import co.codemaestro.punchclock_beta_v003.Interfaces.ListenFromMainActivity;
import co.codemaestro.punchclock_beta_v003.R;
import co.codemaestro.punchclock_beta_v003.ViewModel.CategoryViewModel;

public class MainActivity extends AppCompatActivity implements
        AddCategoryFragment.AddCategoryFragmentListener, CategoryViewHolder.CategoryCardListener, PlusCardViewHolder.PlusCardListener{

    private CategoryViewModel catViewModel;
    private BottomNavigationView bottomNav;
    private ListenFromMainActivity activityListener;

    private static final String PREFS_FILE = "SharedPreferences";
    private static final int PREFS_MODE = Context.MODE_PRIVATE;
    private static final String nightModeBooleanKey = "co.codemaestro.punchclock_beta_v003.nightModeKey";
    private static final String currentFragmentChosenKey = "co.codemaestro.punchclock_beta_v003.currentFragChosen";
    private boolean nightModeEnabled = false;
    private int startingPosition = 1;
    SharedPreferences prefs;

    public void setActivityListener(ListenFromMainActivity activityListener) {
        this.activityListener = activityListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Use sharedprefs to reactivate nightMode
        prefs = getSharedPreferences(PREFS_FILE, PREFS_MODE);
        nightModeEnabled = prefs.getBoolean(nightModeBooleanKey, false);
        if (nightModeEnabled) {
            // Set the night mode theme
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        startingPosition = prefs.getInt(currentFragmentChosenKey, 1);
        loadFragment(startingPosition);
        // Depending on the startingPosition, load a different SupportActionBar title string
        switch(startingPosition) {
            case 1:
                getSupportActionBar().setTitle(R.string.bottom_nav_home);
                break;
            case 2:
                getSupportActionBar().setTitle(R.string.bottom_nav_favorites);
                break;
            case 3:
                getSupportActionBar().setTitle(R.string.bottom_nav_goals);
                break;
            case 4:
                getSupportActionBar().setTitle(R.string.bottom_nav_timer);
                break;
            default:
                getSupportActionBar().setTitle(R.string.bottom_nav_home);
        }


        catViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        // Bottom Nav
        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int newPosition = 0;

                        switch (menuItem.getItemId()) {
                            case R.id.bottom_nav_home:
                                if (startingPosition != 1) {
                                    getSupportActionBar().setTitle(R.string.bottom_nav_home);
                                    newPosition = 1;
                                    return loadFragment(newPosition);
                                }
                                break;
                            case R.id.bottom_nav_favorites:
                                if (startingPosition != 2) {
                                    getSupportActionBar().setTitle(R.string.bottom_nav_favorites);
                                    newPosition = 2;
                                    return loadFragment(newPosition);
                                }
                                break;
                            case R.id.bottom_nav_goals:
                                if (startingPosition != 3) {
                                    getSupportActionBar().setTitle(R.string.bottom_nav_goals);
                                    newPosition = 3;
                                    return loadFragment(newPosition);
                                }
                                break;
                            case R.id.bottom_nav_timer:
                                if (startingPosition != 4) {
                                    getSupportActionBar().setTitle(R.string.bottom_nav_timer);
                                    newPosition = 4;
                                    return loadFragment(newPosition);
                                }
                                break;
                            default:
                                return loadFragment(newPosition);
                        }
                        return loadFragment(newPosition);
                    }
                }
        );
    } // End of onCreate

    private boolean loadFragment(int newPosition) {
        Fragment fragment = null;
        switch (newPosition) {
            case 1:
                fragment = HomeFragment.newInstance(this, this);
                break;
            case 2:
                fragment = FavoritesFragment.newInstance(this);
                break;
            case 3:
                fragment = new GoalsFragment();
                break;
            case 4:
                fragment = new TimerFragment();
                break;
        }

        if(fragment != null) {
            if(startingPosition > newPosition) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right );
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
            if(startingPosition < newPosition) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
            if(startingPosition == newPosition) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }

            startingPosition = newPosition;
            prefs.edit().putInt(currentFragmentChosenKey, newPosition).apply();
            return true;
        }

        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nightMode:
                Toast.makeText(this, "Night Mode Activate!", Toast.LENGTH_SHORT).show();
                // Get shared Prefs reference and toggle NightMode
                SharedPreferences prefs = getSharedPreferences(PREFS_FILE, PREFS_MODE);
                if (!nightModeEnabled) {
                    nightModeEnabled = true;
                }
                else {
                    nightModeEnabled = false;
                }
                // Save the correct nightModeEnabled value to preferences and change the app to nightMode
                if (nightModeEnabled) {
                    //Save "nightModeOn = true" to sharedPref and....
                    prefs.edit().putBoolean(nightModeBooleanKey, true).apply();
                    // Set the night mode theme
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    //Save "nightModeOn = false" to sharedPref and...
                    prefs.edit().putBoolean(nightModeBooleanKey, false).apply();
                    // Set the theme as not being night mode yo
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                recreate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onChoice(boolean choice, String newCategory) {
        // Add category to database
        Log.d("LOG", newCategory);
        activityListener.RecallNotifyDataSetChanged();
        Category addedCategory = new Category(newCategory, 0, 0, 0, "00:00 AM", false, false);
        catViewModel.insert(addedCategory);
    }

    @Override
    public void onCardAction(Category category) {
        catViewModel.updateCategory(category);
    }

    @Override
    public void onPlusCardAction() {
        AddCategoryFragment addCategoryFragment = AddCategoryFragment.newInstance();
        addCategoryFragment.show(getSupportFragmentManager(), "add category fragment");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
