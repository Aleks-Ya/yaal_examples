package ru.yaal.examples.android.activity.fragment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelViewModel extends ViewModel {
    MutableLiveData<String> text = new MutableLiveData<>();
}
