package database.checkers;

import androidx.lifecycle.LiveData;

import java.util.Collection;
import java.util.List;

import database.dao.KanbanDao;

public class Checker {
    private static <T> boolean checkCollectionIsNotNullOrEmpty(Collection<T> ld){
        return ld != null && !ld.isEmpty();
    }
    public static boolean checkLoginAndPassword(KanbanDao kanbanDao, String login, String password){
        List<Integer> ld = kanbanDao.checkUserByLoginAndPassword(login, password).getValue();
        return Checker.<Integer>checkCollectionIsNotNullOrEmpty(ld);
    }
    public static boolean checkLogin(KanbanDao kanbanDao, String login){
        List<Integer> ld = kanbanDao.checkUserByLogin(login).getValue();
        return Checker.<Integer>checkCollectionIsNotNullOrEmpty(ld);
    }
}
