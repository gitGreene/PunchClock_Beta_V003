package co.codemaestro.punchclock_beta_v003.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.Nullable;

import java.util.List;

@Dao
public interface CategoryDao {

    // Insert category
    @Insert
    void insert(Category category);
    //
    @Query("SELECT * from category_table")
    LiveData<List<Category>> getAllCategories();

    //
    @Query("SELECT * from category_table LIMIT 1")
    Category[] getAnyCategory();

    //
    @Query("SELECT * from category_table WHERE id = :categoryId")
    Category getCategoryById(int categoryId);

    //
    @Query("SELECT * from category_table WHERE category = :categoryTitle")
    LiveData<Category> getCategoryByTitle(String categoryTitle);

    @Update
    void updateCategory(Category category);

    @Query("SELECT * from category_table WHERE isFavorite")
    LiveData<List<Category>> getFavorites();
}
