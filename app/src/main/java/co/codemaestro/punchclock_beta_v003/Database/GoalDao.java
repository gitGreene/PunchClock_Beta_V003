package co.codemaestro.punchclock_beta_v003.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GoalDao {

    @Insert
    void insertGoal(Goal goal);

    @Update
    void updateGoal(Goal goal);

    @Delete
    void deleteGoal(Goal goal);

    @Query("SELECT * FROM goals_table")
    LiveData<List<Goal>> getAllGoals();

    @Query("SELECT * FROM goals_table WHERE goalId =:goalId")
    LiveData<List<Goal>> getGoalById(int goalId);

    @Query("SELECT * FROM goals_table WHERE parentCategoryId =:parentCategoryId")
    LiveData<List<Goal>> getAllGoalsByParentId(int parentCategoryId);

    @Query("SELECT * FROM goals_table WHERE parentCategoryId =:parentCategoryId")
    Goal getGoalbyParentId(int parentCategoryId);

}
