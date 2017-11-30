package thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;

/**
 * Created by thiag on 30/11/2017.
 */

@Database(entities = {Doctor.class, Nurse.class, Patient.class, Test.class}, version = 2)
public abstract class DataContext extends RoomDatabase {

    private static DataContext INSTANCE;

    public abstract DoctorDao doctorDao();
    public abstract NurseDao nurseDao();
    public abstract PatientDao patientDao();
    public abstract TestDao testDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };
}
