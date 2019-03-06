package co.codemaestro.punchclock_beta_v003.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.Database.Goal;
import co.codemaestro.punchclock_beta_v003.R;

public class GoalAdapter extends RecyclerView.Adapter {

    private List<Goal> goals;
    private LayoutInflater inflater;
    private Context context;
    private static final String TAG = "GoalAdapter";

    public GoalAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.goals = new ArrayList<>();
    }

    public void setGoalCard(final List<Goal> goals) {
        this.goals = goals;
        notifyDataSetChanged();
        Log.e(TAG, "setGoalCard: Called" );
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = inflater.inflate(R.layout.goal_card, viewGroup, false);
        viewHolder = new GoalViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        GoalViewHolder viewHolder1 = (GoalViewHolder) viewHolder;
        viewHolder1.setGoalCard(goals.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
