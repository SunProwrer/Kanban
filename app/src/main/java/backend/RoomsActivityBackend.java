package backend;

import android.app.Activity;
import android.content.Context;

import database.dao.KanbanDao;

public class RoomsActivityBackend {
    private Activity activity;
    private final Context context;
    private final KanbanDao kanbanDao;
    private String login;

    public RoomsActivityBackend(Context _context, KanbanDao _kanbanDao, String _login) {
        context = _context;
        kanbanDao = _kanbanDao;
        login = _login;
    }
}
