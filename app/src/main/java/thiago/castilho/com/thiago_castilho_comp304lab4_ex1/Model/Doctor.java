package thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by thiag on 30/11/2017.
 */
@Entity
public class Doctor {
    @PrimaryKey(autoGenerate = true) public int id = 0;
    public String firstName;
    public String lastName;
    public String department;
    public String email;
    public String password;

    @Ignore
    public Doctor(String firstName, String lastName, String department, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.email = email;
        this.password = password;
    }

    public Doctor(String email, String password) {
        this.firstName = "Not Set";
        this.lastName = "Not Set";
        this.department = "Not Set";
        this.email = email;
        this.password = password;
    }
}
