package backend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.hse.kanban.R;
import org.hse.kanban.TaskChangeActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import database.dao.KanbanDao;
import database.dataclass.AccessEntity;
import database.dataclass.RoomEntity;
import database.dataclass.TaskEntity;
import database.dataclass.UserEntity;

public class RoomSettingsActivityBackend {
    private Activity activity;
    private Context context;
    private KanbanDao kanbanDao;
    protected RoomEntity room;

    public RoomSettingsActivityBackend(Context _context, KanbanDao _kanbanDao, int idRoom) {
        context = _context;
        kanbanDao = _kanbanDao;
        room = kanbanDao.getRoomById(idRoom).get(0);
    }

    public String getNameRoom() {
        return room.name;
    }

    public void setNameRoom(String name) {
        room.name = name;
    }

    public List<UserEntity> getRoomUsers() {
        return kanbanDao.getUsersByRoom(room.idRoom);
    }

    public void addUser(int idUser) {
        AccessEntity access = new AccessEntity();
        access.idRoom = room.idRoom;
        access.idUser = idUser;
        access.role = AccessEntity.CASUAL;
        kanbanDao.insertAccess(access);
    }

    public void deleteUser(int idUser) {
        kanbanDao.deleteAccess(kanbanDao.getAccessByIdRoomAndUser(room.idRoom, idUser).get(0));
    }

    public void deleteRoomAndAccesses() {
        kanbanDao.eraseAccessesInRoom(room.idRoom);
        kanbanDao.deleteRoom(room);
    }

    public void applyChanges() {
        kanbanDao.updateRoom(room);
    }
}
