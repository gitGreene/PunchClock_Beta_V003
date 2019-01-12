package co.codemaestro.punchclock_beta_v003.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import co.codemaestro.punchclock_beta_v003.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    private static final String PREFS_FILE = "SharedPreferences";
    private static final int PREFS_MODE = Context.MODE_PRIVATE;
    private static final String nightModeBooleanKey = "co.codemaestro.punchclock_beta_v003.nightModeKey";


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SharedPreferences prefs = this.getActivity().getSharedPreferences(PREFS_FILE, PREFS_MODE);
        boolean nightModeEnabled = prefs.getBoolean(nightModeBooleanKey, false);

        CompoundButton nightModeSwitch = view.findViewById(R.id.switchNightMode);

        if (nightModeEnabled) { nightModeSwitch.setChecked(true); }

        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_FILE, PREFS_MODE);
                if (isChecked) {
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
            }
        });
        return view;
    }

}
