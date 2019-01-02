package co.codemaestro.punchclock_beta_v003;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategories;
    private Category category;


    /**
     * @param application
     * Constructor for the Repository
     */
    CategoryRepository(Application application) {
        CategoryDatabase db = CategoryDatabase.getDatabase(application);
        categoryDao = db.categoryDao();
        allCategories = categoryDao.getAllCategories();
    }


    /**
     * Wrapper Methods
     */
    LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public void insert(Category category) {
        new insertAsyncTask(categoryDao).execute(category);
    }

    public void deleteAll() {
        new deleteAllAsyncTask(categoryDao).execute();
    }



    /**
     * AsyncTask Inner Classes
     */

    private static class insertAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao asyncTaskDao;

        insertAsyncTask(CategoryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            asyncTaskDao.insert(categories[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private CategoryDao asyncTaskDao;

        deleteAllAsyncTask(CategoryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAll();
            return null;
        }
    }

}
