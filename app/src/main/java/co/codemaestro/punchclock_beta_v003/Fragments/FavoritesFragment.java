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

import co.codemaestro.punchclock_beta_v003.Adapters.CategoryAdapter;
import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.R;
import co.codemaestro.punchclock_beta_v003.ViewModel.CategoryViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    private CategoryViewModel categoryViewModel;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        final RecyclerView favoritesRecycler = view.findViewById(R.id.favorites_recyclerview);
        final CategoryAdapter favoritesAdapter = new CategoryAdapter(getContext());
        favoritesRecycler.setLayoutManager(new GridLayoutManager(getContext(), 1));
        favoritesRecycler.setAdapter(favoritesAdapter);

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        categoryViewModel.getFavorites().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                favoritesAdapter.setCategories(categories);
            }
        });


        return view;
    }

}
