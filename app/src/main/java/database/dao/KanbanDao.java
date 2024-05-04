package database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import database.dataclass.AccessEntity;
import database.dataclass.RoomEntity;
import database.dataclass.TaskEntity;
import database.dataclass.UserEntity;

@Dao
public interface KanbanDao {
    @Query("SELECT * FROM users")
    LiveData<List<UserEntity>> getUsers();

    @Query("SELECT idUser FROM users WHERE login = :login AND password = :password")
    LiveData<List<Integer>> checkUserByLoginAndPassword(String login, String password);

    @Query("SELECT idUser FROM users WHERE login = :login")
    LiveData<List<Integer>> checkUserByLogin(String login);

    @Update
    void updateUser(UserEntity user);

    @Insert
    void insertUser(UserEntity user);

    @Delete
    void deleteUser(UserEntity user);

    @Query("SELECT * FROM rooms")
    LiveData<List<RoomEntity>> getRooms();

    @Update
    void updateRoom(RoomEntity room);

    @Insert
    void insertRoom(RoomEntity room);

    @Delete
    void deleteRoom(RoomEntity room);

    @Query("SELECT * FROM access")
    LiveData<List<AccessEntity>> getAccess();

    @Insert
    void insertAccess(AccessEntity access);

    @Delete
    void deleteAccess(AccessEntity access);

    @Query("SELECT * FROM tasks")
    LiveData<List<TaskEntity>> getTask();

    @Update
    void updateTask(TaskEntity task);

    @Insert
    void insertTask(TaskEntity task);

    @Delete
    void deleteTask(TaskEntity task);


}
