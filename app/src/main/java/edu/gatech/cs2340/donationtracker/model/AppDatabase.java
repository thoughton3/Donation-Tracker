package edu.gatech.cs2340.donationtracker.model;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomDatabase.Builder;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;


/**
 * the database that stores all of our Users and their information
 */
@Database(entities = {User.class, Item.class}, version = 4, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    /**
     * constructs a Dao
     * @return a Dao
     */
    public abstract Dao dao();
    private static AppDatabase INSTANCE;

    static AppDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    Builder<edu.gatech.cs2340.donationtracker.model.AppDatabase> build =
                            Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "word_database");
                    build.allowMainThreadQueries();
                    INSTANCE = build.build();
                }
            }
        }
        return INSTANCE;
    }
}
