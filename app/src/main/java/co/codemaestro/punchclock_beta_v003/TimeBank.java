package co.codemaestro.punchclock_beta_v003;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//@Entity(tableName = "timeBank_table")
public class TimeBank {

    /**
     * Columns
      */

    //@PrimaryKey(autoGenerate = true)
    //@NonNull
    //@ColumnInfo(name = "timeId")
    private int timeId;

  //  @ColumnInfo(name = "timeValue")
    private String timeValue;

  //  @ColumnInfo(name = "id")
    private int id;

    /**
     * Constructor
     * @param timeId
     * @param timeValue
     * @param id
     */
    TimeBank(int timeId, String timeValue, int id){
        this.timeId = timeId;
        this.timeValue = timeValue;
        this.id = id;
    }

    /**
     * Getters and Setters
     */

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public String getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(String timeValue) {
        this.timeValue = timeValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }
}
