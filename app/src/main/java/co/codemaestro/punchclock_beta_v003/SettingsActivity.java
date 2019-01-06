package co.codemaestro.punchclock_beta_v003;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

//TODO: Figure out why the hell I can't use themes to change the text color like wtf mangg

public class SettingsActivity extends AppCompatActivity {

    //Shared Preferences key for night mode boolean and sharedPref object
    private static final String PREFS_FILE = "SharedPreferences";
    private static final int PREFS_MODE = Context.MODE_PRIVATE;
    private static final String nightModeBooleanKey = "co.codemaestro.punchclock_beta_v003.nightModeKey";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Create SharedPrefs reference and boolean for nightMode
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, PREFS_MODE);
        boolean nightModeEnabled = prefs.getBoolean(nightModeBooleanKey, false);


        // Create references to Switch view and set the switch to the saved setting
        Switch switchNightMode = findViewById(R.id.switchNightMode);
        switchNightMode.setChecked(nightModeEnabled);

        // Create setOnCheckedChangeListener for our switch
        switchNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean nightModeOn) {

                SharedPreferences prefs = getSharedPreferences(PREFS_FILE, PREFS_MODE);

                if (nightModeOn) {

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

            }
        });


    }
}
