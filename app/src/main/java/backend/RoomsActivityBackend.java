package backend;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import database.dao.KanbanDao;
import database.dataclass.RoomEntity;
import database.dataclass.UserEntity;

public class RoomsActivityBackend {
    private Activity activity;
    private final Context context;
    private final KanbanDao kanbanDao;
    private UserEntity user;
    private List<RoomEntity> rooms;

    public RoomsActivityBackend(Context _context, KanbanDao _kanbanDao, String _login) {
        context = _context;
        kanbanDao = _kanbanDao;
        user = kanbanDao.getUserByLogin(_login).get(0);
    }

    public String getLogin() {
        return user.login;
    }

    private void initRoomsList() {
        rooms = kanbanDao.getRooms();
    }
}
