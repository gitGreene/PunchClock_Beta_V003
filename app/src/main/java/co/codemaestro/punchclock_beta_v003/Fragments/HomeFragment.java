package co.codemaestro.punchclock_beta_v003.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.codemaestro.punchclock_beta_v003.Adapters.PlusCardViewHolder;
import co.codemaestro.punchclock_beta_v003.Adapters.CategoryAdapter;
import co.codemaestro.punchclock_beta_v003.Adapters.CategoryViewHolder;
import co.codemaestro.punchclock_beta_v003.ViewModel.CategoryViewModel;
import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class
HomeFragment extends Fragment {
    private CategoryViewModel catViewModel;
    private static CategoryViewHolder.CategoryCardListener listener1;
    private static PlusCardViewHolder.PlusCardListener plusCardListener;
    private static final String TAG = "TAG";


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(CategoryViewHolder.CategoryCardListener listener, PlusCardViewHolder.PlusCardListener listener2) {
        listener1 = listener;
        plusCardListener = listener2;

        return new HomeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final CategoryAdapter adapter = new CategoryAdapter(getContext(), listener1, plusCardListener);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(adapter);


        catViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        catViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                adapter.setCategories(categories);
            }
        });

        return view;
    }
}
