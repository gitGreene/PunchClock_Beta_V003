package co.codemaestro.punchclock_beta_v003;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Category.class}, version = 1, exportSchema = false)
public abstract class CategoryDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();

    private static volatile CategoryDatabase INSTANCE;

    static CategoryDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (CategoryDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CategoryDatabase.class, "category_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration() //TODO: understand migrations
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final CategoryDao dao;
        String[] defaultCategories = {"Work", "School", "Gym"};
        String defaultTimeValues = "00:00:00";

        PopulateDbAsync(CategoryDatabase db) {
            dao = db.categoryDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {

            if(dao.getAnyCategory().length < 1) {
                for(int i = 0; i <= defaultCategories.length - 1; i++) {
                    Category category = new Category(defaultCategories[i], defaultTimeValues);
                    dao.insert(category);
                }
            }

//            dao.deleteAll();
//            Category category = new Category(1,"Work", "12345");
//            dao.insert(category);
            return null;
        }
    }
 }
