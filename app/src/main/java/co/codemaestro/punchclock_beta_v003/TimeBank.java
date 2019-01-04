package co.codemaestro.punchclock_beta_v003;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;


/*@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = CASCADE))*/

@Entity(tableName = "timeBank_table", foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "categoryId", onDelete = CASCADE))
public class TimeBank {

    /**
     * Columns
      */

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "timeValue")
    private String timeValue;

    @ColumnInfo(name = "categoryId")
    private int categoryId;

    /**
     * Constructor
     * @param id
     * @param timeValue
     * @param categoryId
     */
    TimeBank(int id, String timeValue, int categoryId ){
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

    public String getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(String timeValue) {
        this.timeValue = timeValue;
    }

    public int getCategoryId() {
        return categoryId ;
    }

    public void setCategoryId(int categoryId ) {
        this.categoryId  = categoryId ;
    }
}
