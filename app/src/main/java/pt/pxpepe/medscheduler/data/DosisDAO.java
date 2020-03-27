package pt.pxpepe.medscheduler.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DosisDAO {

    @Insert
    void insertDosis(Dosis dosis);

    @Query("DELETE FROM dosis WHERE id = :id")
    void deleteDosis(Integer id);

    @Query("SELECT * FROM dosis where id = :id")
    Dosis findDosisById(Integer id);

    @Query("SELECT dd.id as dosisId, dd.medicamentId, " +
            " mm.name as medicamentName, " +
            "dd.treatmentId, tt.name as treatmentName, " +
            " tt.duration, dd.period, dd.pillNumber, dd.delayNumber " +
            " FROM dosis dd " +
            " INNER JOIN treatment tt" +
            " ON dd.treatmentId = tt.id " +
            " INNER JOIN medicament mm" +
            " ON dd.medicamentId = mm.id " +
            " WHERE tt.id = :treatmentId")
    LiveData<List<JoinDosisMedicamentTreatment>> getDosisWithTreatmentAndMedicamentByTreatmentId(Integer treatmentId);

}
