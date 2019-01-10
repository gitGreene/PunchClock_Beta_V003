package co.codemaestro.punchclock_beta_v003.Database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private TimeBankDao timeBankDao;

    private LiveData<List<Category>> allCategories;
    private LiveData<List<Category>> favorites;
    private LiveData<Category> categoryByTitle;
    private LiveData<List<TimeBank>> allTimeBanks;
    private LiveData<List<TimeBank>> categoryTimeBanks;

    /**
     * @param application
     * Constructor for the Repository
     */
    public CategoryRepository(Application application) {
        CategoryDatabase db = CategoryDatabase.getDatabase(application);
        categoryDao = db.categoryDao();
        timeBankDao = db.timeBankDao();
        allCategories = categoryDao.getAllCategories();
        allTimeBanks = timeBankDao.getAllTimeBanks();
    }


    /**
     * Wrapper Methods
     */

    /** Category Methods */
    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public LiveData<Category> getCategoryByTitle(String title) {
        categoryByTitle = categoryDao.getCategoryByTitle(title);
        return categoryByTitle;
    }

    public void insert(Category category) {
        new InsertAsyncTask(categoryDao).execute(category);
    }

    public void updateCategory(Category category) {
        new UpdateCategoryAsyncTask(categoryDao).execute(category);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(categoryDao).execute();
    }

    public LiveData<List<Category>> getFavorites() {
        favorites = categoryDao.getFavorites();
        return favorites;
    }

    public void setAsFavorite(int id) {
        new SetAsFavoriteAsync(categoryDao).execute(id);
    }

    /** TimeBank Methods */

    // Return every TimeBank
    public LiveData<List<TimeBank>> getAllTimeBanks() {
        return allTimeBanks;
    }

    // Only TimeBanks with a unique Id
    public LiveData<List<TimeBank>> getCategoryTimeBanks(int id){
        categoryTimeBanks = timeBankDao.getCategoryTimeBanks(id);
        return categoryTimeBanks;
    }

    // Sum of all times by category - Async
    public Long getCategoryTimeSum(int id) {
        Long categoryTotalTime = timeBankDao.getCategoryTimeSum(id);
        return categoryTotalTime;
    }

    // Insert - Async
    public void insertTimeBank(TimeBank timeBank) {
        new InsertTimeBankTask(timeBankDao).execute(timeBank);
    }

    // Update - Async - Todo: Implement?
    public void updateTimeBank(TimeBank timeBank) {
        new UpdateTimeBankTask(timeBankDao).execute(timeBank);
    }
    // Delete - Async - Todo: Where/how to implement?
    public void deleteTimeBank(TimeBank timeBank) {
        new DeleteTimeBankTask(timeBankDao).execute(timeBank);
    }


    //    Category getCategoryById(int id) throws ExecutionException, InterruptedException {
//        Category category = new getCategoryByIdAsync(categoryDao).execute(id).get();
//        return category;
//    }

    /**
     * AsyncTask Inner Classes
     */


    // Category Async Methods
    private static class InsertAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao asyncTaskDao;

        InsertAsyncTask(CategoryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            asyncTaskDao.insert(categories[0]);
            return null;
        }
    }

    private static class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao asyncTaskDao;

        UpdateCategoryAsyncTask(CategoryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            asyncTaskDao.updateCategory(categories[0]);
            return null;
        }
    }


    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private CategoryDao asyncTaskDao;

        DeleteAllAsyncTask(CategoryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class SetAsFavoriteAsync extends AsyncTask<Integer, Void, Void> {
        private CategoryDao categoryDao;

        public SetAsFavoriteAsync(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            categoryDao.setAsFavorite(integers[0]);
            return null;
        }
    }



    // TimeBank Async Methods
        // TODO: Make a async task or incorportate live data for getSumOfTimes
    /* private static class GetCategoryTimeSumAsyncTask extends AsyncTask<Int, Void, Long> {
        private TimeBankDao timeBankDao;

        private GetCategoryTimeSumAsyncTask(TimeBankDao timeBankDao) {

            this.timeBankDao = timeBankDao;
        }

         @Override
         protected Long doInBackground(Integer... integers) {
             long categoryTotalTime = timeBankDao.getCategoryTimeSum(integers);
             return categoryTotalTime;

         }
     } */


    private static class InsertTimeBankTask extends AsyncTask<TimeBank, Void, Void> {
        private TimeBankDao timeBankDao;

        private InsertTimeBankTask(TimeBankDao timeBankDao) {
            this.timeBankDao = timeBankDao;
        }

        @Override
        protected Void doInBackground(TimeBank... timeBanks) {
            timeBankDao.insertTimeBank(timeBanks[0]);
            return null;
        }
    }

    private static class UpdateTimeBankTask extends AsyncTask<TimeBank, Void, Void> {
        private TimeBankDao timeBankDao;

        private UpdateTimeBankTask(TimeBankDao timeBankDao) {
            this.timeBankDao = timeBankDao;
        }

        @Override
        protected Void doInBackground(TimeBank... timeBanks) {
            timeBankDao.updateTimeBank(timeBanks[0]);
            return null;
        }
    }

    private static class DeleteTimeBankTask extends AsyncTask<TimeBank, Void, Void> {
        private TimeBankDao timeBankDao;

        private DeleteTimeBankTask(TimeBankDao timeBankDao) {
            this.timeBankDao = timeBankDao;
        }

        @Override
        protected Void doInBackground(TimeBank... timeBanks) {
            timeBankDao.deleteTimeBank(timeBanks[0]);
            return null;
        }
    }




}
