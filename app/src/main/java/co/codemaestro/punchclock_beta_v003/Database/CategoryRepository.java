package co.codemaestro.punchclock_beta_v003.Database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private TimeBankDao timeBankDao;

    private LiveData<List<Category>> allCategories;
    private LiveData<List<Category>> favorites;
    private LiveData<Category> categoryByTitle;
    private LiveData<List<TimeBank>> allTimeBanks;
    private LiveData<List<TimeBank>> categoryTimeBanks;
    private LiveData<Long> categoryTimeSum;

    private Category testCategory;
    private LiveData<String> categoryTitleTest;

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

    // TODO: test 1
    public void testGetCategoryTitleString(TextView textView, int id) {
        new testGetCategoryTitleStringAsync(categoryDao, textView).execute(id);
    }

    // TODO: test 2
    public void setCategoryTitle(String categoryTitle, int id) {
        new SetCategoryTitleAsync(categoryDao, categoryTitle).execute(id);
    }


    public LiveData<String> getCategoryTest(String title) {
        categoryTitleTest = categoryDao.getCategoryTest(title);
        return categoryTitleTest;
    }


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

    public void setAsNotFavorite(int id) {
        new SetAsNotFavoriteAsync(categoryDao).execute(id);
    }

    public LiveData<Boolean> checkIfFavorite(int id) {
        return categoryDao.checkIfFavorite(id);
    }

//    public Category getSpecificCategory(int id) {
//        new
//    }


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
    public LiveData<Long> getCategoryTimeSum(int id) {
        categoryTimeSum = timeBankDao.getCategoryTimeSum(id);
        return categoryTimeSum;
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



    /**
     * AsyncTask Inner Classes
     */

    // Category Async Methods

//    private static class getCategoryTest extends  AsyncTask<String, Void, Category> {
//        private CategoryDao categoryDao;
//        private Category testCategory;
//
//        public getCategoryTest(CategoryDao categoryDao) {
//            this.categoryDao = categoryDao;
//        }
//
//        @Override
//        protected Category doInBackground(String... strings) {
//            testCategory = categoryDao.getCategoryTest(strings[0]);
//            return testCategory;
//        }
//
//        @Override
//        protected void onPostExecute(Category category) {
//            super.onPostExecute(category);
//
//        }
//
//
//    }


    // TODO: test 1
    private static class SetCategoryTitleAsync extends AsyncTask<Integer, Void, String> {
        private CategoryDao categoryDao;
        private WeakReference<String> categoryTitle;

        public SetCategoryTitleAsync(CategoryDao categoryDao, String categoryTitle) {
            this.categoryDao = categoryDao;
            this.categoryTitle = new WeakReference<>(categoryTitle);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            Category category = categoryDao.getCategoryById(integers[0]);
            return category.getCategory();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            categoryTitle.get() = s;
        }

    }
    // TODO: test 2 works
    private static class testGetCategoryTitleStringAsync extends AsyncTask<Integer, Void, String> {
        private CategoryDao categoryDao;
        private WeakReference<TextView> categoryTitleString;


        public testGetCategoryTitleStringAsync(CategoryDao categoryDao, TextView categoryTitleString) {
            this.categoryDao = categoryDao;
            this.categoryTitleString = new WeakReference<>(categoryTitleString);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            Category category = categoryDao.getCategoryById(integers[0]);
            return category.getCategory();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            categoryTitleString.get().setText(s);
        }

    }



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

    private static class SetAsNotFavoriteAsync extends AsyncTask<Integer, Void, Void> {
        private CategoryDao categoryDao;

        public SetAsNotFavoriteAsync(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            categoryDao.setAsNotFavorite(integers[0]);
            return null;
        }
    }


    /** TimeBank Async Methods */
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
