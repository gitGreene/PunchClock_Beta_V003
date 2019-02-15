package co.codemaestro.punchclock_beta_v003.Fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

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


    public static GoalEntryFormFragment newInstance(int categoryId) {
        parentCategoryId = categoryId;
        return new GoalEntryFormFragment();

    }

    public GoalEntryFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_goal_entry_form, container, false);

        parentCategoryIDTextView = view.findViewById(R.id.parent_category_Id);
//        parentCategoryIDTextView.setText(parentCategoryId);
        addGoalButton = view.findViewById(R.id.add_goal_button);

        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);



        return view;
    }



}
