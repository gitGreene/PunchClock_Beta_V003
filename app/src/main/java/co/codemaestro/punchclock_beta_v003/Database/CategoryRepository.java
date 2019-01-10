package co.codemaestro.punchclock_beta_v003.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private TimeBankDao timeBankDao;

    private LiveData<List<Category>> allCategories;
    private LiveData<Category> categoryByTitle;
    private LiveData<List<TimeBank>> allTimeBanks;
    private LiveData<List<TimeBank>> categoryTimeBanks;

    /**
     * @param application
     * Constructor for the Repository
     */
    public CategoryRepository(Application application) {
        //
        CategoryDatabase db = CategoryDatabase.getDatabase(application);
        categoryDao = db.categoryDao();
        timeBankDao = db.timeBankDao();

        //
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
        new insertAsyncTask(categoryDao).execute(category);
    }

    public void deleteAll() {
        new deleteAllAsyncTask(categoryDao).execute();
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

    // Insert
    public void insertTimeBank(TimeBank timeBank) {
        new InsertTimeBankTask(timeBankDao).execute(timeBank);
    }

    // Update - Todo: Implement?
    public void updateTimeBank(TimeBank timeBank) {
        new UpdateTimeBankTask(timeBankDao).execute(timeBank);
    }

    // Delete - Todo: Where/how to implement?
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


    // TimeBank Async Methods

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



//    private static class getCategoryByIdAsync extends AsyncTask<Integer, Void, Category> {
//        private CategoryDao asyncTaskDao;
//
//        public getCategoryByIdAsync(CategoryDao asyncTaskDao) {
//            this.asyncTaskDao = asyncTaskDao;
//        }
//
//        @Override
//        protected Category doInBackground(Integer... integers) {
//            Category category = asyncTaskDao.getCategoryById(integers[0]);
//            return category;
//        }
//
//        @Override
//        protected void onPostExecute(Category category) {
//            return;
//        }
//    }


}
