package thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by thiag on 30/11/2017.
 */
@Dao
public abstract class PatientDao implements BaseDao<Patient> {

    @Query("SELECT * FROM patient")
    public abstract List<Patient> getAllPatients();

    @Query("SELECT * FROM patient where  id = :id")
    public abstract Patient getPatientById(Integer id);

    @Query("SELECT * FROM patient where  firstName = :name")
    public abstract Patient getPatientByName(String name);


}
