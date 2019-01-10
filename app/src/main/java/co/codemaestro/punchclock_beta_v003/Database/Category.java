package co.codemaestro.punchclock_beta_v003.Database;

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

    @ColumnInfo(name = "totalTime")
    private long totalTime;

    @ColumnInfo(name = "timerRunning")
    private boolean timerRunning;

    @ColumnInfo(name = "isFavorite")
    private boolean isFavorite;

    /**
     * Constructors
     */

    @Ignore
    public Category(@NonNull String category) {
        this.category = category;
    }

    @Ignore
    public Category(@NonNull String category, long totalTime) {
        this.category = category;
        this.totalTime = totalTime;

    }

    public Category(int id, @NonNull String category, long totalTime) {
        this.id = id;
        this.category = category;
        this.totalTime = totalTime;

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

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public boolean isTimerRunning() {
        return timerRunning;
    }

    public void setTimerRunning(boolean timerRunning) {
        this.timerRunning = timerRunning;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

}



