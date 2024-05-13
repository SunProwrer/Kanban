package database.checkers;

import java.util.List;

import database.dao.KanbanDao;
import database.dataclass.UserEntity;

public class Checker {
    public static boolean checkLoginAndPassword(KanbanDao kanbanDao, String login, String password){
        List<UserEntity> result = kanbanDao.getUserByLoginAndPassword(login, password);
        return result != null && !result.isEmpty();
    }
    public static boolean checkLogin(KanbanDao kanbanDao, String login){
        List<UserEntity> result = kanbanDao.getUserByLogin(login);
        return result != null && !result.isEmpty();
    }
}
