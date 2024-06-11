package viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.hse.kanban.RoomActivity;

import java.util.List;

import adapters.RoomAdapter;
import model.database.DatabaseManager;
import model.database.checkers.Checker;
import model.database.dao.KanbanDao;
import model.entity.AccessEntity;
import model.entity.RoomEntity;
import model.entity.UserEntity;

public class RoomsActivityViewModel extends AndroidViewModel {
    private static final String TAG = "RoomsActivityViewModel";
    private final KanbanDao kanbanDao;
    private final MutableLiveData<List<RoomEntity>> roomsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> nameOfNewRoomLiveData = new MutableLiveData<>();
    private UserEntity user;

    public RoomsActivityViewModel(@NonNull Application application) {
        super(application);
        kanbanDao = DatabaseManager.getInstance(application).getKanbanDao();
    }

    public void init(String login) {
        user = kanbanDao.getUserByLogin(login).get(0);
        initRoomsList();
    }

    public LiveData<List<RoomEntity>> getRoomsLiveData() {
        return roomsLiveData;
    }

    public LiveData<String> getNameOfNewRoomLiveData() {
        return nameOfNewRoomLiveData;
    }

    public void setNameOfNewRoom(String name) {
        nameOfNewRoomLiveData.setValue(name);
    }

    public String getLogin() {
        return user.login;
    }

    public void createNewRoom() {
        String nameOfNewRoom = nameOfNewRoomLiveData.getValue();
        if (nameOfNewRoom == null || nameOfNewRoom.isEmpty()) {
            // TODO: Show a toast or handle the error appropriately
            return;
        }
        if (!Checker.checkAvailableNameOfRoom(kanbanDao, nameOfNewRoom)) {
            // TODO: Show a toast or handle the error appropriately
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
        addRoomToList(newRoom);
    }

    public void goToRoom(Context context, int index) {
        Intent intent = new Intent(context, RoomActivity.class);
        intent.putExtra(RoomActivity.USER, user.login);
        intent.putExtra(RoomActivity.ROOM, roomsLiveData.getValue().get(index).name);
        context.startActivity(intent);
    }

    private void initRoomsList() {
        List<RoomEntity> rooms = kanbanDao.getRoomsByUserId(user.idUser);
        roomsLiveData.setValue(rooms);
    }

    private void addRoomToList(RoomEntity newRoom) {
        List<RoomEntity> currentRooms = roomsLiveData.getValue();
        currentRooms.add(newRoom);
        roomsLiveData.setValue(currentRooms);
    }
}


