package pt.pxpepe.medscheduler.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import pt.pxpepe.medscheduler.utilities.MyRoomDatabase;

public class InstanceRepository {

    private InstanceDAO instanceDao;

    private LiveData<List<JoinInstanceTreatment>> allInstances;

    public InstanceRepository(Application application){
        MyRoomDatabase db;
        db = MyRoomDatabase.getDatabase(application);
        instanceDao = db.instanceDAO();
        allInstances = instanceDao.getInstancesWithTreatment();
    }

    public Long insertInstance(Instance newInstance) {
        Long insertedId = null;
        try {
            insertedId = new insertAsyncTask(instanceDao).execute(newInstance).get();
        } catch (Exception e) {
            Log.e("",e.toString());
        }
        return insertedId;
    }

    public void deleteInstance(String id) {
        new deleteAsyncTask(instanceDao).execute(id);
    }

    public LiveData<List<JoinInstanceTreatment>> getAllInstances() {
        return allInstances;
    }

    public Instance findInstanceById(Integer id) {
        Instance instance = null;
        try {
            instance = new findByIdAsyncTask(instanceDao).execute(id).get();
        } catch (Exception e) {
            Log.e("", e.toString());
        }
        return instance;
    }

    private static class findByIdAsyncTask extends AsyncTask<Integer, Void, Instance> {

        private InstanceDAO asyncTaskDao;

        // Constructor
        findByIdAsyncTask(InstanceDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Instance doInBackground(final Integer... params) {
            return asyncTaskDao.findInstanceById(params[0]);
        }
    }

    private static class insertAsyncTask extends AsyncTask<Instance, Void, Long> {

        private InstanceDAO asyncTaskDao;

        // Constructor
        insertAsyncTask(InstanceDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Instance... params) {
            Long insertdId = null;
            try {
                insertdId = asyncTaskDao.insertInstance(params[0]);
            } catch (Exception e) {
                Log.d("",e.toString());
            }
            return insertdId;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<String, Void, Void> {

        private InstanceDAO asyncTaskDao;

        deleteAsyncTask(InstanceDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.deleteInstance(Integer.parseInt(params[0]));
            return null;
        }
    }

}
