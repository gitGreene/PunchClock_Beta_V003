package co.codemaestro.punchclock_beta_v003.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void insert(Category category);

    @Query("DELETE FROM category_table")
    void deleteAll();

    @Query("SELECT * from category_table")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * from category_table LIMIT 1")
    Category[] getAnyCategory();

    @Query("SELECT * from category_table WHERE id = :currentId")
    Category getCategoryById(int currentId);

    @Query("SELECT * from category_table WHERE category = :categoryTitle")
    LiveData<Category> getCategoryByTitle(String categoryTitle);

//    @Update
//    void updateTimerRunningBoolean(Category category);

    @Delete
    void deleteCategory(Category category);

    @Update
    void updateTimeValue(Category category);


}
