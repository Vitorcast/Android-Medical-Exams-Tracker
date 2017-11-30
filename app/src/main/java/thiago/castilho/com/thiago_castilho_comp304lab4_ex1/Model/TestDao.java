package thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by thiag on 30/11/2017.
 */
@Dao
public abstract class TestDao implements BaseDao<Test>{

    @Query("SELECT * FROM test")
    public abstract List<Test> getAllTests();

    @Query("SELECT * FROM test where  id = :id")
    public abstract Test getTestById(Integer id);
}
