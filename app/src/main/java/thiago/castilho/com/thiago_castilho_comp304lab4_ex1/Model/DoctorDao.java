package thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by thiag on 30/11/2017.
 */
@Dao
public abstract class DoctorDao implements BaseDao<Doctor> {

    @Query("SELECT * FROM doctor")
    public abstract List<Doctor> getAllDoctors();

    @Query("SELECT * FROM doctor where  id = :id")
    public abstract Doctor getDoctorById(Integer id);

    @Query("SELECT * FROM doctor where  email = :email")
    public abstract Doctor getDoctorByEmail(String email);

    @Query("SELECT * FROM doctor where  firstName = :firstName and lastName = :lastName")
    public abstract Doctor getDoctorByName(String firstName, String lastName);
}
