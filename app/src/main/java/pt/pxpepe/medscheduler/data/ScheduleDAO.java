package pt.pxpepe.medscheduler.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface ScheduleDAO {

    @Insert
    long insertSchedule(Schedule schedule);

    @Query("DELETE FROM schedule WHERE id = :id")
    void deleteSchedule(Integer id);

    @Query("SELECT * FROM schedule where id = :id")
    Schedule findScheduleById(Integer id);

    @Query("SELECT ss.id as scheduleId, ss.instanceId, " +
            " ii.treatmentId, ss.medicamentId, mm.name as medicamentName,  " +
            "ss.instant, ss.took, ss.delay" +
            " FROM schedule ss " +
            " INNER JOIN instance ii" +
            " ON ss.instanceId = ii.id" +
            " INNER JOIN medicament mm" +
            " ON ss.medicamentId = mm.id " +
            " WHERE ss.instant between :currentDate-(20*60*1000) and :currentDate+(60*60*1000)")
    LiveData<List<JoinScheduleInstanceMedicament>> getSchedulesWithInstanceMedicament(long currentDate);

    @Query("SELECT * from schedule where instanceId = :instanceId")
    LiveData<List<Schedule>> getSchedulesByInstance(int instanceId);

}
