package co.codemaestro.punchclock_beta_v003.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private ImageButton enterStartDate, enterEndDate;
    private String goalName;
    private long desiredGoalTime, goalCycleValue;
    private EditText enterGoalName, enterGoalValue, enterGoalCycleValue;

    public static GoalEntryFormFragment newInstance(int categoryId) {
        parentCategoryId = categoryId;
        return new GoalEntryFormFragment();

    }

    public GoalEntryFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_goal_entry_form,null );
        final AlertDialog.Builder newGoalFormBuilder = new AlertDialog.Builder(getActivity());
        newGoalFormBuilder.setView(view);


//        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
//        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
//        getDialog().getWindow().setLayout(width, height);

        enterStartDate = view.findViewById(R.id.start_date_button);
        enterEndDate = view.findViewById(R.id.end_date_button);
        enterGoalName = view.findViewById(R.id.enter_goal_name_et);
        enterGoalValue = view.findViewById(R.id.enter_goal_value_et);
        enterGoalCycleValue = view.findViewById(R.id.enter_goal_cycle_value_et);

        // TODO: Make the spinners exist and work properly



        return newGoalFormBuilder.create();
    }
}
