package pt.pxpepe.medscheduler.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import pt.pxpepe.medscheduler.data.Treatment;
import pt.pxpepe.medscheduler.data.TreatmentRepository;

public class TreatmentViewModel extends AndroidViewModel {

    private TreatmentRepository repository;
    private LiveData<List<Treatment>> allTreatments;

    // Constructor
    public TreatmentViewModel(Application application) {
        super(application);
        repository = new TreatmentRepository(application);
        allTreatments = repository.getAllTreatments();
    }

    public LiveData<List<Treatment>> getAllTreatments() {
        return allTreatments;
    }

    public Long insertTreatment(Treatment treatment) {
        return repository.insertTreatment(treatment);
    }

    public void updateTreatment(Treatment treatment) {
        repository.updateTreatment(treatment);
    }

    public void removeTreatment(String id) {
        repository.deleteTreatment(id);
    }

    public Treatment findTreatmentById(Integer id) {
        return repository.findTreatmentById(id);
    }


}
