package backend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import org.hse.kanban.RoomActivity;

import java.util.List;

import adapters.RoomAdapter;
import database.checkers.Checker;
import database.dao.KanbanDao;
import database.dataclass.AccessEntity;
import database.dataclass.RoomEntity;
import database.dataclass.UserEntity;

public class RoomsActivityBackend {
    private final String TAG = "RoomsActivityBackend";
    private Activity activity;
    private final Context context;
    private final KanbanDao kanbanDao;
    private UserEntity user;
    private List<RoomEntity> rooms;
    private RoomAdapter adapter;
    private String nameOfNewRoom = "";

    public RoomsActivityBackend(Context _context, KanbanDao _kanbanDao, String _login) {
        context = _context;
        kanbanDao = _kanbanDao;
        user = kanbanDao.getUserByLogin(_login).get(0);
        initRoomsList();
        createAdapter();
    }

    public String getLogin() {
        return user.login;
    }

    public void setNameOfNewRoom(String name) {
        nameOfNewRoom = name;
    }

    public RoomAdapter getAdapter() {
        return adapter;
    }

    public void createNewRoom() {
        if (nameOfNewRoom.isEmpty()) {
            //TODO toast
            return;
        }
        if (Checker.checkAvailableNameOfRoom(kanbanDao, nameOfNewRoom)) {
            //TODO toast
            return;
        }
        RoomEntity newRoom = new RoomEntity();
        newRoom.name = nameOfNewRoom;
        kanbanDao.insertRoom(newRoom);
        newRoom = kanbanDao.getRoomByName(nameOfNewRoom).get(0);
        Log.i(TAG, String.valueOf(newRoom.idRoom));

        AccessEntity access = new AccessEntity();
        access.idRoom = newRoom.idRoom;
        access.idUser = user.idUser;
        access.role = AccessEntity.OWNER;
        kanbanDao.insertAccess(access);
        addRoomToList(nameOfNewRoom);
        updateAdapter();
    }

    public void goToRoom(int index) {
        Intent intent = new Intent(context, RoomActivity.class);
        intent.putExtra(RoomActivity.USER, user.login);
        intent.putExtra(RoomActivity.ROOM, rooms.get(index).name);
        context.startActivity(intent);
    }

    private void initRoomsList() {
        rooms = kanbanDao.getRoomsByUserId(user.idUser);
    }

    private void addRoomToList(String name) {
        rooms.add(kanbanDao.getRoomByName(name).get(0));
    }

    private void createAdapter() {
        adapter = new RoomAdapter(context, rooms);
    }

    private void updateAdapter() {
        adapter.notifyItemInserted(rooms.size() - 1);
    }
}
