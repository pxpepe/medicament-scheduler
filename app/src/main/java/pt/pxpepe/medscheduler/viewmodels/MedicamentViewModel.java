package pt.pxpepe.medscheduler.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import pt.pxpepe.medscheduler.data.Medicament;
import pt.pxpepe.medscheduler.data.MedicamentRepository;

public class MedicamentViewModel extends AndroidViewModel {

    private MedicamentRepository repository;
    private LiveData<List<Medicament>> allMedicaments;

    // Constructor
    public MedicamentViewModel(Application application) {
        super(application);
        repository = new MedicamentRepository(application);
        allMedicaments = repository.getAllMedicaments();
    }

    public LiveData<List<Medicament>> getAllMedicaments() {
        return allMedicaments;
    }

    public Long insertMedicament(Medicament medicament) {
        return repository.insertMedicament(medicament);
    }

    public void updateMedicament(Medicament medicament) {
        repository.updateMedicament(medicament);
    }

    public void removeMedicament(String id) {
        repository.deleteMedicament(id);
    }

    public Medicament findMedicamentById(Integer id) {
        return repository.findMedicamentById(id);
    }


}
