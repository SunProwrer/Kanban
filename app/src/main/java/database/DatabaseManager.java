package database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

import database.dao.KanbanDao;
import database.dataclass.AccessEntity;
import database.dataclass.UserEntity;

public class DatabaseManager {
    private final DatabaseHelper db;
    private static DatabaseManager instance;

    public static DatabaseManager getInstance(Context context){
        if (instance == null){
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    private DatabaseManager(Context context){
        db = Room.databaseBuilder(context, DatabaseHelper.class, DatabaseHelper.DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        Executors.newSingleThreadScheduledExecutor().execute(() -> initData(context));
                    }
                })
                .allowMainThreadQueries()
                .build();
    }
    private void initData(Context context){
        KanbanDao kanbanDao = DatabaseManager.getInstance(context).getKanbanDao();
    }

    public KanbanDao getKanbanDao(){
        return db.kanbanDao();
    }
}

