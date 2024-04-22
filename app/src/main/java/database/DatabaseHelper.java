package database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import database.converters.Converters;
import database.dataclass.RoomEntity;
import database.dataclass.StatusEntity;
import database.dataclass.TaskEntity;
import database.dataclass.UserEntity;

@Database(entities = {UserEntity.class, StatusEntity.class, RoomEntity.class, TaskEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DatabaseHelper extends RoomDatabase {
    public static final String DATABASE_NAME = "kanban";

}
