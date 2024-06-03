package viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.hse.kanban.TaskActivity;

import java.util.List;

import adapters.TaskAdapter;
import model.database.checkers.Checker;
import model.database.dao.KanbanDao;
import model.entity.RoomEntity;
import model.entity.TaskEntity;
import model.entity.UserEntity;

public class RoomActivityViewModel {
    private Activity activity;
    private Context context;
    private KanbanDao kanbanDao;
    private UserEntity user;
    private RoomEntity room;
    private List<TaskEntity> tasks;
    private TaskAdapter adapter;
    private String headerOfNewTask = "";
    private int status = TaskEntity.TODO;
    public RoomActivityViewModel(Context _context, KanbanDao _kanbanDao, String login, String name){
        context = _context;
        kanbanDao = _kanbanDao;
        user = kanbanDao.getUserByLogin(login).get(0);
        room = kanbanDao.getRoomByName(name).get(0);
        initTasksList();
        createAdapter();
    }

    public String getNameOfRoom() { return room.name; }

    public void setHeaderOfNewTask(String header) {
        headerOfNewTask = header;
    }

    public TaskAdapter getAdapter() {
        return adapter;
    }

    public void createNewTask() {
        if (headerOfNewTask.isEmpty()) {
            //TODO toast
            return;
        }
        if (Checker.checkAvailableNameOfTaskForRoom(kanbanDao, headerOfNewTask, room.idRoom)) {
            //TODO toast
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
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(TaskActivity.ROOM, room.idRoom);
        intent.putExtra(TaskActivity.TASK, tasks.get(index).idTask);
        context.startActivity(intent);
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
        tasks = kanbanDao.getTasksByIdRoomAndStatus(room.idRoom, status);
    }

    private void addNewTaskToList(String header) {
        tasks.add(kanbanDao.getTaskByHeader(header).get(0));
        updateAdapter();
    }

    private void refreshTaskList() {
        tasks.clear();
        tasks.addAll(kanbanDao.getTasksByIdRoomAndStatus(room.idRoom, status));
        hardUpdateAdapter();
    }

    private void createAdapter() {
        adapter = new TaskAdapter(context, tasks);
    }

    private void updateAdapter() {
        adapter.notifyItemInserted(tasks.size() - 1);
    }

    private void hardUpdateAdapter() {
        adapter.notifyDataSetChanged();
    }
}
