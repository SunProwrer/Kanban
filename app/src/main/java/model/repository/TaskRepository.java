package model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import model.database.AppDatabase;
import model.database.dao.KanbanDao;
import model.entity.TaskEntity;

public class TaskRepository {
    private final KanbanDao kanbanDao;

    public TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        kanbanDao = db.kanbanDao();
    }

    public LiveData<List<TaskEntity>> getTasks() {
        return kanbanDao.getTask();
    }

    public List<TaskEntity> getTaskById(long idTask) {
        return kanbanDao.getTaskById(idTask);
    }

    public List<TaskEntity> getTaskByHeaderAndIdRoom(String header, long idRoom) {
        return kanbanDao.getTaskByHeaderAndIdRoom(header, idRoom);
    }

    public List<TaskEntity> getTasksByIdRoomAndStatus(long idRoom, int status) {
        return kanbanDao.getTasksByIdRoomAndStatus(idRoom, status);
    }

    public List<TaskEntity> getTaskByHeader(String header) {
        return kanbanDao.getTaskByHeader(header);
    }

    public void updateTask(TaskEntity task) {
        kanbanDao.updateTask(task);
    }

    public void insertTask(TaskEntity task) {
        kanbanDao.insertTask(task);
    }

    public void deleteTask(TaskEntity task) {
        kanbanDao.deleteTask(task);
    }

    public List<Integer> getCountTasksByRoomAndStatus(long idRoom, int status) {
        return kanbanDao.getCountTasksByRoomAndStatus(idRoom, status);
    }
}
