package model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import model.entity.AccessEntity;
import model.entity.RoomEntity;
import model.entity.TaskEntity;
import model.entity.UserEntity;

@Dao
public interface KanbanDao {
    @Query("SELECT * FROM users")
    List<UserEntity> getUsers();

    @Query("SELECT * FROM users WHERE idUser = :idUser")
    List<UserEntity> getUserById(long idUser);

    @Query("SELECT * FROM users WHERE login = :login AND password = :password")
    List<UserEntity> getUserByLoginAndPassword(String login, String password);

    @Query("SELECT * FROM users WHERE login = :login")
    List<UserEntity> getUserByLogin(String login);

    @Update
    void updateUser(UserEntity user);

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = UserEntity.class)
    void insertUser(UserEntity user);

    @Insert
    void insertUsers(List<UserEntity> user);

    @Delete
    void deleteUser(UserEntity user);

    @Query("SELECT * FROM rooms")
    List<RoomEntity> getRooms();

    @Query("SELECT * FROM rooms WHERE idRoom = :idRoom")
    List<RoomEntity> getRoomById(long idRoom);

    @Query("SELECT * FROM rooms WHERE idRoom IN (SELECT idRoom FROM access WHERE idUser = :idUser)")
    List<RoomEntity> getRoomsByUserId(long idUser);

    @Query("Select * FROM rooms WHERE name = :name")
    List<RoomEntity> getRoomByName(String name);

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

    @Query("SELECT * FROM tasks WHERE idTask = :idTask")
    List<TaskEntity> getTaskById(long idTask);

    @Query("SELECT * FROM tasks WHERE header = :header AND idRoom = :idRoom")
    List<TaskEntity> getTaskByHeaderAndIdRoom(String header, long idRoom);

    @Query("SELECT * FROM tasks WHERE idRoom = :idRoom AND status = :status")
    List<TaskEntity> getTasksByIdRoomAndStatus(long idRoom, int status);

    @Query("SELECT * FROM tasks WHERE header = :header")
    List<TaskEntity> getTaskByHeader(String header);

    @Update
    void updateTask(TaskEntity task);

    @Insert
    void insertTask(TaskEntity task);

    @Delete
    void deleteTask(TaskEntity task);

    @Query("SELECT COUNT(*) FROM tasks WHERE idRoom = :idRoom AND status = :status")
    List<Integer> getCountTasksByRoomAndStatus(long idRoom, int status);
}
