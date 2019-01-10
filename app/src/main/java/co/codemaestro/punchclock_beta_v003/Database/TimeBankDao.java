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

    @Insert
    void insertTimeBank(TimeBank timeBank);

    @Update
    void updateTimeBank(TimeBank timeBank);

    @Delete
    void deleteTimeBank(TimeBank timeBank);

    @Query("SELECT * FROM timeBank_table")
    LiveData<List<TimeBank>> getAllTimeBanks();

    @Query("SELECT * FROM timeBank_table WHERE categoryId=:categoryId")
    LiveData<List<TimeBank>> getCategoryTimeBanks(final int categoryId);




}
