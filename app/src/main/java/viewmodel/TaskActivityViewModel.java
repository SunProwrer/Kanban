package viewmodel;

import android.app.Activity;
import android.content.Context;

import model.database.dao.KanbanDao;

public class TaskActivityViewModel {
    private Activity activity;
    private Context context;
    private KanbanDao kanbanDao;

    public TaskActivityViewModel(Context _context, KanbanDao _kanbanDao) {
        context = _context;
        kanbanDao = _kanbanDao;
    }
}
