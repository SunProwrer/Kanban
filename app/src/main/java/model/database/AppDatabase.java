package model.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import model.database.dao.KanbanDao;
import model.entity.AccessEntity;
import model.entity.RoomEntity;
import model.entity.TaskEntity;
import model.entity.UserEntity;

@Database(entities = {UserEntity.class, RoomEntity.class, TaskEntity.class, AccessEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract KanbanDao kanbanDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "kanban_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}