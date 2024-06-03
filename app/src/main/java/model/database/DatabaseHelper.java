package model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import model.database.converters.Converters;
import model.database.dao.KanbanDao;
import model.entity.RoomEntity;
import model.entity.AccessEntity;
import model.entity.TaskEntity;
import model.entity.UserEntity;

@Database(entities = {UserEntity.class, AccessEntity.class, RoomEntity.class, TaskEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DatabaseHelper extends RoomDatabase {
    public static final String DATABASE_NAME = "kanban";
    public abstract KanbanDao kanbanDao();

}
