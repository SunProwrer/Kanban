package database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

import database.dao.KanbanDao;
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
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                initData(context);
                            }
                        });
                    }
                })
                .allowMainThreadQueries()
                .build();
    }
    private void initData(Context context){
        KanbanDao kanbanDao = DatabaseManager.getInstance(context).getKanbanDao();

        UserEntity user = new UserEntity();
        user.password = "d";
        user.login = "d";
        kanbanDao.insertUser(user);
    }

    public KanbanDao getKanbanDao(){
        return db.kanbanDao();
    }
}

