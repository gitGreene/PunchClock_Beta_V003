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

    @ColumnInfo(name = "displayTime")
    private long displayTime;

    @ColumnInfo(name = "timeAfterLife")
    private long timeAfterLife;

    @ColumnInfo(name = "startTime")
    private String startTime;

    @ColumnInfo(name = "timerRunning")
    private boolean timerRunning;

    @ColumnInfo(name = "isFavorite")
    private boolean isFavorite;

    /**
     * Constructors
     */

    @Ignore
    public Category(String category, long totalTime, long displayTime, long timeAfterLife, String startTime, boolean timerRunning, boolean isFavorite) {
        this.category = category;
        this.totalTime = totalTime;
        this.displayTime = displayTime;
        this.timeAfterLife = timeAfterLife;
        this.timerRunning = timerRunning;
        this.startTime = startTime;
        this.isFavorite = isFavorite;

    }

    public Category(int id, @NonNull String category, long totalTime, long displayTime, long timeAfterLife, String startTime, boolean timerRunning, boolean isFavorite) {
        this.id = id;
        this.category = category;
        this.totalTime = totalTime;
        this.displayTime = displayTime;
        this.timeAfterLife = timeAfterLife;
        this.startTime = startTime;
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

    //displayTime
    public long getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(long displayTime) {
        this.displayTime = displayTime;
    }

    //timeAfterLife
    public long getTimeAfterLife() {
        return timeAfterLife;
    }

    public void setTimeAfterLife(long timeAfterLife) {
        this.timeAfterLife = timeAfterLife;
    }

    //startTime
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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



