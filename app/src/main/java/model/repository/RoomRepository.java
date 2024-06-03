package model.repository;

import android.app.Application;

import java.util.List;

import model.database.AppDatabase;
import model.database.dao.KanbanDao;
import model.entity.RoomEntity;

public class RoomRepository {
    private final KanbanDao kanbanDao;

    public RoomRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        kanbanDao = db.kanbanDao();
    }

    public List<RoomEntity> getRooms() {
        return kanbanDao.getRooms();
    }

    public List<RoomEntity> getRoomById(long idRoom) {
        return kanbanDao.getRoomById(idRoom);
    }

    public List<RoomEntity> getRoomsByUserId(long idUser) {
        return kanbanDao.getRoomsByUserId(idUser);
    }

    public List<RoomEntity> getRoomByName(String name) {
        return kanbanDao.getRoomByName(name);
    }

    public void updateRoom(RoomEntity room) {
        kanbanDao.updateRoom(room);
    }

    public void insertRoom(RoomEntity room) {
        kanbanDao.insertRoom(room);
    }

    public void deleteRoom(RoomEntity room) {
        kanbanDao.deleteRoom(room);
    }
}
