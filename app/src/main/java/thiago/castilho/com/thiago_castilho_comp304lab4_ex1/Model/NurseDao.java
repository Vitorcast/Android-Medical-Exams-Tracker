package thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by thiag on 30/11/2017.
 */
@Dao
public abstract class NurseDao implements BaseDao<Nurse>{

    @Query("SELECT * FROM nurse")
    public abstract List<Nurse> getAllNurses();

    @Query("SELECT * FROM nurse where  id = :id")
    public abstract Nurse getNurseById(Integer id);

    @Query("SELECT * FROM nurse where  email = :email")
    public abstract Nurse getNurseByEmail(String email);
}
