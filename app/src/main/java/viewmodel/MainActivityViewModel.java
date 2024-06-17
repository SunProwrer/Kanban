package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class MainActivityViewModel extends AndroidViewModel {
    //private MutableLiveData<List<AbstractTask>> tasksLiveData;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        //tasksLiveData = new MutableLiveData<>();
    }


}

