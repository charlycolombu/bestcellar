package com.example.caveavinmmm.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.caveavinmmm.converter.Converters;
import com.example.caveavinmmm.model.User;
import com.example.caveavinmmm.model.Wine;

@Database(entities = {User.class, Wine.class}, version = 2, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class UserDatabase extends RoomDatabase {
    private static final String DB_NAME = "vin-database.db";
    private static volatile UserDatabase instance;

    public static synchronized UserDatabase getInstance(Context context) {
        instance = create(context);
        return instance;
    }

    public UserDatabase() {};

    public static UserDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                UserDatabase.class,
                DB_NAME).allowMainThreadQueries().build();
    }

    public abstract UserDAO getUserDao();
    public abstract WineDAO getWineDao();
}


