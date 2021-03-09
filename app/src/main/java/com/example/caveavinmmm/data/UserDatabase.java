package com.example.caveavinmmm.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.caveavinmmm.converter.Converters;
import com.example.caveavinmmm.model.User;
import com.example.caveavinmmm.model.Wine;

@Database(entities = {User.class, Wine.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDAO getUserDao();
    public abstract WineDAO getWineDao();
}
