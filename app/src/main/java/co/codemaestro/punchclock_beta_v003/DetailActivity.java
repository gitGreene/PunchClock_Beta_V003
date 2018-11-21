package co.codemaestro.punchclock_beta_v003;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {
//    private CategoryViewModel catViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        TODO: Do we need this?
//        catViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
    }
}
