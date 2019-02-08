package co.codemaestro.punchclock_beta_v003.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.R;

public class AddCategoryCardViewHolder extends RecyclerView.ViewHolder {
    private ToggleButton addCategoryButton;
    private AddCategoryCardListener listener;

    public interface AddCategoryCardListener {
        void addCategoryCardAction();
    }


    public AddCategoryCardViewHolder(@NonNull View itemView, final Context context, final AddCategoryCardListener listener) {
        super(itemView);
        addCategoryButton = itemView.findViewById(R.id.add_category_button);
        this.listener = listener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addCategoryCardAction();
                Toast.makeText(context, "Add Card Clicked", Toast.LENGTH_LONG).show();
            }
        });
    }


}
