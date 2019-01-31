package co.codemaestro.punchclock_beta_v003.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.codemaestro.punchclock_beta_v003.Classes.FormatMillis;
import co.codemaestro.punchclock_beta_v003.Database.TimeBank;
import co.codemaestro.punchclock_beta_v003.R;

public class DetailTimeBankAdapter extends RecyclerView.Adapter<DetailTimeBankAdapter.DetailTimeBankHolder> {


    // FormatMillis object
    FormatMillis form = new FormatMillis();

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
        //Use the holder to set the text to the correct values in database, correctly formatting the time
        detailTimeBankHolder.textViewStartTime.setText(currentTimeBank.getStartTime());
        detailTimeBankHolder.textViewEndTime.setText(currentTimeBank.getEndTime());
        detailTimeBankHolder.textViewTime.setText(form.FormatMillisIntoHMS(currentTimeBank.getTimeValue()));
        detailTimeBankHolder.textViewDate.setText(currentTimeBank.getDate());
    }

    @Override
    public int getItemCount() {
        // Return the size of the data array
        return timeBanks.size();
    }

    public void setTimeBanks(List<TimeBank> timeBanks) {
        this.timeBanks = timeBanks;

        // Notifies the adapter that the underlying data has changed
        // Therefore causing the Adapter to refresh
        notifyDataSetChanged();
    }

    class DetailTimeBankHolder extends RecyclerView.ViewHolder {
        private TextView textViewStartTime, textViewEndTime, textViewTime, textViewDate;

        public DetailTimeBankHolder(@NonNull View itemView) {
            super(itemView);
            textViewStartTime = itemView.findViewById(R.id.textViewStartTime);
            textViewEndTime = itemView.findViewById(R.id.textViewEndTime);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }

}

