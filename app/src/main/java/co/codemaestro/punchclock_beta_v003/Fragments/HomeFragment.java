package co.codemaestro.punchclock_beta_v003.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.codemaestro.punchclock_beta_v003.Activities.DetailActivity;
import co.codemaestro.punchclock_beta_v003.Activities.MainActivity;
import co.codemaestro.punchclock_beta_v003.Adapters.PlusCardViewHolder;
import co.codemaestro.punchclock_beta_v003.Adapters.CategoryAdapter;
import co.codemaestro.punchclock_beta_v003.Adapters.CategoryViewHolder;
import co.codemaestro.punchclock_beta_v003.Interfaces.ListenFromMainActivity;
import co.codemaestro.punchclock_beta_v003.ViewModel.CategoryViewModel;
import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ListenFromMainActivity {
    private CategoryViewModel catViewModel;
    private static CategoryViewHolder.CategoryCardListener cardListener;
    private static PlusCardViewHolder.PlusCardListener plusCardListener;
    private static Boolean cardDataIsSet;
    private static final String TAG = "TAG";

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(CategoryViewHolder.CategoryCardListener listener1, PlusCardViewHolder.PlusCardListener listener2) {
        cardListener = listener1;
        plusCardListener = listener2;
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final CategoryAdapter adapter = new CategoryAdapter(getContext(), cardListener, plusCardListener);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(adapter);

        ((MainActivity) getActivity()).setMainActivityListener(HomeFragment.this);
        catViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        cardDataIsSet = false;

        catViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                if (!cardDataIsSet) {
                    adapter.setCategories(categories);
                    cardDataIsSet = true;
                }
            }
        });
        return view;
    }

    @Override
    public void ActionFromMain() {
        cardDataIsSet = false;
        Log.e(TAG, "ActionFromMain: Called");
    }
}
