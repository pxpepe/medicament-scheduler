package pt.pxpepe.medscheduler.utilities;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import pt.pxpepe.medscheduler.data.Dosis;
import pt.pxpepe.medscheduler.data.DosisDAO;
import pt.pxpepe.medscheduler.data.Instance;
import pt.pxpepe.medscheduler.data.InstanceDAO;
import pt.pxpepe.medscheduler.data.Medicament;
import pt.pxpepe.medscheduler.data.MedicamentDAO;
import pt.pxpepe.medscheduler.data.Schedule;
import pt.pxpepe.medscheduler.data.ScheduleDAO;
import pt.pxpepe.medscheduler.data.Treatment;
import pt.pxpepe.medscheduler.data.TreatmentDAO;

@Database(entities = {Treatment.class, Medicament.class, Dosis.class, Instance.class, Schedule.class}, version = 1, exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {

    public abstract TreatmentDAO treatmentDAO();
    public abstract MedicamentDAO medicamentDAO();
    public abstract InstanceDAO instanceDAO();
    public abstract DosisDAO dosisDAO();
    public abstract ScheduleDAO scheduleDAO();

    private static MyRoomDatabase INSTANCE;

    public static MyRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (MyRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    MyRoomDatabase.class, "medSchedulerDataBase0")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
