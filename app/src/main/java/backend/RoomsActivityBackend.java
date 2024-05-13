package backend;

import android.app.Activity;
import android.content.Context;
import android.widget.Adapter;

import java.util.List;

import adapters.RoomAdapter;
import database.dao.KanbanDao;
import database.dataclass.RoomEntity;
import database.dataclass.UserEntity;

public class RoomsActivityBackend {
    private Activity activity;
    private final Context context;
    private final KanbanDao kanbanDao;
    private UserEntity user;
    private List<RoomEntity> rooms;
    private RoomAdapter adapter;

    public RoomsActivityBackend(Context _context, KanbanDao _kanbanDao, String _login) {
        context = _context;
        kanbanDao = _kanbanDao;
        user = kanbanDao.getUserByLogin(_login).get(0);
        initRoomsList();
    }

    public String getLogin() {
        return user.login;
    }

    public RoomAdapter getAdapter() {
        return adapter;
    }

    private void initRoomsList() {
        rooms = kanbanDao.getRoomsByUserId(user.idUser);
    }

    private void createAdapter() {
        adapter = new RoomAdapter(context, rooms);
    }
}
