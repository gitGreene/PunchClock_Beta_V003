package co.codemaestro.punchclock_beta_v003;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CategoryViewModel catViewModel;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                        return false;
                    }
                }
        );

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CategoryAdapter adapter = new CategoryAdapter(this);
        recyclerView.setAdapter(adapter);
        // TODO: Fix spanCount
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        catViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        catViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                adapter.setCategories(categories);
            }
        });
    }
}
