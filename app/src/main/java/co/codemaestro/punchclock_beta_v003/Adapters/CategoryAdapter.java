package co.codemaestro.punchclock_beta_v003.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import co.codemaestro.punchclock_beta_v003.Classes.FormatMillis;
import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.Fragments.AddCategoryFragment;
import co.codemaestro.punchclock_beta_v003.R;

public class CategoryAdapter extends RecyclerView.Adapter {

    FormatMillis format = new FormatMillis();
    private List<Category> categories;
    private LayoutInflater inflater;
    private Context context;
    private CategoryViewHolder.CategoryCardListener listener;
    private AddCategoryCardViewHolder.AddCategoryCardListener listener2;

    private static final int CARD_LAYOUT_ONE = 1;
    private static final int CARD_LAYOUT_TWO = 2;


    public CategoryAdapter(Context context, CategoryViewHolder.CategoryCardListener listener, AddCategoryCardViewHolder.AddCategoryCardListener listener2) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.listener2 = listener2;
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
        if (categories != null) {
            return categories.size()+1;
        } else return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == categories.size()) {
            return CARD_LAYOUT_TWO;
        } else {
            return CARD_LAYOUT_ONE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflator = LayoutInflater.from(context);
        switch(viewType) {
            case CARD_LAYOUT_ONE:
                View view1 = inflater.inflate(R.layout.category_card, viewGroup, false);
                viewHolder = new CategoryViewHolder(view1, categories, context, listener);
                break;
            case CARD_LAYOUT_TWO:
                View view2 = inflater.inflate(R.layout.add_category_card, viewGroup, false);
                viewHolder = new AddCategoryCardViewHolder(view2, context, listener2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case CARD_LAYOUT_ONE:
                CategoryViewHolder viewHolder1 = (CategoryViewHolder) viewHolder;
                viewHolder1.setCategory(categories.get(position));
                break;
            case CARD_LAYOUT_TWO:
                AddCategoryCardViewHolder viewHolder2 = (AddCategoryCardViewHolder) viewHolder;
                break;
        }
    }



}
