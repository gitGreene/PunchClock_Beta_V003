package co.codemaestro.punchclock_beta_v003.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "timeBank_table",
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "id",
                childColumns = "categoryId",
                onDelete = CASCADE))

public class TimeBank {

    /**
     * Columns
      */

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "timeValue")
    private long timeValue;

    @ColumnInfo(name = "categoryId")
    private int categoryId;

    /**
     * Constructor
     *
     */

    @Ignore
    public TimeBank(long timeValue) {
        this.timeValue = timeValue;
    }

    @Ignore
    public TimeBank(long timeValue, int categoryId) {
        this.timeValue = timeValue;
        this.categoryId = categoryId;
    }


    public TimeBank(int id, long timeValue, int categoryId ){
        this.id = id;
        this.timeValue = timeValue;
        this.categoryId = categoryId ;
    }

    /**
     * Getters and Setters
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(long timeValue) {
        this.timeValue = timeValue;
    }

    public int getCategoryId() {
        return categoryId ;
    }

    public void setCategoryId(int categoryId ) {
        this.categoryId  = categoryId ;
    }
}
