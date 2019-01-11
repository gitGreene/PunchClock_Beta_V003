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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


import co.codemaestro.punchclock_beta_v003.Classes.FormatMillis;
import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.Activities.DetailActivity;
import co.codemaestro.punchclock_beta_v003.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    FormatMillis format = new FormatMillis();
    private final LayoutInflater inflater;
    private List<Category> categories;
    private Context context;

    public CategoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(categories != null) {
            return categories.size();
        } else return 0;
    }


    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, int position) {
        if(categories != null) {
            final Category current = categories.get(position);
            holder.timeBankTitleView.setText(current.getCategory());
            holder.timeBankValueView.setText(format.FormatMillisIntoHMS(current.getTotalTime()));
            holder.playButton.setText(R.string.play_button);
            holder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Make this open DetailActivity + Start Timer
                    Toast.makeText(context, "Damn son: " + current.getCategory(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.category_card, parent, false);

        return new CategoryViewHolder(itemView);
    }



    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView timeBankTitleView;
        private final TextView timeBankValueView;
        private final Button playButton;
        private final ConstraintLayout categoryCardTitle;


        private CategoryViewHolder(View itemView) {
            super(itemView);
            timeBankTitleView = itemView.findViewById(R.id.timeBankTitle);
            timeBankValueView = itemView.findViewById(R.id.timeBankValue);
            playButton = itemView.findViewById(R.id.playButton);
            categoryCardTitle = itemView.findViewById(R.id.category_title_container_card);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Create category object to hold category
            Category currentCategory = categories.get(getAdapterPosition());

            // Send category over and start the detail activity
            Intent detailIntent = new Intent(context, DetailActivity.class);
            detailIntent.putExtra("category_title", currentCategory.getCategory());
            detailIntent.putExtra("category_id", currentCategory.getId());
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, categoryCardTitle, ViewCompat.getTransitionName(categoryCardTitle));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                context.startActivity(detailIntent, options.toBundle());
            }


        }
    }
}
