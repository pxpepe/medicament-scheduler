package pt.pxpepe.medscheduler.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import pt.pxpepe.medscheduler.utilities.MyRoomDatabase;

public class MedicamentRepository {

    private MedicamentDAO medicamentDao;

    private LiveData<List<Medicament>> allMedicaments;

    public MedicamentRepository(Application application){
        MyRoomDatabase db;
        db = MyRoomDatabase.getDatabase(application);
        medicamentDao = db.medicamentDAO();
        allMedicaments = medicamentDao.getAllMedicaments();
    }

    public Long insertMedicament(Medicament newMedicament) {
        Long insertedId = null;
        try {
            insertedId = new insertAsyncTask(medicamentDao).execute(newMedicament).get();
        } catch (Exception e) {
            Log.e("", e.toString());
        }
        return insertedId;
    }

    public void updateMedicament(Medicament medicament) {
        new updateAsyncTask(medicamentDao).execute(medicament);
    }

    public void deleteMedicament(String id) {
        new deleteAsyncTask(medicamentDao).execute(id);
    }

    public LiveData<List<Medicament>> getAllMedicaments() {
        return allMedicaments;
    }

    public Medicament findMedicamentById(Integer id) {
        Medicament medicament = null;
        try {
            medicament = new findByIdAsyncTask(medicamentDao).execute(id).get();
        } catch (Exception e) {
            Log.e("", e.toString());
        }
        return medicament;
    }

    private static class findByIdAsyncTask extends AsyncTask<Integer, Void, Medicament> {

        private MedicamentDAO asyncTaskDao;

        // Constructor
        findByIdAsyncTask(MedicamentDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Medicament doInBackground(final Integer... params) {
            return asyncTaskDao.findMedicamentById(params[0]);
        }
    }

    private static class insertAsyncTask extends AsyncTask<Medicament, Void, Long> {

        private MedicamentDAO asyncTaskDao;

        // Constructor
        insertAsyncTask(MedicamentDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Medicament... params) {
            return asyncTaskDao.insertMedicament(params[0]);
        }
    }

    private static class updateAsyncTask extends AsyncTask<Medicament, Void, Void> {

        private MedicamentDAO asyncTaskDao;

        // Constructor
        updateAsyncTask(MedicamentDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Medicament... params) {
            asyncTaskDao.updateMedicament(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<String, Void, Void> {

        private MedicamentDAO asyncTaskDao;

        deleteAsyncTask(MedicamentDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.deleteMedicament(Integer.parseInt(params[0]));
            return null;
        }
    }

}
