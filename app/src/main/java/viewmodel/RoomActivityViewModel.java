package viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.hse.kanban.TaskActivity;

import java.util.List;

import adapters.TaskAdapter;
import model.database.checkers.Checker;
import model.database.dao.KanbanDao;
import model.entity.RoomEntity;
import model.entity.TaskEntity;
import model.entity.UserEntity;

public class RoomActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<TaskEntity>> tasksLiveData;
    private KanbanDao kanbanDao;
    private RoomEntity room;
    private UserEntity user;
    private String headerOfNewTask = "";
    private int status = TaskEntity.TODO;

    public RoomActivityViewModel(@NonNull Application application, KanbanDao _kanbanDao, String login, String name) {
        super(application);
        kanbanDao = _kanbanDao;
        user = kanbanDao.getUserByLogin(login).get(0);
        room = kanbanDao.getRoomByName(name).get(0);
        tasksLiveData = new MutableLiveData<>();
        initTasksList();
    }

    public String getNameOfRoom() {
        return room.name;
    }

    public void setHeaderOfNewTask(String header) {
        headerOfNewTask = header;
    }

    public LiveData<List<TaskEntity>> getTasksLiveData() {
        return tasksLiveData;
    }

    public void createNewTask() {
        if (headerOfNewTask.isEmpty()) {
            // TODO: Show a toast or handle the error appropriately
            return;
        }
        if (!Checker.checkAvailableNameOfTaskForRoom(kanbanDao, headerOfNewTask, room.idRoom)) {
            // TODO: Show a toast or handle the error appropriately
            return;
        }
        TaskEntity newTask = new TaskEntity();
        newTask.header = headerOfNewTask;
        newTask.idRoom = room.idRoom;
        newTask.status = status;
        kanbanDao.insertTask(newTask);

        addNewTaskToList(headerOfNewTask);
    }

    public void goToTask(int index) {
        Intent intent = new Intent(getApplication(), TaskActivity.class);
        intent.putExtra(TaskActivity.ROOM, room.idRoom);
        intent.putExtra(TaskActivity.TASK, tasksLiveData.getValue().get(index).idTask);
        getApplication().startActivity(intent);
    }

    public void changeStatusToTODO() {
        status = TaskEntity.TODO;
        refreshTaskList();
    }

    public void changeStatusToDOING() {
        status = TaskEntity.DOING;
        refreshTaskList();
    }

    public void changeStatusToDONE() {
        status = TaskEntity.DONE;
        refreshTaskList();
    }

    public int getCountOfTODOTasks() {
        return kanbanDao.getCountTasksByRoomAndStatus(room.idRoom, TaskEntity.TODO).get(0);
    }

    public int getCountOfDOINGTasks() {
        return kanbanDao.getCountTasksByRoomAndStatus(room.idRoom, TaskEntity.DOING).get(0);
    }

    public int getCountOfDONETasks() {
        return kanbanDao.getCountTasksByRoomAndStatus(room.idRoom, TaskEntity.DONE).get(0);
    }

    private void initTasksList() {
        List<TaskEntity> tasks = kanbanDao.getTasksByIdRoomAndStatus(room.idRoom, status);
        tasksLiveData.setValue(tasks);
    }

    private void addNewTaskToList(String header) {
        List<TaskEntity> currentTasks = tasksLiveData.getValue();
        currentTasks.add(kanbanDao.getTaskByHeader(header).get(0));
        tasksLiveData.setValue(currentTasks);
    }

    private void refreshTaskList() {
        List<TaskEntity> updatedTasks = kanbanDao.getTasksByIdRoomAndStatus(room.idRoom, status);
        tasksLiveData.setValue(updatedTasks);
    }
}


