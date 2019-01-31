package co.codemaestro.punchclock_beta_v003.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.codemaestro.punchclock_beta_v003.Classes.FormatMillis;
import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.R;

public class CategoryAdapter extends RecyclerView.Adapter {

    FormatMillis format = new FormatMillis();
    private List<Category> categories;
    private LayoutInflater inflater;
    private Context context;
    private CategoryViewHolder.CategoryCardListener listener;


    public CategoryAdapter(Context context, CategoryViewHolder.CategoryCardListener listener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
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
            return categories.size();
        } else return 0;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.category_card, viewGroup, false);
        return new CategoryViewHolder(view, categories, context, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        CategoryViewHolder vh = (CategoryViewHolder) viewHolder;
        vh.setCategory(categories.get(position));
    }


}
