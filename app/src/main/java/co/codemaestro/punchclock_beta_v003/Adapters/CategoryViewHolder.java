package co.codemaestro.punchclock_beta_v003.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;


import java.util.List;

import co.codemaestro.punchclock_beta_v003.Activities.DetailActivity;
import co.codemaestro.punchclock_beta_v003.Classes.FormatMillis;
import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView categoryCardTitle;
    private TextView categoryCardTotalTime;
    private Button categoryCardPlayButton;
    private ToggleButton categoryCardFavicon;
    private LinearLayout categoryCardLayout;
    private FormatMillis format = new FormatMillis();
    private Category category;
    private List<Category> categories;
    private Context context;
    private CategoryCardListener listener;


    public interface CategoryCardListener {
        void onFavoriteIconClicked(boolean newFavoriteValue, Category category);
    }

    CategoryViewHolder(View itemView, List<Category> categories, Context context, final CategoryCardListener listener) {
        super(itemView);
        this.listener = listener;
        this.categories = categories;
        this.context = context;
        categoryCardTitle = itemView.findViewById(R.id.category_card_title);
        categoryCardTotalTime = itemView.findViewById(R.id.category_card_total_time);
        categoryCardPlayButton = itemView.findViewById(R.id.category_card_play_button);
        categoryCardLayout = itemView.findViewById(R.id.card_layout);
        categoryCardFavicon = itemView.findViewById(R.id.category_card_favicon);

        itemView.setOnClickListener(this);
    }

    public void setCategory(final Category category) {
        this.category = category;
        categoryCardTitle.setText(category.getCategory());
        categoryCardTotalTime.setText(format.FormatMillisIntoHMS(category.getTotalTime()));
        categoryCardPlayButton.setText(R.string.play_button);
        if (category.isFavorite()) {
            categoryCardFavicon.setChecked(true);
        } else {
            categoryCardFavicon.setChecked(false);
        }


        final ScaleAnimation scaleAnimation =
                new ScaleAnimation(0.9f, 1.1f, 0.9f, 1.1f,
                        Animation.RELATIVE_TO_SELF, 0.7f,
                        Animation.RELATIVE_TO_SELF, 0.7f);

        scaleAnimation.setDuration(200);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        categoryCardFavicon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.startAnimation(scaleAnimation);

                if(isChecked) {
                    category.setFavorite(true);
                    listener.onFavoriteIconClicked(true, category);
                } else {
                    category.setFavorite(false);
                    listener.onFavoriteIconClicked(false, category);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        // Create category object to hold category
        Category currentCategory = categories.get(getAdapterPosition());

        //TODO: FIX THE INTENTS UP AND GET THE DAMN HOME WOK SHIT WORKING---- ALSO THE STARTTIME

        // Send category over and start the detail activity
        Intent detailIntent = new Intent(context, DetailActivity.class);
        detailIntent.putExtra("category_id", currentCategory.getId());
        detailIntent.putExtra("category_title", currentCategory.getCategory());
        detailIntent.putExtra("display_time", currentCategory.getDisplayTime());
        detailIntent.putExtra("timer_after_life", currentCategory.getTimeAfterLife());
        detailIntent.putExtra("is_running", currentCategory.isTimerRunning());

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, categoryCardLayout, ViewCompat.getTransitionName(categoryCardLayout));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(detailIntent, options.toBundle());
        }


    }
}

