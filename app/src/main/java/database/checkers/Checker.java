package database.checkers;

import database.dao.KanbanDao;

public class Checker {
    public static boolean checkLoginAndPassword(KanbanDao kanbanDao, String login, String password){
        return kanbanDao.checkUserByLoginAndPassword(login, password) != null;
    }
    public static boolean checkLogin(KanbanDao kanbanDao, String login){
        return kanbanDao.checkUserByLogin(login) != null;
    }
}
