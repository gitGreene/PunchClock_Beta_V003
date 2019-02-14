package co.codemaestro.punchclock_beta_v003.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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


    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_category_goals, container, false);




        // Inflate the layout for this fragment
        return view;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }
}
