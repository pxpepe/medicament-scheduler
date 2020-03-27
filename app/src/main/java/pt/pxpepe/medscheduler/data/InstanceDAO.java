package pt.pxpepe.medscheduler.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface InstanceDAO {

    @Insert
    long insertInstance(Instance instance);

    @Query("DELETE FROM instance WHERE id = :id")
    void deleteInstance(Integer id);

    @Query("SELECT * FROM instance where id = :id")
    Instance findInstanceById(Integer id);

    @Query("SELECT ii.id as instanceId, ii.treatmentId, " +
            " tt.name, tt.duration, ii.startDate, ii.endDate" +
            " FROM instance ii " +
            " INNER JOIN treatment tt" +
            " ON ii.treatmentId = tt.id")
    LiveData<List<JoinInstanceTreatment>> getInstancesWithTreatment();

}
