package backend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.hse.kanban.TaskActivity;

import java.util.List;

import adapters.TaskAdapter;
import database.checkers.Checker;
import database.dao.KanbanDao;
import database.dataclass.RoomEntity;
import database.dataclass.TaskEntity;
import database.dataclass.UserEntity;

public class RoomActivityBackend {
    private Activity activity;
    private Context context;
    private KanbanDao kanbanDao;
    private UserEntity user;
    private RoomEntity room;
    private List<TaskEntity> tasks;
    private TaskAdapter adapter;
    private String headerOfNewTask = "";
    private int status = TaskEntity.TODO;
    public RoomActivityBackend(Context _context, KanbanDao _kanbanDao, String login, String name){
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
        updateAdapter();
    }

    public void goToTask(int index) {
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(TaskActivity.ROOM, room.idRoom);
        intent.putExtra(TaskActivity.TASK, tasks.get(index).idTask);
        context.startActivity(intent);
    }

    public void changeStatusToTODO() {
        status = TaskEntity.TODO;
    }

    public void changeStatusToDOING() {
        status = TaskEntity.DOING;
    }

    public void changeStatusToDONE() {
        status = TaskEntity.DONE;
    }

    private void initTasksList() {
        tasks = kanbanDao.getTasksByIdRoomAndStatus(room.idRoom, status);
    }

    private void addNewTaskToList(String header) {
        tasks.add(kanbanDao.getTaskByHeader(header).get(0));
    }

    private void createAdapter() {
        adapter = new TaskAdapter(context, tasks);
    }

    private void updateAdapter() {
        adapter.notifyItemInserted(tasks.size() - 1);
    }
}
