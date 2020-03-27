package pt.pxpepe.medscheduler.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import pt.pxpepe.medscheduler.data.Instance;
import pt.pxpepe.medscheduler.data.InstanceRepository;
import pt.pxpepe.medscheduler.data.JoinInstanceTreatment;

public class InstanceViewModel extends AndroidViewModel {

    private InstanceRepository repository;
    private LiveData<List<JoinInstanceTreatment>> allInstances;

    // Constructor
    public InstanceViewModel(Application application) {
        super(application);
        repository = new InstanceRepository(application);
        allInstances = repository.getAllInstances();
    }

    public LiveData<List<JoinInstanceTreatment>> getAllInstances() {
        return allInstances;
    }

    public Long insertInstance(Instance instance) {
        return repository.insertInstance(instance);
    }

    public void removeInstance(String id) {
        repository.deleteInstance(id);
    }

    public Instance findInstanceById(Integer id) {
        return repository.findInstanceById(id);
    }


}
