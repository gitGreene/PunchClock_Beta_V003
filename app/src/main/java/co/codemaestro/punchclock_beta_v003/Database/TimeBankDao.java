package co.codemaestro.punchclock_beta_v003.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TimeBankDao {

    // Insert
    @Insert
    void insertTimeBank(TimeBank timeBank);

    // Update
    @Update
    void updateTimeBank(TimeBank timeBank);

    // Delete
    @Delete
    void deleteTimeBank(TimeBank timeBank);

    // Get all timebanks as observable livedata
    @Query("SELECT * FROM timeBank_table")
    LiveData<List<TimeBank>> getAllTimeBanks();

    // Get all timebanks by categoryId as observable livedata
    @Query("SELECT * FROM timeBank_table WHERE categoryId=:categoryId")
    LiveData<List<TimeBank>> getCategoryTimeBanks(final int categoryId);

    // Get the sum of all timebanks with a particular categoryId as observable livedata
    @Query("SELECT SUM(timeValue)+0 FROM timeBank_table WHERE categoryId=:categoryId")
    LiveData<Long> getCategoryTimeSum(final int categoryId);




}
