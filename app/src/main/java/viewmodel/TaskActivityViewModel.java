package viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import model.database.dao.KanbanDao;
import model.entity.TaskEntity;

public class TaskActivityViewModel extends AndroidViewModel {
    private final KanbanDao kanbanDao;
    private MutableLiveData<TaskEntity> taskLiveData;

    public TaskActivityViewModel(@NonNull Application application, KanbanDao _kanbanDao) {
        super(application);
        kanbanDao = _kanbanDao;
        taskLiveData = new MutableLiveData<>();
    }

    public LiveData<TaskEntity> getTaskLiveData() {
        return taskLiveData;
    }


}
