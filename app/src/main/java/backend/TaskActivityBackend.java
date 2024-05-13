package backend;

import android.app.Activity;
import android.content.Context;

import database.dao.KanbanDao;

public class TaskActivityBackend {
    private Activity activity;
    private Context context;
    private KanbanDao kanbanDao;

    public TaskActivityBackend(Context _context, KanbanDao _kanbanDao) {
        context = _context;
        kanbanDao = _kanbanDao;
    }
}
