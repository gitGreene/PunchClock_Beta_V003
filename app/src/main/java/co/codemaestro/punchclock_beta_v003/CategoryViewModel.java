package co.codemaestro.punchclock_beta_v003;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository repository;
    private LiveData<List<Category>> allCategories;
    private LiveData<Category> category;


    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new CategoryRepository(application);
        allCategories = repository.getAllCategories();
    }

    LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    LiveData<Category> getCategoryByTitle(String title) {
        category = repository.getCategoryByTitle(title);
        return category;
    }

//    Category getCategoryById(int id) throws ExecutionException, InterruptedException {
//        return repository.getCategoryById(id);
//    }

    public void insert(Category category) {
        repository.insert(category);
    }

//    public void updateTimerRunningBoolean(Category category) {
//        repository.updateTimerRunningBoolean();
//    }

    public void deleteAll() {
        repository.deleteAll();
    }


}
