package com.example.caveavinmmm.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.caveavinmmm.model.User;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM user_table where email= :mail and password= :password")
    User getUser(String mail, String password);

    @Query("SELECT * FROM user_table where email= :mail")
    User findUserByMail(String mail);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
