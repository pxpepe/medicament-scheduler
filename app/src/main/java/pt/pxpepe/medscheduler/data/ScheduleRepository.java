package pt.pxpepe.medscheduler.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import pt.pxpepe.medscheduler.utilities.MyRoomDatabase;

public class ScheduleRepository {

    private ScheduleDAO scheduleDao;

    public ScheduleRepository(Application application){
        MyRoomDatabase db;
        db = MyRoomDatabase.getDatabase(application);
        scheduleDao = db.scheduleDAO();
    }

    public Long insertSchedule(Schedule newSchedule) {
        Long insertedId = null;
        try {
            insertedId = new insertAsyncTask(scheduleDao).execute(newSchedule).get();
        } catch (Exception e) {
            Log.e("",e.toString());
        }
        return insertedId;
    }

    public void deleteSchedule(String id) {
        new deleteAsyncTask(scheduleDao).execute(id);
    }

    public LiveData<List<JoinScheduleInstanceMedicament>> getAllSchedules(long currentDate) {
        return scheduleDao.getSchedulesWithInstanceMedicament(currentDate);
    }

    public LiveData<List<Schedule>> getSchedulesByInstance(int instanceId) {
        return scheduleDao.getSchedulesByInstance(instanceId);
    }

    public Schedule findScheduleById(Integer id) {
        Schedule schedule = null;
        try {
            schedule = new findByIdAsyncTask(scheduleDao).execute(id).get();
        } catch (Exception e) {
            Log.e("", e.toString());
        }
        return schedule;
    }

    private static class findByIdAsyncTask extends AsyncTask<Integer, Void, Schedule> {

        private ScheduleDAO asyncTaskDao;

        // Constructor
        findByIdAsyncTask(ScheduleDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Schedule doInBackground(final Integer... params) {
            return asyncTaskDao.findScheduleById(params[0]);
        }
    }

    private static class insertAsyncTask extends AsyncTask<Schedule, Void, Long> {

        private ScheduleDAO asyncTaskDao;

        // Constructor
        insertAsyncTask(ScheduleDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Schedule... params) {
            Long insertdId = null;
            try {
                insertdId = asyncTaskDao.insertSchedule(params[0]);
            } catch (Exception e) {
                Log.d("",e.toString());
            }
            return insertdId;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<String, Void, Void> {

        private ScheduleDAO asyncTaskDao;

        deleteAsyncTask(ScheduleDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.deleteSchedule(Integer.parseInt(params[0]));
            return null;
        }
    }

}
