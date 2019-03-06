package co.codemaestro.punchclock_beta_v003.Adapters;

import android.app.Activity;
import android.arch.persistence.room.Ignore;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import java.util.List;

import co.codemaestro.punchclock_beta_v003.Activities.DetailActivity;
import co.codemaestro.punchclock_beta_v003.Activities.MainActivity;
import co.codemaestro.punchclock_beta_v003.Classes.FormatMillis;
import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView categoryCardTitle;
    private TextView categoryCardTimer, categoryCardTotalTime;
    private ProgressBar progressBar;
    private Button categoryCardPlayButton, categoryCardPauseButton, categoryCardResetButton, categoryCardCommitButton;
    private ToggleButton categoryCardFavicon;
    private LinearLayout categoryCardLayout;
    private FormatMillis form = new FormatMillis();
    private Category category;
    private List<Category> categories;
    private Context context;
    private CategoryCardListener listener;
    private UltimateRunnable ultimateRunnable;
    private Handler handler = new Handler();


    public interface CategoryCardListener {
        void onCardAction(Category category);
    }

    CategoryViewHolder(View itemView, List<Category> categories, Context context, final CategoryCardListener listener) {
        super(itemView);
        this.listener = listener;
        this.categories = categories;
        this.context = context;
        categoryCardTitle = itemView.findViewById(R.id.category_card_title);
        categoryCardTimer = itemView.findViewById(R.id.category_card_timer);
        categoryCardTotalTime = itemView.findViewById(R.id.category_card_total_time);
        categoryCardPlayButton = itemView.findViewById(R.id.category_card_play_button);
        categoryCardPauseButton = itemView.findViewById(R.id.category_card_pause_button);
        categoryCardResetButton = itemView.findViewById(R.id.category_card_reset_button);
        categoryCardCommitButton = itemView.findViewById(R.id.category_card_commit_button);
        categoryCardLayout = itemView.findViewById(R.id.card_layout);
        categoryCardFavicon = itemView.findViewById(R.id.detail_activity_favicon);
        ultimateRunnable = new UltimateRunnable(handler, categoryCardTimer, SystemClock.elapsedRealtime());
        itemView.setOnClickListener(this);
    }

    public void setCategory(final Category category, final int position) {
        // Stop runnable and get currentCategory
        if (ultimateRunnable != null) {
            handler.removeCallbacks(ultimateRunnable);
        }

        this.category = category;
        categoryCardTitle.setText(category.getCategory());
        categoryCardTimer.setText(form.FormatMillisIntoHMS(category.getDisplayTime()));
        categoryCardTotalTime.setText(form.FormatMillisIntoDHM(category.getTotalTime()));

        // If timer was running before, start a new one
        if (category.isTimerRunning()) {
            ultimateRunnable.holderTV = categoryCardTimer;
            long timeAfterLife = SystemClock.elapsedRealtime() - category.getTimeAtDeath();
            ultimateRunnable.initialTime = SystemClock.elapsedRealtime() - category.getDisplayTime() - timeAfterLife;
            ultimateRunnable.displayResultToLog = category.getCategory();
            ultimateRunnable.currentCategory = category;
            ultimateRunnable.position = position;
            handler.postDelayed(ultimateRunnable, 100);
            StartEnabledButtons();
        } else {
            if (category.getDisplayTime() > 0) {
                PauseEnabledButtons();
            } else {
                DefaultEnabledButtons();
            }
        }

        if (category.isFavorite()) {
            categoryCardFavicon.setChecked(true);
        } else {
            categoryCardFavicon.setChecked(false);
        }

        final ScaleAnimation cardIconScaleAnimation =
                new ScaleAnimation(0.9f, 1.1f, 0.9f, 1.1f,
                        Animation.RELATIVE_TO_SELF, 0.7f,
                        Animation.RELATIVE_TO_SELF, 0.7f);

        cardIconScaleAnimation.setDuration(200);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        cardIconScaleAnimation.setInterpolator(bounceInterpolator);

        categoryCardFavicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardFavicon.startAnimation(cardIconScaleAnimation);

                if(categoryCardFavicon.isChecked()) {
                    category.setFavorite(true);
                    listener.onCardAction(category);
                } else {
                    category.setFavorite(false);
                    listener.onCardAction(category);
                }
            }
        });

        //TODO: Make the progress bar work - https://stackoverflow.com/questions/16893209/how-to-customize-a-progress-bar-in-android


        categoryCardPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartEnabledButtons();
                category.setTimerRunning(true);
                categories.set(position, category);

                ultimateRunnable.holderTV = categoryCardTimer;
                ultimateRunnable.initialTime = SystemClock.elapsedRealtime() - category.getDisplayTime();
                ultimateRunnable.displayResultToLog = category.getCategory();
                ultimateRunnable.currentCategory = category;
                ultimateRunnable.position = position;
                handler.postDelayed(ultimateRunnable, 100);

                // Update category
                listener.onCardAction(category);
            }
        });

        categoryCardPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PauseEnabledButtons();
                handler.removeCallbacks(ultimateRunnable);
                category.setTimerRunning(false);
                categories.set(position, category);

                // Update category and redraw card
                listener.onCardAction(category);
            }
        });

        categoryCardResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DefaultEnabledButtons();
                category.setTimerRunning(false);
                category.setDisplayTime(0);
                category.setTimeAtDeath(0);
                categoryCardTimer.setText(R.string.default_timer);
                categories.set(position, category);

                // Update category and redraw card
                listener.onCardAction(category);
            }
        });

        categoryCardCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DefaultEnabledButtons();
            }
        });
    }

    @Override
    public void onClick(View v) {
        // Kill the timer threads
        clearAll();
        // Create category object to hold category
        Intent detailIntent = new Intent(context, DetailActivity.class);
        detailIntent.putExtra("category", category);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, categoryCardLayout, ViewCompat.getTransitionName(categoryCardLayout));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(detailIntent, options.toBundle());
        }
    }

    /**
     * UltimateRunnable
     */

    public void clearAll() {
        handler.removeCallbacksAndMessages(null);
    }

    // Runnable that updates the timerView text, the value in categories and the category database table every tick
    public class UltimateRunnable implements Runnable {

        private Handler handler;
        private TextView holderTV;
        private long initialTime;
        private Category currentCategory;
        private int position;
        private long displayMillis;
        private String displayResultToLog;
        private FormatMillis form = new FormatMillis();
        private final static String TAG = "CustomRunnable";

        @Ignore
        public UltimateRunnable(Handler handler, TextView holderTV, long initialTime) {
            this.handler = handler;
            this.holderTV = holderTV;
            this.initialTime = initialTime;
        }

        //Unused
        public UltimateRunnable(Handler handler, TextView holderTV, long initialTime, Category currentCategory, int position) {
            this.handler = handler;
            this.holderTV = holderTV;
            this.initialTime = initialTime;
            this.currentCategory = currentCategory;
            this.position = position;
        }

        @Override
        public void run() {
            //android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            displayMillis = SystemClock.elapsedRealtime() - initialTime;
            holderTV.setText(form.FormatMillisIntoHMS(displayMillis));
            currentCategory.setDisplayTime(displayMillis);
            currentCategory.setTimeAtDeath(SystemClock.elapsedRealtime());
            categories.set(position, currentCategory);
            listener.onCardAction(currentCategory);

            Log.e(TAG, "CustomRunnable--" + displayResultToLog + " DisplayTime: " + form.FormatMillisIntoHMS(SystemClock.elapsedRealtime() - initialTime));
            handler.postDelayed(this, 1000);
        }
    }

    /**
     * SetEnabled Timer Button Methods - Three states of buttons
     */

    public void StartEnabledButtons() {
        // timer Started
        categoryCardPlayButton.setEnabled(false);
        categoryCardPauseButton.setEnabled(true);
        categoryCardResetButton.setEnabled(false);
        categoryCardCommitButton.setEnabled(false);
    }

    public void PauseEnabledButtons() {
        // timer Paused
        categoryCardPlayButton.setEnabled(true);
        categoryCardPauseButton.setEnabled(false);
        categoryCardResetButton.setEnabled(true);
        categoryCardCommitButton.setEnabled(true);
    }

    public void DefaultEnabledButtons() {
        // timer Reset
        categoryCardPlayButton.setEnabled(true);
        categoryCardPauseButton.setEnabled(false);
        categoryCardResetButton.setEnabled(false);
        categoryCardCommitButton.setEnabled(false);
    }
}

