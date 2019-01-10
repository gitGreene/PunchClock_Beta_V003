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
//        CompoundButton nightModeSwitch = view.findViewById(R.id.switchNightMode);
//        nightModeSwitch.setChecked(false);
//        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                int nightMode = AppCompatDelegate.getDefaultNightMode();
//                if(isChecked) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    buttonView.toggle();
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    buttonView.toggle();
//                }
//                getActivity().recreate();
//            }
//        });
        return view;
    }

}
