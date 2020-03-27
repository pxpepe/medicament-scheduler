package pt.pxpepe.medscheduler.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MedicamentDAO {

    @Insert
    Long insertMedicament(Medicament medicament);

    @Query("DELETE FROM medicament WHERE id = :id")
    void deleteMedicament(Integer id);

    @Update
    void updateMedicament(Medicament medicament);

    @Query("SELECT * FROM medicament")
    LiveData<List<Medicament>> getAllMedicaments();

    @Query("SELECT * FROM medicament where id = :id")
    Medicament findMedicamentById(Integer id);

}
