package thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by thiag on 30/11/2017.
 */
@Entity
public class Patient {

    @PrimaryKey(autoGenerate = true) public int id = 0;
    public String firstName;
    public String lastName;
    public String department;
    public Integer doctorId;
    public Integer room;

    public Patient(String firstName, String lastName, String department, Integer doctorId, Integer room) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.doctorId = doctorId;
        this.room = room;
    }
}
