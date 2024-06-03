package model.database.checkers;

import java.util.List;

import model.database.dao.KanbanDao;
import model.entity.RoomEntity;
import model.entity.TaskEntity;
import model.entity.UserEntity;

public class Checker {
    public static boolean checkLoginAndPassword(KanbanDao kanbanDao, String login, String password) {
        List<UserEntity> result = kanbanDao.getUserByLoginAndPassword(login, password);
        return result != null && !result.isEmpty();
    }
    public static boolean checkLogin(KanbanDao kanbanDao, String login) {
        List<UserEntity> result = kanbanDao.getUserByLogin(login);
        return result != null && !result.isEmpty();
    }
    public static boolean checkAvailableNameOfRoom(KanbanDao kanbanDao, String name) {
        List<RoomEntity> result = kanbanDao.getRoomByName(name);
        return result != null && !result.isEmpty();
    }
    public static boolean checkAvailableNameOfTaskForRoom(KanbanDao kanbanDao, String header, long idRoom) {
        List<TaskEntity> result = kanbanDao.getTaskByHeaderAndIdRoom(header, idRoom);
        return result != null && !result.isEmpty();
    }
}
