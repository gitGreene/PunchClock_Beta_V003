package co.codemaestro.punchclock_beta_v003;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailTimeBankAdapter extends RecyclerView.Adapter<DetailTimeBankAdapter.DetailTimeBankHolder> {

    // Our data
    private List<TimeBank> timeBanks = new ArrayList<>();

    // Return a Holder itemView?
    @NonNull
    @Override
    public DetailTimeBankHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_row_activity_detail, viewGroup, false);
        return new DetailTimeBankHolder(itemView);
    }

    // Bind the data to each row
    @Override
    public void onBindViewHolder(@NonNull DetailTimeBankHolder detailTimeBankHolder, int position) {
        //Create a TimeBank object with the relevant position
        TimeBank currentTimeBank = timeBanks.get(position);
        //Use the holder to set the text to the correct timeValue in the database
        detailTimeBankHolder.textViewTime.setText(currentTimeBank.getTimeValue());
    }

    @Override
    public int getItemCount() {
        // Return the size of the data array
        return timeBanks.size();
    }

    public void setTimeBanks(List<TimeBank> timeBanks) {
        this.timeBanks = timeBanks;
        notifyDataSetChanged();
    }

    class DetailTimeBankHolder extends RecyclerView.ViewHolder {
        private TextView textViewTime;

        public DetailTimeBankHolder(@NonNull View itemView) {
            super(itemView);

            textViewTime = itemView.findViewById(R.id.textViewTime);
        }
    }

}
