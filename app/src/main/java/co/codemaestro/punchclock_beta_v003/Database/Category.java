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

    @ColumnInfo(name = "currentTime")
    private long currentTime;

    @ColumnInfo(name = "timeAfterLife")
    private long timeAfterLife;

    @ColumnInfo(name = "timerRunning")
    private boolean timerRunning;

    @ColumnInfo(name = "isFavorite")
    private boolean isFavorite;

    /**
     * Constructors
     */


    @Ignore
    public Category(String category, long totalTime, long currentTime, long timeAfterLife, boolean timerRunning, boolean isFavorite) {
        this.category = category;
        this.totalTime = totalTime;
        this.currentTime = currentTime;
        this.timeAfterLife = timeAfterLife;
        this.timerRunning = timerRunning;
        this.isFavorite = isFavorite;

    }

    public Category(int id, @NonNull String category, long totalTime, long currentTime, long timeAfterLife, boolean timerRunning, boolean isFavorite) {
        this.id = id;
        this.category = category;
        this.totalTime = totalTime;
        this.currentTime = currentTime;
        this.timeAfterLife = timeAfterLife;
        this.timerRunning = timerRunning;
        this.isFavorite = isFavorite;

    }

    /**
     * Getters and Setters
     */

    //id
    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    //category
    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    //totalTime
    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    //currentTime
    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    //timeAfterLife
    public long getTimeAfterLife() {
        return timeAfterLife;
    }

    public void setTimeAfterLife(long timeAfterLife) {
        this.timeAfterLife = timeAfterLife;
    }

    //timerRunning
    public boolean isTimerRunning() {
        return timerRunning;
    }

    public void setTimerRunning(boolean timerRunning) {
        this.timerRunning = timerRunning;
    }

    //isFavorite
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

}



