package edu.gatech.cs2340.donationtracker.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class DonationTrackerRepository {
    private Dao mDao;

    public DonationTrackerRepository(Application application) {
        AppDatabase db = AppDatabase.getINSTANCE(application);
        mDao = db.dao();

    }

    public void insertItem (Item word) {
        new insertAsyncTask(mDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Item, Void, Void> {

        private Dao mAsyncTaskDao;

        insertAsyncTask(Dao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.insertItem(params[0]);
            return null;
        }
    }
}

