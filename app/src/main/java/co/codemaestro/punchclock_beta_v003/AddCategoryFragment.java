package co.codemaestro.punchclock_beta_v003;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * Add Category Fragment
 */
public class AddCategoryFragment extends AppCompatDialogFragment {
    AddCategoryFragmentListener listener;

    public static AddCategoryFragment newInstance() {
        AddCategoryFragment fragment = new AddCategoryFragment();
        return fragment;
    }

    interface AddCategoryFragmentListener {
        void onChoice(boolean choice);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_category, null);

        final EditText newCategory = view.findViewById(R.id.edit_category);
//        final Button saveButton = view.findViewById(R.id.button_save);

        AlertDialog.Builder newCategoryFragmentBuilder = new AlertDialog.Builder(getActivity());
        newCategoryFragmentBuilder.setView(view);

        newCategoryFragmentBuilder.setPositiveButton(R.string.add_category_fragment_create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onChoice(true);
            }
        }).setNegativeButton(R.string.add_category_fragment_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onChoice(false);
                dialog.cancel();
            }
        });


        return newCategoryFragmentBuilder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
