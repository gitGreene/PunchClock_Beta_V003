package co.codemaestro.punchclock_beta_v003;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository repository;

    //
    private LiveData<List<Category>> allCategories;
    private LiveData<Category> category;
    private LiveData<List<TimeBank>> allTimeBanks;
    private LiveData<List<TimeBank>> categoryTimeBanks;




    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new CategoryRepository(application);
        allCategories = repository.getAllCategories();
        allTimeBanks = repository.getAllTimeBanks();
    }


    /** Category Methods */

    /** Category */
    LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    LiveData<Category> getCategoryByTitle(String title) {
        category = repository.getCategoryByTitle(title);
        return category;
    }

    public void insert(Category category) {
        repository.insert(category);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    /** TimeBank Methods */

    // Get all TimeBanks
    LiveData<List<TimeBank>> getAllTimeBanks() {
        return allTimeBanks;
    }

    // Get all TimeBanks with unique categoryId
    LiveData<List<TimeBank>> getCategoryTimeBanks(int id) {
        categoryTimeBanks = repository.getCategoryTimeBanks(id);
        return categoryTimeBanks;
    }

    // Insert TimeBanks
    public void insertTimeBank(TimeBank timeBank) {
        repository.insertTimeBank(timeBank);
    }



//    Category getCategoryById(int id) throws ExecutionException, InterruptedException {
//        return repository.getCategoryById(id);
//    }
//    public void updateTimerRunningBoolean(Category category) {
//        repository.updateTimerRunningBoolean();
//    }
}
