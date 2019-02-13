package co.codemaestro.punchclock_beta_v003.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.codemaestro.punchclock_beta_v003.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryGoalsFragment extends Fragment {


    public CategoryGoalsFragment() {
        // Required empty public constructor
    }

    public static CategoryGoalsFragment newInstance(int position) {
        return new CategoryGoalsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_goals, container, false);
    }

}
