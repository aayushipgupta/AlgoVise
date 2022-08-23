package com.aayushigupta.algovise.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aayushigupta.algovise.algorithms.AlgorithmFactory;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        if (AlgorithmFactory.getCurrentAlgorithm() != null && AlgorithmFactory.getCurrentAlgorithm().getDescription() != null) {
            mText.setValue(AlgorithmFactory.getCurrentAlgorithm().getDescription());
        } else {
            mText.setValue("Data Not Available!!");
        }
    }

    public LiveData<String> getText() {
        return mText;
    }
}