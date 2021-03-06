package co.codemaestro.punchclock_beta_v003.Fragments;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
    private TextView goalIdTv, parentCategoryIdTv, goalNameTv, timeSpentTv, desiredGoalTimeTv, goalCycleValueTv;


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

        goalIdTv = view.findViewById(R.id.goal_Id);
        parentCategoryIdTv = view.findViewById(R.id.parent_category_Id);
        goalNameTv = view.findViewById(R.id.goal_name);
        timeSpentTv = view.findViewById(R.id.time_spent);
        desiredGoalTimeTv = view.findViewById(R.id.desired_goal_time);
        goalCycleValueTv = view.findViewById(R.id.goal_cycle_value);


        addGoalButton = view.findViewById(R.id.add_goal_button);

        //TODO: Fix the goal entry form fragment size. Not respecting the views on screen
//        addGoalButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GoalEntryFormFragment goalEntryForm = GoalEntryFormFragment.newInstance(parentCategoryId);
//                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.category_goals_container, goalEntryForm);
//                fragmentTransaction.commit();
//            }
//        });


        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        categoryViewModel.getAllCategoryGoals(parentCategoryId).observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(@Nullable List<Goal> goals) {
                if(goals.size() != 0) {
                    tempGoal = goals.get(0);
                    goalIdTv.setText(tempGoal.getGoalId());
                    parentCategoryIdTv.setText(tempGoal.getParentCategoryId());
                    goalNameTv.setText(tempGoal.getGoalName());
                    timeSpentTv.setText(tempGoal.getTimeSpent() + "");
                    desiredGoalTimeTv.setText(tempGoal.getDesiredGoalTime() + "");
                    goalCycleValueTv.setText(tempGoal.getGoalCycleValue());
                } else {
                    goalIdTv.setText("null");
                }
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
