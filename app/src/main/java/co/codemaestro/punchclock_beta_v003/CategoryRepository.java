package co.codemaestro.punchclock_beta_v003;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategories;


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

    Category getCategoryById(int id) throws ExecutionException, InterruptedException {
        Category category = new getCategoryByIdAsync(categoryDao).execute(id).get();
        return category;
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

    private static class getCategoryByIdAsync extends AsyncTask<Integer, Void, Category> {
        private CategoryDao asyncTaskDao;

        public getCategoryByIdAsync(CategoryDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Category doInBackground(Integer... integers) {
            Category category = asyncTaskDao.getCategoryById(integers[0]);
            return category;
        }

        @Override
        protected void onPostExecute(Category category) {
            return;
        }
    }

//    private static class setTimerRunningBooleanAsync extends AsyncTask<Void, Void, Void> {
//        private CategoryDao asyncTaskDao;
//
//        public setTimerRunningBooleanAsync(CategoryDao asyncTaskDao) {
//            this.asyncTaskDao = asyncTaskDao;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            asyncTaskDao.updateTimerRunningBoolean();
//        }
//    }

}
