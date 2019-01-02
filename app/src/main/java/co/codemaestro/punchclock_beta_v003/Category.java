package co.codemaestro.punchclock_beta_v003;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "category_table")
public class Category {


    /**
     * Column Names
     */

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "time value")
    private String timeValue;

    @ColumnInfo(name = "timerRunning")
    private boolean timerRunning;


    /**
     * Constructors
     */

    @Ignore
    public Category(@NonNull String category) {
        this.category = category;
    }

    @Ignore
    public Category(@NonNull String category, String timeValue) {
        this.category = category;
        this.timeValue = timeValue;
    }



    public Category(int id, @NonNull String category, String timeValue) {
        this.id = id;
        this.category = category;
        this.timeValue = timeValue;
    }

    /**
     * Getters and Setters
     */

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    public String getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(String timeValue) {
        this.timeValue = timeValue;
    }

    public boolean isTimerRunning() {
        return timerRunning;
    }

    public void setTimerRunning(boolean timerRunning) {
        this.timerRunning = timerRunning;
    }

}



