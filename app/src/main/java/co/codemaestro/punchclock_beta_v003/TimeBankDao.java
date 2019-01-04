package co.codemaestro.punchclock_beta_v003;

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
    void insert(TimeBank timeBank);

    @Update
    void update(TimeBank timeBank);

    @Delete
    void delete(TimeBank timeBank);

    @Query("SELECT * FROM timeBank_table")
    LiveData<List<TimeBank>> getAllTimeBankRows();

    // Try this one out
    //@Query("SELECT * FROM category_table WHERE id=:id")
    //List<TimeBank> findRepositoriesForUser(final int id);




}
