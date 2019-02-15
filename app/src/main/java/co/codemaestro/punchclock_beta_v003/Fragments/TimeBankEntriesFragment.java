package co.codemaestro.punchclock_beta_v003.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.codemaestro.punchclock_beta_v003.Adapters.DetailTimeBankAdapter;
import co.codemaestro.punchclock_beta_v003.Database.TimeBank;
import co.codemaestro.punchclock_beta_v003.R;
import co.codemaestro.punchclock_beta_v003.ViewModel.CategoryViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeBankEntriesFragment extends Fragment {
    CategoryViewModel categoryVM;
    private static int parentCategoryID;


    public TimeBankEntriesFragment() {
        // Required empty public constructor
    }

    public static TimeBankEntriesFragment newInstance(int categoryID) {
        parentCategoryID = categoryID;
        return new TimeBankEntriesFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_bank_entries, container, false);
        final RecyclerView tbEntriesRecyclerView = view.findViewById(R.id.timeBankEntriesRecyclerView);
        final DetailTimeBankAdapter detailTimeBankAdapter = new DetailTimeBankAdapter();
        tbEntriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tbEntriesRecyclerView.setAdapter(detailTimeBankAdapter);


        categoryVM = ViewModelProviders.of(this).get(CategoryViewModel.class);

        categoryVM.getCategoryTimeBanks(parentCategoryID).observe(this, new Observer<List<TimeBank>>() {
            @Override
            public void onChanged(@Nullable List<TimeBank> timeBanks) {
                detailTimeBankAdapter.setTimeBanks(timeBanks);
            }
        });
        return view;
    }

}
