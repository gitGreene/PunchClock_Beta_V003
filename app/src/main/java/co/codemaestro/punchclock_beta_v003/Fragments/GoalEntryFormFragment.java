package co.codemaestro.punchclock_beta_v003.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import co.codemaestro.punchclock_beta_v003.Database.Goal;
import co.codemaestro.punchclock_beta_v003.R;
import co.codemaestro.punchclock_beta_v003.ViewModel.CategoryViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoalEntryFormFragment extends AppCompatDialogFragment {

    private CategoryViewModel categoryViewModel;
    private static int parentCategoryId;
    private TextView parentCategoryIDTextView;
    private Button addGoalButton;
    private String goalName;
    private long desiredGoalTime, goalCycleValue;
    private EditText enterGoalName, enterDesiredTime, enterGoalCycleValue;

    public static GoalEntryFormFragment newInstance(int categoryId) {
        parentCategoryId = categoryId;
        return new GoalEntryFormFragment();

    }

    public GoalEntryFormFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.fragment_goal_entry_form,null );

        parentCategoryIDTextView = view.findViewById(R.id.parent_category_Id);
        final AlertDialog.Builder newGoalFormBuilder = new AlertDialog.Builder(getActivity());
        newGoalFormBuilder.setView(view);

        enterGoalName = view.findViewById(R.id.enter_goal_edit_text);
        enterDesiredTime = view.findViewById(R.id.enter_desired_time_edit_text);
        enterGoalCycleValue = view.findViewById(R.id.enter_goal_cycle_value_edit_text);

        final String goalName = enterGoalName.getText().toString();

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        addGoalButton = view.findViewById(R.id.add_goal_button);
        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goal goal = new Goal(parentCategoryId, goalName, 0L, 0L, "24 Hours");
                categoryViewModel.insertGoal(goal);
            }
        });




        return newGoalFormBuilder.create();
    }
}
