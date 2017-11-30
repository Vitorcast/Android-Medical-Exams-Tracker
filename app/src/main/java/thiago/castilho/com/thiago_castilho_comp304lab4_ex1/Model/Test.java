package thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by thiag on 30/11/2017.
 */
@Entity
public class Test {
    @PrimaryKey(autoGenerate = true) public int id = 0;
    public Integer bpl;
    public Integer bph;
    public Integer temperature;
    public Integer nurseId;
    public Integer patientId;

    public Test(Integer bpl, Integer bph, Integer temperature, Integer nurseId, Integer patientId) {
        this.bpl = bpl;
        this.bph = bph;
        this.temperature = temperature;
        this.nurseId = nurseId;
        this.patientId = patientId;
    }
}
