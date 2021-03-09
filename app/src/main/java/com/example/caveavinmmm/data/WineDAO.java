package com.example.caveavinmmm.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.caveavinmmm.model.Wine;

import java.util.List;

@Dao
public interface WineDAO {
    @Query("SELECT * FROM wine_table where nom_vin= :nomVin")
    Wine getWineByName(String nomVin);

    @Query("SELECT * FROM wine_table where id= :id")
    Wine findWineById(int id);

    @Query("SELECT * FROM wine_table")
    List<Wine> getWine();

    @Insert
    void insert(Wine wine);

    @Update
    void update(Wine wine);

    @Delete
    void delete(Wine wine);
}
