package co.codemaestro.punchclock_beta_v003;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "category_table")
public class Category {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo
    private String timeValue;

//    @Ignore
//    public Category(int id, @NonNull String category) {
//        this.id = id;
//        this.category = category;
//    }

    @Ignore
    public Category(@NonNull String category, String timeValue) {
        this.category = category;
        this.timeValue = timeValue;
    }

    @Ignore
    public Category(@NonNull String category) {
        this.category = category;
    }

    public Category(int id, @NonNull String category, String timeValue) {
        this.id = id;
        this.category = category;
        this.timeValue = timeValue;
    }



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
}



