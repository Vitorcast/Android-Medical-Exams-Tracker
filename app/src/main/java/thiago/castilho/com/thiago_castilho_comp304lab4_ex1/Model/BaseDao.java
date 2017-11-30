package thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by thiag on 30/11/2017.
 */

@Dao
interface BaseDao<T>{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(T... t);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleList(List<T> t);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(T t);

    @Delete
    void deleteUser(T t);
}
