package co.codemaestro.punchclock_beta_v003.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import co.codemaestro.punchclock_beta_v003.R;

public class PlusCardViewHolder extends RecyclerView.ViewHolder {
    private ToggleButton addCategoryButton;
    private PlusCardListener plusCardListener;

    public interface PlusCardListener {
        void onPlusCardAction();
    }


    public PlusCardViewHolder(@NonNull View itemView, final Context context, final PlusCardListener plusCardListener) {
        super(itemView);
        this.addCategoryButton = itemView.findViewById(R.id.add_category_button);
        this.plusCardListener = plusCardListener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusCardListener.onPlusCardAction();
                Toast.makeText(context, "Plus Card Clicked", Toast.LENGTH_LONG).show();
            }
        });
    }


}
