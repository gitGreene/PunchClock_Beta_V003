package co.codemaestro.punchclock_beta_v003.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;


import co.codemaestro.punchclock_beta_v003.Classes.FormatMillis;
import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.Activities.DetailActivity;
import co.codemaestro.punchclock_beta_v003.R;
import co.codemaestro.punchclock_beta_v003.ViewModel.CategoryViewModel;
import co.codemaestro.punchclock_beta_v003.ViewModel.TimerViewModel;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private TimerViewModel timerViewModel;
    FormatMillis format = new FormatMillis();
    private List<Category> categories;
    private Context context;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    // Passes the list of categories we received from the Observer
    //
    public void setCategories(List<Category> categories) {
        this.categories = categories;
        // Notifies the adapter that the underlying data has changed
        // Therefore causing the Adapter to refresh
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(categories != null) {
            return categories.size();
        } else return 0;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.category_card, parent, false);

        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, int position) {
        holder.setCategory(categories.get(position));
        final Category current = categories.get(position);
        holder.favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current.isFavorite()) {
                    holder.favoriteIcon.setChecked(true);
                } else {
                    holder.favoriteIcon.setChecked(false);
                }
            }
        });
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView timeBankTitleView;
        private final TextView timeBankValueView;
        private final Button playButton;
        private final ToggleButton favoriteIcon;
        private final LinearLayout categoryCardLayout;

        Category category;

        private CategoryViewHolder(View itemView) {
            super(itemView);
            timeBankTitleView = itemView.findViewById(R.id.timeBankTitle);
            timeBankValueView = itemView.findViewById(R.id.timeBankValue);
            playButton = itemView.findViewById(R.id.playButton);
            categoryCardLayout = itemView.findViewById(R.id.card_layout);
            favoriteIcon = itemView.findViewById(R.id.favorite_icon);
            itemView.setOnClickListener(this);



            favoriteIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        category.setFavorite(true);
                    } else {
                        category.setFavorite(false);
                    }
                }
            });

        }

        public void setCategory(final Category category) {
            this.category = category;
            timeBankTitleView.setText(category.getCategory());
            timeBankValueView.setText(format.FormatMillisIntoHMS(category.getTotalTime()));
            playButton.setText(R.string.play_button);
            if(category.isFavorite()) {
                favoriteIcon.setChecked(true);
            } else {
                favoriteIcon.setChecked(false);
            }
        }

        @Override
        public void onClick(View v) {
            // Create category object to hold category
            Category currentCategory = categories.get(getAdapterPosition());

            // Send category over and start the detail activity
            Intent detailIntent = new Intent(context, DetailActivity.class);
            detailIntent.putExtra("category_id", currentCategory.getId());
            detailIntent.putExtra("category_title", currentCategory.getCategory());
            detailIntent.putExtra("display_time", currentCategory.getDisplayTime());
            detailIntent.putExtra("timer_after_life", currentCategory.getTimeAfterLife());
            detailIntent.putExtra("is_running", currentCategory.isTimerRunning());
            detailIntent.putExtra("is_favorite", currentCategory.isFavorite());

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, categoryCardLayout, ViewCompat.getTransitionName(categoryCardLayout));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                context.startActivity(detailIntent, options.toBundle());
            }


        }
    }
}
