package co.codemaestro.punchclock_beta_v003.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.Database.CategoryRepository;
import co.codemaestro.punchclock_beta_v003.Database.TimeBank;



public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository repository;

    // Category variables
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Category>> favorites;
    private LiveData<Category> category;

    private String categoryTitle;

    //Timebank variables
    private LiveData<List<TimeBank>> allTimeBanks;
    private LiveData<List<TimeBank>> categoryTimeBanks;
    private LiveData<Long> categoryTimeSum;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new CategoryRepository(application);
        allCategories = repository.getAllCategories();
        allTimeBanks = repository.getAllTimeBanks();
    }


    /** Category Methods */

    /** Category */

    // TODO: test 1
    public void testGetCategoryTitleString(TextView textView, int id) {
        repository.testGetCategoryTitleString(textView, id);
    }

    // TODO: test 2
    public void setCategoryTitle(int id) {
        repository.setCategoryTitle(categoryTitle, id);
    }

    // TODO: test 3
    public String returnCategoryTitle() {
        return categoryTitle;
    }


    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public LiveData<Category> getCategoryByTitle(String title) {
        category = repository.getCategoryByTitle(title);
        return category;
    }

    public void insert(Category category) {
        repository.insert(category);
    }

    public void updateCategory(Category category) {
        repository.updateCategory(category);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<Category>> getFavorites() {
        favorites = repository.getFavorites();
        return favorites;
    }

    public void setAsFavorite(int id) {
        repository.setAsFavorite(id);
    }

    public void setAsNotFavorite(int id){
        repository.setAsNotFavorite(id);
    }

    public LiveData<Boolean> checkIfFavorite(int id) {
        return repository.checkIfFavorite(id);
    }


    /** TimeBank Methods */

    // Get all TimeBanks
    public LiveData<List<TimeBank>> getAllTimeBanks() {
        return allTimeBanks;
    }

    // Get all TimeBanks with unique categoryId
    public LiveData<List<TimeBank>> getCategoryTimeBanks(int id) {
        categoryTimeBanks = repository.getCategoryTimeBanks(id);
        return categoryTimeBanks;
    }
    // Get sum of all times by category
    public LiveData<Long> getCategoryTimeSum(int id) {
        categoryTimeSum = repository.getCategoryTimeSum(id);
        return categoryTimeSum;
    }

    // Insert TimeBanks
    public void insertTimeBank(TimeBank timeBank) {
        repository.insertTimeBank(timeBank);
    }

}
