package co.codemaestro.punchclock_beta_v003.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.R;

public class FavoritesAdapter extends RecyclerView.Adapter {

    private List<Category> categories;
    private LayoutInflater inflater;
    private Context context;
    private CategoryViewHolder.CategoryCardListener categoryCardListener;
    private PlusCardViewHolder.PlusCardListener plusCardListener;

    public FavoritesAdapter(Context context, CategoryViewHolder.CategoryCardListener categoryCardListener) {
        this.context = context;
        this.categoryCardListener = categoryCardListener;
        this.inflater = LayoutInflater.from(context);
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;

        // Notifies the adapter that the underlying data has changed
        // Therefore causing the Adapter to refresh
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;

        View view1 = inflater.inflate(R.layout.category_card, viewGroup, false);
        viewHolder = new CategoryViewHolder(view1, categories, context, categoryCardListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        CategoryViewHolder viewHolder1 = (CategoryViewHolder) viewHolder;
        viewHolder1.setCategory(categories.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (categories != null) {
            return categories.size();
        } else return 0;
    }
}
