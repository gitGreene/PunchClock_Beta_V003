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

    // Insert category
    @Insert
    void insert(Category category);

    // Delete all categories
    @Query("DELETE FROM category_table")
    void deleteAll();

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

    // Todo: Does this updateCategory overwrite every field even if we don't give it one?
    @Update
    void updateCategory(Category category);

    //
    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * from category_table WHERE isFavorite = 'true'")
    LiveData<List<Category>> getFavorites();

    @Query("UPDATE category_table SET isFavorite = 'true' WHERE id = :id")
    void setAsFavorite(int id);


}
