package model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import model.database.AppDatabase;
import model.database.dao.KanbanDao;
import model.entity.AccessEntity;

public class AccessRepository {
    private final KanbanDao kanbanDao;

    public AccessRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        kanbanDao = db.kanbanDao();
    }

    public LiveData<List<AccessEntity>> getAccess() {
        return kanbanDao.getAccess();
    }

    public void insertAccess(AccessEntity access) {
        kanbanDao.insertAccess(access);
    }

    public void deleteAccess(AccessEntity access) {
        kanbanDao.deleteAccess(access);
    }
}
