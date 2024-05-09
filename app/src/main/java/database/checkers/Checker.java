package database.checkers;

import java.util.List;

import database.dao.KanbanDao;

public class Checker {
    public static boolean checkLoginAndPassword(KanbanDao kanbanDao, String login, String password){
        List<Integer> result = kanbanDao.checkUserByLoginAndPassword(login, password);
        return result != null && !result.isEmpty();
    }
    public static boolean checkLogin(KanbanDao kanbanDao, String login){
        List<Integer> result = kanbanDao.checkUserByLogin(login);
        return result != null && !result.isEmpty();
    }
}
