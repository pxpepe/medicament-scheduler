package pt.pxpepe.medscheduler.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TreatmentDAO {

    @Insert
    Long insertTreatment(Treatment treatment);

    @Query("DELETE FROM treatment WHERE id = :id")
    void deleteTreatment(Integer id);

    @Update
    void updateTreatment(Treatment treatment);

    @Query("SELECT * FROM treatment")
    LiveData<List<Treatment>> getAllTreatments();

    @Query("SELECT * FROM treatment where id = :id")
    Treatment findTreatmentById(Integer id);

}
