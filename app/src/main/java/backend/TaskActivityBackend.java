package backend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.hse.kanban.R;
import org.hse.kanban.TaskChangeActivity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import database.dao.KanbanDao;
import database.dataclass.RoomEntity;
import database.dataclass.TaskEntity;

public class TaskActivityBackend implements Serializable {
    private Activity activity;
    private Context context;
    private KanbanDao kanbanDao;
    protected TaskEntity task;

    public TaskActivityBackend(Context _context, KanbanDao _kanbanDao, int idTask) {
        context = _context;
        kanbanDao = _kanbanDao;
        task = kanbanDao.getTaskById(idTask).get(0);
    }

    public String getTaskHeader() {
        return task.header;
    }

    public String getTaskStatus() {
        return context.getResources().getStringArray(R.array.statuses)[task.status];
    }

    public String getDeadline() {
        Date date = task.deadline;
        int periodDays = (int)((date.getTime() - new Date().getTime()) / (24 * 60 * 60 * 1000));
        if (periodDays > 7) {
            String pattern = "yy.MM.dd";
            DateFormat df = new SimpleDateFormat(pattern, Locale.getDefault());
            Date today = Calendar.getInstance().getTime();
            return  df.format(today);
        }

        return String.valueOf(periodDays);
    }

    public String getTaskBody() {
        return task.body;
    }

    public void increaseTaskStatus() {
        if (task.status < TaskEntity.DONE) {
            task.status++;
            kanbanDao.updateTask(task);
        }
    }

    public void decreaseTaskStatus() {
        if (task.status > TaskEntity.TODO) {
            task.status--;
            kanbanDao.updateTask(task);
        }
    }

    public void deleteTask() {
        kanbanDao.deleteTask(task);
    }

    public void goUpdateTask() {
        Intent intent = new Intent(context, TaskChangeActivity.class);
        intent.putExtra(TaskChangeActivity.TASK, task.idTask);
        context.startActivity(intent);
    }

    public void applyChanges() {
        kanbanDao.updateTask(task);
    }
}
