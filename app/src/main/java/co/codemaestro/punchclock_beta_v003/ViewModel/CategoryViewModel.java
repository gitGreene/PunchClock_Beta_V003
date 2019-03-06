package co.codemaestro.punchclock_beta_v003.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import co.codemaestro.punchclock_beta_v003.Database.Category;
import co.codemaestro.punchclock_beta_v003.Database.CategoryRepository;
import co.codemaestro.punchclock_beta_v003.Database.Goal;
import co.codemaestro.punchclock_beta_v003.Database.TimeBank;



public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository repository;

    // Category variables
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Category>> favorites;
    private LiveData<Category> category;

    //Timebank variables
    private LiveData<List<TimeBank>> allTimeBanks;
    private LiveData<List<TimeBank>> categoryTimeBanks;

    //Goal variables
    private LiveData<List<Goal>> allCategoryGoals;
    private LiveData<Goal> goal;


    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new CategoryRepository(application);
        allCategories = repository.getAllCategories();
        allTimeBanks = repository.getAllTimeBanks();
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

    public LiveData<List<Category>> getFavorites() {
        favorites = repository.getFavorites();
        return favorites;
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

    // Insert TimeBanks
    public void insertTimeBank(TimeBank timeBank) {
        repository.insertTimeBank(timeBank);
    }

    /**
     * Goal Methods
     */
    public void insertGoal(Goal goal) {
        repository.insertGoal(goal);
    }

    public LiveData<List<Goal>> getAllCategoryGoals(int parentCategoryId) {
        allCategoryGoals = repository.getAllCategoryGoals(parentCategoryId);
        return allCategoryGoals;
    }
}