package co.codemaestro.punchclock_beta_v003.Database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "goals_table",
        foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "parentCategoryId", onDelete = CASCADE),
        indices = @Index("goalId"))
public class Goal {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "goalId")
    private int goalId;

    @ColumnInfo(name = "parentCategoryId")
    private int parentCategoryId;

    @ColumnInfo(name = "goalName")
    private String goalName;

    @ColumnInfo(name = "timeSpent")
    private Long timeSpent;

    @ColumnInfo(name = "desiredGoalTime")
    private Long desiredGoalTime;

    @ColumnInfo(name = "goalCycleValue")
    private String goalCycleValue;




    /**
     * Constructor for a Goal
     * Takes the follow parameters for initialization
     * @param goalId ID of this goal
     * @param parentCategoryId ID of the parent Category
     * @param goalName Name of this goal
     * @param timeSpent Time committed towards this goal during a given goal cycle.
     * @param desiredGoalTime Time user wants to achieve during a given goal cycle.
     * @param goalCycleValue 24hrs, 7 days, 14 days, 30 days, etc.
     */

    public Goal(int goalId, int parentCategoryId, String goalName, Long timeSpent, Long desiredGoalTime, String goalCycleValue) {
        this.goalId = goalId;
        this.parentCategoryId = parentCategoryId;
        this.goalName = goalName;
        this.timeSpent = timeSpent;
        this.desiredGoalTime = desiredGoalTime;
        this.goalCycleValue = goalCycleValue;
    }

    /**
     * Alternate Constructors
     */


    @Ignore
    public Goal(int goalId) {
        this.goalId = goalId;
    }

    @Ignore
    public Goal(String goalName) {
        this.goalName = goalName;
    }

    /**
     * Getters and Setters for a Goal's Values
     */

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public Long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Long getDesiredGoalTime() {
        return desiredGoalTime;
    }

    public void setDesiredGoalTime(Long desiredGoalTime) {
        this.desiredGoalTime = desiredGoalTime;
    }

    public String getGoalCycleValue() {
        return goalCycleValue;
    }

    public void setGoalCycleValue(String goalCycleValue) {
        this.goalCycleValue = goalCycleValue;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
