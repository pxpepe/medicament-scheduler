package pt.pxpepe.medscheduler.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import pt.pxpepe.medscheduler.data.JoinScheduleInstanceMedicament;
import pt.pxpepe.medscheduler.data.Schedule;
import pt.pxpepe.medscheduler.data.ScheduleRepository;

public class ScheduleViewModel extends AndroidViewModel {

    private ScheduleRepository repository;

    // Constructor
    public ScheduleViewModel(Application application) {
        super(application);
        repository = new ScheduleRepository(application);
    }

    public LiveData<List<JoinScheduleInstanceMedicament>> getAllSchedules(long currentDate) {
        return repository.getAllSchedules(currentDate);
    }

    public LiveData<List<Schedule>> getSchedulesByInstance(int instanceId) {
        return repository.getSchedulesByInstance(instanceId);
    }

    public Long insertSchedule(Schedule schedule) {
        return repository.insertSchedule(schedule);
    }

    public void removeSchedule(String id) {
        repository.deleteSchedule(id);
    }

    public Schedule findScheduleById(Integer id) {
        return repository.findScheduleById(id);
    }


}
