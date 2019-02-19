package co.codemaestro.punchclock_beta_v003.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.codemaestro.punchclock_beta_v003.Classes.FormatMillis;
import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.R;

public class CategoryAdapter extends RecyclerView.Adapter {

    FormatMillis format = new FormatMillis();
    private List<Category> categories;
    private LayoutInflater inflater;
    private Context context;
    private CategoryViewHolder.CategoryCardListener categoryCardListener;
    private PlusCardViewHolder.PlusCardListener plusCardListener;

    private static final int CATEGORY_CARD_LAYOUT_TAG = 1;
    private static final int PLUS_CARD_LAYOUT_TAG = 2;


    public CategoryAdapter(Context context, CategoryViewHolder.CategoryCardListener categoryCardListener, PlusCardViewHolder.PlusCardListener plusCardListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.categoryCardListener = categoryCardListener;
        this.plusCardListener = plusCardListener;
        this.categories = new ArrayList<>();
    }

    // Passes the list of categories we received from the Observer
    //
    public void setCategories(final List<Category> categories) {
        this.categories = categories;
//        final List<Category> oldCategories = new ArrayList<>(this.categories);
//        this.categories.clear();
//        this.categories.addAll(categories);
//
//
//        DiffUtil.calculateDiff(new DiffUtil.Callback() {
//            @Override
//            public int getOldListSize() {
//                return oldCategories.size();
//            }
//
//            @Override
//            public int getNewListSize() {
//                return categories.size();
//            }
//
//            @Override
//            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//                return oldCategories.get(oldItemPosition).equals(categories.get(newItemPosition));
//            }
//
//            @Override
//            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//                return oldCategories.get(oldItemPosition).equals(categories.get(newItemPosition));
//            }
//        }).dispatchUpdatesTo(this);

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
            return PLUS_CARD_LAYOUT_TAG;
        } else {
            return CATEGORY_CARD_LAYOUT_TAG;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch(viewType) {
            case CATEGORY_CARD_LAYOUT_TAG:
                View view1 = inflater.inflate(R.layout.category_card, viewGroup, false);
                viewHolder = new CategoryViewHolder(view1, categories, context, categoryCardListener);
                break;
            case PLUS_CARD_LAYOUT_TAG:
                View view2 = inflater.inflate(R.layout.plus_card, viewGroup, false);
                viewHolder = new PlusCardViewHolder(view2, context, plusCardListener);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case CATEGORY_CARD_LAYOUT_TAG:
                CategoryViewHolder viewHolder1 = (CategoryViewHolder) viewHolder;
                viewHolder1.setCategory(categories.get(position));
                break;
            case PLUS_CARD_LAYOUT_TAG:
                PlusCardViewHolder viewHolder2 = (PlusCardViewHolder) viewHolder;
                break;
        }
    }
}
