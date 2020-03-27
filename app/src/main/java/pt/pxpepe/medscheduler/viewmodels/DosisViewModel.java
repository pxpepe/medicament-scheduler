package pt.pxpepe.medscheduler.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import pt.pxpepe.medscheduler.data.Dosis;
import pt.pxpepe.medscheduler.data.DosisRepository;
import pt.pxpepe.medscheduler.data.JoinDosisMedicamentTreatment;

public class DosisViewModel extends AndroidViewModel {

    private DosisRepository repository;

    // Constructor
    public DosisViewModel(Application application) {
        super(application);
        repository = new DosisRepository(application);
    }

    public LiveData<List<JoinDosisMedicamentTreatment>> getAllDosiss(Integer treatmentId) {
        return repository.getAllDosiss(treatmentId);
    }

    public Boolean insertDosis(Dosis instance) {
        return repository.insertDosis(instance);
    }

    public void removeDosis(String id) {
        repository.deleteDosis(id);
    }

    public Dosis findDosisById(Integer id) {
        return repository.findDosisById(id);
    }


}
