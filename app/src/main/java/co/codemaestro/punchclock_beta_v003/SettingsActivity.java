package co.codemaestro.punchclock_beta_v003;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

//TODO: Figure out why the hell I can't use themes to change the text color like wtf mangg

public class SettingsActivity extends AppCompatActivity {

    //Shared Preferences key for night mode boolean and sharedPref object
    private static final String PREFS_FILE_SETTINGS = "SettingsSharedPreferences";
    private static final int PREFS_MODE_SETTINGS = Context.MODE_PRIVATE;
    private static final String nightModeBooleanKey = "co.codemaestro.punchclock_beta_v003.nightModeKey";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create SharedPrefs reference and boolean for nightMode
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE_SETTINGS, PREFS_MODE_SETTINGS);
        boolean nightModeEnabled = prefs.getBoolean(nightModeBooleanKey, false);


        // Self explanatory tbh
        if (nightModeEnabled) {
             setTheme(R.style.AppThemeNightMode);
        } else {
            setTheme(R.style.AppTheme);
        }

        // We then set the layout with the relevant theme
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Create references to Switch view and set the switch the the saved setting
        Switch switchNightMode = findViewById(R.id.switchNightMode);
        switchNightMode.setChecked(nightModeEnabled);

        // Create setOnCheckedChangeListener for our switch
        switchNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean nightModeOn) {

                SharedPreferences prefs = getSharedPreferences(PREFS_FILE_SETTINGS, PREFS_MODE_SETTINGS);

                if (nightModeOn) {

                    //Save "nightModeOn = true" to sharedPref and restart the activity
                    prefs.edit().putBoolean(nightModeBooleanKey, nightModeOn).apply();
                    finish();
                    Intent i = new Intent(getBaseContext(), SettingsActivity.class);
                    startActivity(i);
                } else {

                    //Save "nightModeOn = false" to sharedPref and restart the activity
                    prefs.edit().putBoolean(nightModeBooleanKey, nightModeOn).apply();
                    finish();
                    Intent i = new Intent(getBaseContext(), SettingsActivity.class);
                    startActivity(i);
                }

            }
        });

    }
}
