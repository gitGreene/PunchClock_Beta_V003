package co.codemaestro.punchclock_beta_v003;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryFragment extends AppCompatDialogFragment {

    public static AddCategoryFragment newInstance() {
        AddCategoryFragment fragment = new AddCategoryFragment();
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_category, null);

        final EditText newCategory = view.findViewById(R.id.edit_category);

        AlertDialog.Builder newCategoryFragmentBuilder = new AlertDialog.Builder(getActivity());
        newCategoryFragmentBuilder.setView(view);


//        return super.onCreateDialog(savedInstanceState);
        return newCategoryFragmentBuilder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
