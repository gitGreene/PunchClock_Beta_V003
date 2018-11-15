package co.codemaestro.punchclock_beta_v003;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

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
                            .fallbackToDestructiveMigration() //TODO: understand migrations
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
