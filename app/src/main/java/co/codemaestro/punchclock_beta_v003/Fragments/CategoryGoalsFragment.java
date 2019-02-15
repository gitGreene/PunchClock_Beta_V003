package co.codemaestro.punchclock_beta_v003.Fragments;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.codemaestro.punchclock_beta_v003.Database.Goal;
import co.codemaestro.punchclock_beta_v003.R;
import co.codemaestro.punchclock_beta_v003.ViewModel.CategoryViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryGoalsFragment extends Fragment {
    private Button addGoalButton;
    private static int parentCategoryId;
    private CategoryViewModel categoryViewModel;
    private Goal tempGoal;


    public CategoryGoalsFragment() {
        // Required empty public constructor
    }

    public static CategoryGoalsFragment newInstance(int position) {
        parentCategoryId = position;
        return new CategoryGoalsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_category_goals, container, false);

        addGoalButton = view.findViewById(R.id.add_goal_button);

        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoalEntryFormFragment goalEntryForm = GoalEntryFormFragment.newInstance(parentCategoryId);
                goalEntryForm.show(getFragmentManager(), "goal entry form fragment");
            }
        });

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        categoryViewModel.getAllCategoryGoals(parentCategoryId).observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(@Nullable List<Goal> goals) {
//                if(goals != null){
//                    tempGoal = goals.get(0);
//                } else {
//                    Toast.makeText(getContext(), "No Goals set yet fool", Toast.LENGTH_LONG).show();
//                }
                Toast.makeText(getContext(), "No Goals set yet fool", Toast.LENGTH_LONG).show();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }
}
