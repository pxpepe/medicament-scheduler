package pt.pxpepe.medscheduler.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import pt.pxpepe.medscheduler.utilities.MyRoomDatabase;

public class DosisRepository {

    private DosisDAO dosisDao;

    public LiveData<List<JoinDosisMedicamentTreatment>> getAllDosiss(Integer treatmentId) {
        return dosisDao.getDosisWithTreatmentAndMedicamentByTreatmentId(treatmentId);
    }

    public DosisRepository(Application application){
        MyRoomDatabase db;
        db = MyRoomDatabase.getDatabase(application);
        dosisDao = db.dosisDAO();
    }

    public Boolean insertDosis(Dosis newDosis) {
        Boolean isInserted = false;
        try {
            isInserted = new insertAsyncTask(dosisDao).execute(newDosis).get();
        } catch (Exception e) {
            Log.e("",e.toString());
        }
        return isInserted;
    }

    public void deleteDosis(String id) {
        new deleteAsyncTask(dosisDao).execute(id);
    }

    public Dosis findDosisById(Integer id) {
        Dosis dosis = null;
        try {
            dosis = new findByIdAsyncTask(dosisDao).execute(id).get();
        } catch (Exception e) {
            Log.e("", e.toString());
        }
        return dosis;
    }

    private static class findByIdAsyncTask extends AsyncTask<Integer, Void, Dosis> {

        private DosisDAO asyncTaskDao;

        // Constructor
        findByIdAsyncTask(DosisDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Dosis doInBackground(final Integer... params) {
            return asyncTaskDao.findDosisById(params[0]);
        }
    }

    private static class insertAsyncTask extends AsyncTask<Dosis, Void, Boolean> {

        private DosisDAO asyncTaskDao;

        // Constructor
        insertAsyncTask(DosisDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Boolean doInBackground(final Dosis... params) {

            boolean isInserted = true;

            try {
                asyncTaskDao.insertDosis(params[0]);
            } catch (Exception e) {
                isInserted=false;
            }
            return isInserted;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<String, Void, Void> {

        private DosisDAO asyncTaskDao;

        deleteAsyncTask(DosisDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.deleteDosis(Integer.parseInt(params[0]));
            return null;
        }
    }

}
