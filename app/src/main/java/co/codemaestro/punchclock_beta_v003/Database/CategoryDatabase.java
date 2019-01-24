package co.codemaestro.punchclock_beta_v003.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.text.Format;

import co.codemaestro.punchclock_beta_v003.Classes.FormatMillis;


@Database(entities = {Category.class, TimeBank.class }, version = 36, exportSchema = false)

public abstract class CategoryDatabase extends RoomDatabase {

    // Dao abstract methods
    public abstract CategoryDao categoryDao();
    public abstract TimeBankDao timeBankDao();

    // Singleton
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
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };



    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final CategoryDao categoryDao;
        String[] defaultCategories = {"Work", "School", "Gym"};



        PopulateDbAsync(CategoryDatabase db) {
            categoryDao = db.categoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // format class object
            if(categoryDao.getAnyCategory().length < 1) {
                for(int i = 0; i <= defaultCategories.length - 1; i++) {
                    Category category = new Category(defaultCategories[i], 0, 0, 0, false, false);
                    categoryDao.insert(category);
                }
            }
            return null;
        }
    }
 }
