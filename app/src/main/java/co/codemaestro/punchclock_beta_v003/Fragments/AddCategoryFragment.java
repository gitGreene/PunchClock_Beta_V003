package co.codemaestro.punchclock_beta_v003.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.codemaestro.punchclock_beta_v003.R;


/**
 * Add Category Fragment
 */
public class AddCategoryFragment extends AppCompatDialogFragment {
    AddCategoryFragmentListener listener;

    public static AddCategoryFragment newInstance() {
        AddCategoryFragment fragment = new AddCategoryFragment();
        return fragment;
    }

    public interface AddCategoryFragmentListener {
        void onChoice(boolean choice, String newCategory);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AddCategoryFragmentListener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_add_category, null);

        final EditText newCategory = view.findViewById(R.id.edit_category);
        final Button createCategory = view.findViewById(R.id.create_category_button);
        final Button cancelDialog = view.findViewById(R.id.cancel_category_button);


        final AlertDialog.Builder newCategoryFragmentBuilder = new AlertDialog.Builder(getActivity());
        newCategoryFragmentBuilder.setView(view);

        // TODO: Send New Category Name back to MainActivity/ViewModel
        createCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChoice(true, newCategory.getText().toString());
                dismiss();
            }
        });

        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return newCategoryFragmentBuilder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
