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
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        AddCategoryFragment.AddCategoryFragmentListener{

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
                        switch (menuItem.getItemId()) {
                            case R.id.action_add_category:
                                AddCategoryFragment addCategoryFragment = AddCategoryFragment.newInstance();
                                addCategoryFragment.show(getSupportFragmentManager(), "add category fragment");
                            case R.id.action_settings:
                                // TODO: add activity for settings
                        }

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


    @Override
    public void onChoice(boolean choice, String newCategory) {
        // Add category to database
        Log.d("LOG", newCategory);
        Category addedCategory = new Category(newCategory, "00:00:00");
        catViewModel.insert(addedCategory);
    }
}
