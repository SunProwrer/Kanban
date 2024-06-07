package backend;

import android.app.Activity;
import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import database.dao.KanbanDao;
import database.dataclass.RoomEntity;
import database.dataclass.TaskEntity;

public class TaskChangeActivityBackend extends TaskActivityBackend {

    public TaskChangeActivityBackend(Context _context, KanbanDao _kanbanDao, int idTask) {
        super(_context, _kanbanDao, idTask);
    }

    @Override
    public void increaseTaskStatus() {
        if (task.status < TaskEntity.DONE) {
            task.status++;
        }
    }

    @Override
    public void decreaseTaskStatus() {
        if (task.status > TaskEntity.TODO) {
            task.status--;
        }
    }

    @Override
    public String getDeadline() {
        String pattern = "yy.MM.dd";
        DateFormat df = new SimpleDateFormat(pattern, Locale.getDefault());
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }

    public Date getDeadlineDate() {
        return task.deadline;
    }

    public void updateHeader(String header) {
        task.header = header;
    }

    public void updateDeadline(Date date) {
        task.deadline = date;
    } //TODO неработайт

    public void updateBody(String body) {
        task.body = body;
    }
}
