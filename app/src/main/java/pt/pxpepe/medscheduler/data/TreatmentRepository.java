package pt.pxpepe.medscheduler.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import pt.pxpepe.medscheduler.utilities.MyRoomDatabase;

public class TreatmentRepository {

    private TreatmentDAO treatmentDao;

    private LiveData<List<Treatment>> allTreatments;

    public TreatmentRepository(Application application){
        MyRoomDatabase db;
        db = MyRoomDatabase.getDatabase(application);
        treatmentDao = db.treatmentDAO();
        allTreatments = treatmentDao.getAllTreatments();
    }

    public Long insertTreatment(Treatment newTreatment) {
        Long insertedId = null;
        try {
            insertedId = new insertAsyncTask(treatmentDao).execute(newTreatment).get();
        } catch (Exception e) {
            Log.e("",e.toString());
        }
        return insertedId;
    }

    public void updateTreatment(Treatment treatment) {
        new updateAsyncTask(treatmentDao).execute(treatment);
    }

    public void deleteTreatment(String id) {
        new deleteAsyncTask(treatmentDao).execute(id);
    }

    public LiveData<List<Treatment>> getAllTreatments() {
        return allTreatments;
    }

    public Treatment findTreatmentById(Integer id) {
        Treatment treatment = null;
        try {
            treatment = new findByIdAsyncTask(treatmentDao).execute(id).get();
        } catch (Exception e) {
            Log.e("", e.toString());
        }
        return treatment;
    }

    private static class findByIdAsyncTask extends AsyncTask<Integer, Void, Treatment> {

        private TreatmentDAO asyncTaskDao;

        // Constructor
        findByIdAsyncTask(TreatmentDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Treatment doInBackground(final Integer... params) {
            return asyncTaskDao.findTreatmentById(params[0]);
        }
    }

    private static class insertAsyncTask extends AsyncTask<Treatment, Void, Long> {

        private TreatmentDAO asyncTaskDao;

        // Constructor
        insertAsyncTask(TreatmentDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Treatment... params) {
            return asyncTaskDao.insertTreatment(params[0]);
        }
    }

    private static class updateAsyncTask extends AsyncTask<Treatment, Void, Void> {

        private TreatmentDAO asyncTaskDao;

        // Constructor
        updateAsyncTask(TreatmentDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Treatment... params) {
            asyncTaskDao.updateTreatment(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<String, Void, Void> {

        private TreatmentDAO asyncTaskDao;

        deleteAsyncTask(TreatmentDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.deleteTreatment(Integer.parseInt(params[0]));
            return null;
        }
    }

}
