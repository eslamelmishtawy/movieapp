package com.example.android.movieapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.android.movieapp.database.AppDatabase;
import com.example.android.movieapp.database.FavEntry;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<FavEntry>> favs;


    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        favs = database.taskDao().loadAllFavs();
    }

    public LiveData<List<FavEntry>> getTasks() {
        return favs;
    }
}
