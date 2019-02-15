package co.codemaestro.punchclock_beta_v003.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private TimeBankDao timeBankDao;
    private GoalDao goalDao;

    private LiveData<List<Category>> allCategories;
    private LiveData<List<Category>> favorites;
    private LiveData<Category> categoryByTitle;
    private LiveData<List<TimeBank>> allTimeBanks;
    private LiveData<List<TimeBank>> categoryTimeBanks;
    private LiveData<Long> categoryTimeSum;

    private LiveData<List<Goal>> allCategoryGoals;

    /**
     * @param application
     * Constructor for the Repository
     * Initializes:
     * CategoryDao
     * TimeBankDao
     * GoalDao
     * allCategories List
     * allTimeBanks List
     */
    public CategoryRepository(Application application) {
        CategoryDatabase db = CategoryDatabase.getDatabase(application);
        categoryDao = db.categoryDao();
        timeBankDao = db.timeBankDao();
        goalDao = db.goalDao();
        allCategories = categoryDao.getAllCategories();
        allTimeBanks = timeBankDao.getAllTimeBanks();
    }


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
    public LiveData<List<Category>> getFavorites() {
        favorites = categoryDao.getFavorites();
        return favorites;
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
     * Goal Methods
     */
    public void insertGoal(Goal goal) {
        new InsertGoalAsyncTask(goalDao).execute(goal);
    }

    public LiveData<List<Goal>> getAllCategoryGoals(int parentCategoryId) {
        allCategoryGoals = goalDao.getAllGoalsByParentId(parentCategoryId);
        return allCategoryGoals;
    }






    /**
     * Category Async Tasks
     */

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


    /**
     * TimeBank Async Tasks
     */
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


    /**
     * Goal Async Tasks
     */

    private static class InsertGoalAsyncTask extends  AsyncTask<Goal, Void, Void>{
        private GoalDao goalDao;

        public InsertGoalAsyncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }

        @Override
        protected Void doInBackground(Goal... goals) {
            goalDao.insertGoal(goals[0]);
            return null;
        }
    }




}
